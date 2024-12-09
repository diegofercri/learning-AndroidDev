package net.azarquiel.acbroomjpc.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import net.azarquiel.acbroomjpc.model.AcbDB
import net.azarquiel.acbroomjpc.model.EquipoWithJugadores
import net.azarquiel.acbroomjpc.model.Jugador
import net.azarquiel.acbroomjpc.model.JugadorWE


class EquipoRepository(application: Application) {
    val daoEquipo = AcbDB.getDatabase(application).daoEquipo()
    // select
    fun getEquipos(): LiveData<List<EquipoWithJugadores>> {
        return daoEquipo.getEquipos()
    }
}

class JugadorRepository(application: Application) {

    val daoJugador = AcbDB.getDatabase(application).daoJugador()

    // update
    fun update(jugador: Jugador) {
        daoJugador.update(jugador)
    }

    fun getJugadores(): LiveData<List<JugadorWE>> {
        return daoJugador.getJugadores()
    }
}

