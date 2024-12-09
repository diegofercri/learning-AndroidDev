package net.azarquiel.acbroomjpc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.acbroomjpc.model.EquipoWithJugadores
import net.azarquiel.acbroomjpc.repositories.EquipoRepository


class EquipoViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: EquipoRepository = EquipoRepository(application)

    fun getEquipos(): LiveData<List<EquipoWithJugadores>> {
        return repository.getEquipos()
    }
}
