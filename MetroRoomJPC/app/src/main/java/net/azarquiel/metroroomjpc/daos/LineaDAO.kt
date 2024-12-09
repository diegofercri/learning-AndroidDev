package net.azarquiel.metroroomjpc.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import net.azarquiel.metroroomjpc.model.Linea
import net.azarquiel.metroroomjpc.model.LineaWithEstaciones


@Dao
interface LineaDAO {
    @Query("SELECT * from linea ORDER BY id ASC")
    fun getAllLineas(): LiveData<List<LineaWithEstaciones>>
}
