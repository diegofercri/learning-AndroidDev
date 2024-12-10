package net.azarquiel.andalusiabeaches.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.andalusiabeaches.model.Beach
import net.azarquiel.andalusiabeaches.model.BeachRepository

class BeachViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: BeachRepository = BeachRepository(application)

    fun getBeachesByCoast(coast:Int): LiveData<List<Beach>>{
        return repository.getBeachesByCoast(coast)
    }

    fun getBlueBeachesByCoast(coast:Int): LiveData<List<Beach>>{
        return repository.getBlueBeachesByCoast(coast)
    }
}

