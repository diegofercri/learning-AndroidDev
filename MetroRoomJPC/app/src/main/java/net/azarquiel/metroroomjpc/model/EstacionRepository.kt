package net.azarquiel.metroroomjpc.model

import android.app.Application
import androidx.lifecycle.LiveData


class EstacionRepository(application: Application) {


    val estacionDAO = MetroDB.getDatabase(application).estacionDAO()
    // select
    fun getEstacionesByLinea(linea:Int): LiveData<List<Estacion>> {
        return estacionDAO.getEstacionesByLinea(linea)
    }
}
