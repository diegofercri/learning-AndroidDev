package net.azarquiel.famouspersonsgame

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import net.azarquiel.famouspersonsgame.model.Person

class MainViewModel(context: Context) : ViewModel() {

    // Lista de objetos persona
    private val persons = ArrayList<Person>()

    // LiveData para las imágenes, nombres y colores
    private val _images = MutableLiveData<List<Int>>()
    val images: LiveData<List<Int>> = _images

    private val _names = MutableLiveData<List<String>>()
    val names: LiveData<List<String>> = _names

    init {
        loadPersons(context)
        setupGameData()
    }

    private fun loadPersons(context: Context) {

        // Obtiene SharedPreferences
        val sharedPreferences = context.getSharedPreferences("persons", Context.MODE_PRIVATE)

        // Lee el JSON almacenado en la clave "persons"
        val personsShare = sharedPreferences.all

        // Añade las personas al ArrayList "persons"
        for ((key,value) in personsShare) {
            val jsonPerson = value.toString()
            val person = Gson().fromJson(jsonPerson, Person::class.java)
            persons.add(person)
        }
    }

    private fun setupGameData() {
        // Obtener 5 personas aleatorias
        val randomPersons = persons.shuffled().take(5)
    }
}

