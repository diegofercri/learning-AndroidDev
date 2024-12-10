package net.azarquiel.andalusiabeaches.model

import android.app.Application
import androidx.lifecycle.LiveData

class CoastRepository (application: Application) {
    private val coastDAO = AndalusiaBeachesDB.getDatabase(application).coastDAO()
    fun getCoasts() : LiveData<List<Coast>> {
        return coastDAO.getCoasts()
    }
}

class BeachRepository (application: Application) {
    private val beachesDAO = AndalusiaBeachesDB.getDatabase(application).beachesDAO()

    fun  getBeachesByCoast(coast:Int) : LiveData<List<Beach>> {
        return beachesDAO.getBeachesByCoast(coast)
    }
    fun  getBlueBeachesByCoast(coast:Int) : LiveData<List<Beach>> {
        return beachesDAO.getBlueBeachesByCoast(coast)
    }
}