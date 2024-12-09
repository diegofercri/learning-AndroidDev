package net.azarquiel.acbroomjpc.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface DaoJugador {
    @Update
    fun update(jugador: Jugador)

    @Transaction
    @Query("SELECT * from jugador ORDER BY nombre ASC")
    fun getJugadores(): LiveData<List<JugadorWE>>
}


