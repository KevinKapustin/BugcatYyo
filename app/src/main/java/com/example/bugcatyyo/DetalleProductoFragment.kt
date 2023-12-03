package com.example.bugcatyyo


import android.content.Intent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class DetalleProductoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_detalle_producto, container, false)

        val descripcion = arguments?.getString("descripcion")
        val imagen      = arguments?.getString("imagen")
        val nombre      = arguments?.getString("nombre")
        val precio      = arguments?.getDouble("precio")

        view.findViewById<TextView>(R.id.txt_nombre_producto).text = nombre
        view.findViewById<TextView>(R.id.txt_descripcion_producto).text = descripcion
        val precioFormateado = String.format("S/. %.2f", precio)
        view.findViewById<TextView>(R.id.txt_precio_producto).text = precioFormateado

        Picasso.get().load(imagen).into(view.findViewById<ImageView>(R.id.imagen_producto))

        view.findViewById<Button>(R.id.btn_comprar).setOnClickListener {
            agregarAlCarrito()
        }

        val txtCantidad = view?.findViewById<EditText>(R.id.txt_cantidad)
        view.findViewById<Button>(R.id.btn_aumentar).setOnClickListener {
            incrementarCantidad(txtCantidad)
        }
        view.findViewById<Button>(R.id.btn_disminuir).setOnClickListener {
            disminuirCantidad(txtCantidad)
        }

        val imgRegresar = view.findViewById<ImageView>(R.id.img_regresar)

        imgRegresar.setOnClickListener {
            val catalogoFragment = CatalogoFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, catalogoFragment)
                .addToBackStack(null)
                .commit()
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val serviceIntent = Intent(requireContext(), MusicaService::class.java)
        requireActivity().stopService(serviceIntent)
    }

    private fun agregarAlCarrito() {
        val cantidadEditText = view?.findViewById<EditText>(R.id.txt_cantidad)
        val cantidadString = cantidadEditText?.text?.toString() ?: "0"

        val cantidad = cantidadString.toIntOrNull() ?: 0

        if (cantidad > 0) {
            val precio = arguments?.getDouble("precio") ?: 0.0
            val subtotal = cantidad * precio

            val detalleProducto = hashMapOf(
                "cantidad" to cantidad,
                "imagen" to (arguments?.getString("imagen") ?: ""),
                "nombre" to (arguments?.getString("nombre") ?: ""),
                "precio" to precio,
                "subtotal" to subtotal
            )

            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.let { user ->
                val db = FirebaseFirestore.getInstance()
                val usuarioRef = db.collection("usuarios").document(user.uid)
                val detalleProductosRef = usuarioRef.collection("detalleproductos")

                detalleProductosRef.add(detalleProducto)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            requireContext(),
                            "Producto añadido al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Error al agregar el producto al carrito", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(requireContext(), "Ingrese una cantidad", Toast.LENGTH_SHORT).show()
        }
    }


    private fun incrementarCantidad(txtCantidad: EditText?) {
        var cantidad = txtCantidad?.text?.toString()?.toIntOrNull() ?: 0
        cantidad++
        txtCantidad?.setText(cantidad.toString())
    }

    private fun disminuirCantidad(txtCantidad: EditText?) {
        var cantidad = txtCantidad?.text?.toString()?.toIntOrNull() ?: 0
        cantidad = maxOf(0, cantidad - 1)
        txtCantidad?.setText(cantidad.toString())
    }


}