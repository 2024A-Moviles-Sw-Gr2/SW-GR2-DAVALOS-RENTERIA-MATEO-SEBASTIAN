package com.example.proyectomovilesb2.entities

data class BReseña(
    val id: Int,
    val id_usuario: Int,

    val descripcion: String,
    val nombre_categoria_reseña: String,
    val nombre_pelicula: String,
    val nombre_categoria_pelicula: String,
    val likes: Int,
    val fecha: String,
    val titulo: String
)