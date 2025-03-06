package net.azarquiel.avesretrofitjpc.model

import java.io.Serializable

data class Usuario (
    var id: Int,
    var nick: String,
    var pass: String,
): Serializable

data class Zona (
    var id: Int,
    var nombre: String,
    var localizacion: String,
    var formaciones_principales: String,
    var presentacion: String,
    var geom_lat: Double,
    var geom_lon: Double,
):Serializable

data class Recurso (
    var id: Int,
    var zona: Int,
    var titulo: String,
    var url: String,
): Serializable

data class Comentario(
    var id: Int = 0,
    var recurso: String,
    var usuario: Int,
    var nick: String = "",
    var fecha: String,
    var comentario: String,
): Serializable

data class Result (
    val recursos: List<Recurso>,
    val zonas: List<Zona>,
    val comentarios: List<Comentario>,
    val usuarios: List<Usuario>,
    val usuario: Usuario,
    val comentario: Comentario,
)

