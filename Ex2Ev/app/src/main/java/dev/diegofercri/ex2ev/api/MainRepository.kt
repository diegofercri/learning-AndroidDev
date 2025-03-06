package dev.diegofercri.ex2ev.api

import dev.diegofercri.ex2ev.model.*


class MainRepository() {
    val service = WebAccess.ex2evService

    suspend fun getMarcas(): List<Marca> {
        val webResponse = service.getMarcas().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.marcas
        }
        return emptyList()
    }
    suspend fun getGafas(idmarca:Int): List<Gafa> {
        val webResponse = service.getGafas(idmarca).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.gafas
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
    suspend fun getComentarios(idgafa:Int): List<Comentario> {
        val webResponse = service.getComentarios(idgafa).await()
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
    suspend fun saveComentario(idgafa: Int, comentario: Comentario): Comentario? {
        val webResponse = service.saveComentario(idgafa, comentario)
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comentario
        }
        return null
    }

}
