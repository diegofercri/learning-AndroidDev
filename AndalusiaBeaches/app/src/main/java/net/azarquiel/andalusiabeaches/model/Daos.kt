package net.azarquiel.andalusiabeaches.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CoastDAO{
    @Query("SELECT * FROM costa ORDER BY nombre")
    fun getCoasts(): LiveData<List<Coast>>
}

@Dao
interface BeachesDAO {
    @Transaction
    @Query("SELECT * FROM playa WHERE costa = :coast ORDER BY nombre")
    fun getBeachesByCoast(coast:Int) : LiveData<List<Beach>>

    @Transaction
    @Query("SELECT * FROM playa WHERE azul = 1 AND costa = :coast ORDER BY nombre")
    fun getBlueBeachesByCoast(coast:Int) : LiveData<List<Beach>>
}