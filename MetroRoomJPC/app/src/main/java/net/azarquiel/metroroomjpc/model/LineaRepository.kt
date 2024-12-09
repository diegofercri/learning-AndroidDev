package net.azarquiel.metroroomjpc.model

import android.app.Application
import androidx.lifecycle.LiveData


class LineaRepository(application: Application) {


    val lineaDAO = MetroDB.getDatabase(application).lineaDAO()
    // select
    fun getAllLineas(): LiveData<List<LineaWithEstaciones>> {
        return lineaDAO.getAllLineas()
    }
}
