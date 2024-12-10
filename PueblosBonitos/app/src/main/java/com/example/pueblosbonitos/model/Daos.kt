package com.example.pueblosbonitos.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ComunidadDAO{
    @Query("SELECT * FROM comunidad")
    fun getComunidades(): LiveData<List<Comunidad>>
}

@Dao
interface PueblosDAO {
    @Transaction
    @Query("select * from pueblo where provincia in (select id from provincia where comunidad = :comunidad) order by nombre")
    fun getPueblosByComunidad(comunidad:Int) : LiveData<List<PuebloWp>>

    @Transaction
    @Query("select * from pueblo where fav = 1 and provincia in (select id from provincia where comunidad = :comunidad) order by nombre")
    fun getPueblosFavByComunidad(comunidad:Int) : LiveData<List<PuebloWp>>

    @Update
    fun update(pueblo: Pueblo)
}