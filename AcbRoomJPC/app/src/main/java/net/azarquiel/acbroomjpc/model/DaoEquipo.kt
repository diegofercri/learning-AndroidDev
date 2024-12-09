package net.azarquiel.acbroomjpc.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface DaoEquipo {
    @Transaction
    @Query("SELECT * from equipo ORDER BY nombre ASC")
    fun getEquipos(): LiveData<List<EquipoWithJugadores>>
}
