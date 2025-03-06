package net.azarquiel.avesretrofitjpc.api

import kotlinx.coroutines.Deferred
import net.azarquiel.avesretrofitjpc.model.Comentario
import retrofit2.Response
import retrofit2.http.*
import net.azarquiel.avesretrofitjpc.model.Result
import net.azarquiel.avesretrofitjpc.model.Usuario

interface AveService {

    @GET("zonas")
    fun getZonas(): Deferred<Response<Result>>

    @GET("zona/{idzona}/recursos")
    fun getRecursos(@Path("idzona") idzona: Int): Deferred<Response<Result>>

    @GET("usuarios")
    fun getUsuarios(): Deferred<Response<Result>>

    @GET("usuario")
    fun getDataUsuarioPorNickPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<Result>>

    @GET("recurso/{idrecurso}/comentarios")
    fun getComentarios(@Path("idrecurso") idrecurso: Int): Deferred<Response<Result>>

    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Result>>

    @POST("recurso/{idrecurso}/comentario")
    suspend fun saveComentario(@Path("idrecurso") idrecurso: Int,@Body comentario: Comentario
    ): Response<Result>}