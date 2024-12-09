package net.azarquiel.metroroomjpc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.metroroomjpc.model.Estacion
import net.azarquiel.metroroomjpc.model.EstacionRepository


class EstacionViewModel  (application: Application) : AndroidViewModel(application) {


    private var repository: EstacionRepository = EstacionRepository(application)


    fun getEstacionesByLinea(linea:Int): LiveData<List<Estacion>> {
        return repository.getEstacionesByLinea(linea)
    }
}
