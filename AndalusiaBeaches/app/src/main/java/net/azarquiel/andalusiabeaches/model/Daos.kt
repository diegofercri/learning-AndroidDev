package net.azarquiel.andalusiabeaches.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CoastDAO{
    @Query("SELECT * FROM coast ORDER BY name")
    fun getCoasts(): LiveData<List<Coast>>
}

@Dao
interface BeachesDAO {
    @Transaction
    @Query("SELECT * FROM beach WHERE coast = :coast ORDER BY name")
    fun getBeachesByCoast(coast:Int) : LiveData<List<Beach>>

    @Transaction
    @Query("SELECT * FROM beach WHERE blue = 1 AND coast = :coast ORDER BY name")
    fun getBlueBeachesByCoast(coast:Int) : LiveData<List<Beach>>
}