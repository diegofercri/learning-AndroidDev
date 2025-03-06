package dev.diegofercri.ex2ev.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*
import dev.diegofercri.ex2ev.model.*

interface Ex2evService {

    @GET("marcas")
    fun getMarcas(): Deferred<Response<Result>>

    @GET("marca/{idmarca}/gafas")
    fun getGafas(@Path("idmarca") idmarca: Int): Deferred<Response<Result>>

    @GET("usuarios")
    fun getUsuarios(): Deferred<Response<Result>>

    @GET("usuario")
    fun getDataUsuarioPorNickPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<Result>>

    @GET("gafa/{idgafa}/comentarios")
    fun getComentarios(@Path("idgafa") idgafa: Int): Deferred<Response<Result>>

    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Result>>

    @POST("gafa/{idgafa}/comentario")
    suspend fun saveComentario(@Path("idgafa") idgafa: Int,
                               @Body comentario: Comentario
    ): Response<Result>
}
