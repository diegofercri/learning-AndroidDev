package net.azarquiel.avesretrofitjpc.api

import net.azarquiel.avesretrofitjpc.model.Comentario
import net.azarquiel.avesretrofitjpc.model.Recurso
import net.azarquiel.avesretrofitjpc.model.Usuario
import net.azarquiel.avesretrofitjpc.model.Zona

class MainRepository() {
    val service = WebAccess.aveService

    suspend fun getZonas(): List<Zona> {
        val webResponse = service.getZonas().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.zonas
        }
        return emptyList()
    }

    suspend fun getRecursos(idzona:Int): List<Recurso> {
        val webResponse = service.getRecursos(idzona).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.recursos
        }
        return emptyList()
    }

    suspend fun getUsuarios(): List<Usuario> {
        val webResponse = service.getUsuarios().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuarios
        }
        return emptyList()
    }

    suspend fun getComentarios(idrecurso:Int): List<Comentario> {
        val webResponse = service.getComentarios(idrecurso).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comentarios
        }
        return emptyList()
    }

    suspend fun getDataUsuarioPorNickPass(nick:String, pass:String): Usuario? {
        val webResponse = service.getDataUsuarioPorNickPass(nick, pass).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }

    suspend fun saveUsuario(usuario: Usuario): Usuario? {
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return null
    }
    suspend fun saveComentario(idrecurso: Int, comentario: Comentario): Comentario? {
        val webResponse = service.saveComentario(idrecurso, comentario)
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comentario
        }
        return null
    }

}
