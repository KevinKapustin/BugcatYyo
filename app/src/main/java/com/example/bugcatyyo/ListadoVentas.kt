package com.example.bugcatyyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ListadoVentas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_ventas)

        val listView: ListView = findViewById(R.id.listview)
        val adapter = VentasAdapter(this, obtenerDatos()) // Implementa obtenerDatosDeFirestore() para obtener tus datos de Firestore
        listView.adapter = adapter

    }

    private fun obtenerDatos(): List<Venta> {
        val db = FirebaseFirestore.getInstance()
        val ventas = mutableListOf<Venta>()

        // Reemplaza "ventas" con el nombre de tu colección en Firestore
        db.collection("ventas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val id = document.id
                    val fecha = document.getString("fecha") ?: ""
                    val idUsuario = document.getString("idUsuario") ?: ""
                    val montoTotal = document.getString("montoTotal") ?: ""

                    val venta = Venta(id, fecha, idUsuario, montoTotal)
                    ventas.add(venta)
                }

                actualizarListView(ventas)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al obtener los datos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }

        return ventas
    }

    private fun actualizarListView(ventas: List<Venta>) {
        val listView: ListView = findViewById(R.id.listview)
        val adapter = VentasAdapter(this, ventas)
        listView.adapter = adapter
    }




}