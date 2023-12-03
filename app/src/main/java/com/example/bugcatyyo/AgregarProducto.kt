package com.example.bugcatyyo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class AgregarProducto : AppCompatActivity() {

    private lateinit var txtName: TextInputEditText
    private lateinit var txtDescription: TextInputEditText
    private lateinit var txtImage: TextInputEditText
    private lateinit var txtPrice: TextInputEditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var addButton: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        txtName = findViewById(R.id.txt_name)
        txtDescription = findViewById(R.id.txt_description)
        txtImage = findViewById(R.id.txt_imagen)
        txtPrice = findViewById(R.id.txt_price)
        addButton = findViewById(R.id.btn_Actualizar)

        spinnerCategory = findViewById(R.id.spinner_2)

        val categoriasList = arrayOf("Ropa", "Taza", "Accesorio", "Peluche")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriasList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

    fun agregarProducto(view: View) {
        val nombre = txtName.text.toString()
        val descripcion = txtDescription.text.toString()
        val categoria = spinnerCategory.selectedItem.toString()
        val imagen = txtImage.text.toString()
        val precio = txtPrice.text.toString().toDoubleOrNull()

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty() && precio != null) {
            val producto = Producto(imagen, nombre, precio, descripcion, categoria)

            db.collection("productos")
                .add(producto)
                .addOnSuccessListener { documentReference ->
                    val nuevoId = documentReference.id

                    producto.id = nuevoId

                    db.collection("productos").document(nuevoId)
                        .set(producto)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registro completado", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MantenimientoActivity::class.java))
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}