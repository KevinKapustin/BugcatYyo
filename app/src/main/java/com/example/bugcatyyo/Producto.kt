package com.example.bugcatyyo

data class Producto(
    var id: String = "",
    val imagen: String,
    val nombre: String,
    val precio: Double,
    val descripcion: String = "",
    val categoria: String = ""
) {

    constructor(imagen: String, nombre: String, precio: Double) : this("", imagen, nombre, precio, "", "")

    constructor(imagen: String, nombre: String, precio: Double, descripcion: String) : this("", imagen, nombre, precio, descripcion, "")

    constructor(imagen: String, nombre: String, precio: Double, descripcion: String, categoria: String) : this("", imagen, nombre, precio, descripcion, categoria)
}
