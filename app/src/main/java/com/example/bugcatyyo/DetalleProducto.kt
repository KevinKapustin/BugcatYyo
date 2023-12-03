package com.example.bugcatyyo

data class DetalleProducto(
    val imagen: String = "",
    val nombre: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 0,
    val subtotal: Double = 0.0
)
