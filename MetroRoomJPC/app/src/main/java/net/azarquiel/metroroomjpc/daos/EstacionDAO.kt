package net.azarquiel.metroroomjpc.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import net.azarquiel.metroroomjpc.model.Estacion


@Dao
interface EstacionDAO {


    @Query("SELECT * from estacion where linea = :linea ORDER BY id ASC")
    fun getEstacionesByLinea(linea:Int): LiveData<List<Estacion>>
}
