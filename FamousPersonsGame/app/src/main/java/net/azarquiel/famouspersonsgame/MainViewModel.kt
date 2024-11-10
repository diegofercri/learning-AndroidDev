package net.azarquiel.famouspersonsgame

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import net.azarquiel.famouspersonsgame.model.Person

class MainViewModel(context: Context) : ViewModel() {

    // LiveData para IDs y nombres seleccionados aleatoriamente
    private val _fivePersonIds = MutableLiveData<List<String>>()
    val fivePersonIds: LiveData<List<String>> = _fivePersonIds

    private val _fivePersonNames = MutableLiveData<List<String>>()
    val fivePersonNames: LiveData<List<String>> = _fivePersonNames

    // Lista completa de personas
    private val persons = ArrayList<Person>()
    private var id: String? = null
    private var name: String? = null

    init {
        loadPersons(context)
        setupGameData()
    }

    private fun loadPersons(context: Context) {
        val sharedPreferences = context.getSharedPreferences("persons", Context.MODE_PRIVATE)
        val personsShare = sharedPreferences.all

        // Lee cada valor en sharedPreferences y lo convierte en objeto Person
        for ((_, value) in personsShare) {
            val jsonPerson = value.toString()
            try {
                val person = Gson().fromJson(jsonPerson, Person::class.java)
                persons.add(person)
            } catch (e: JsonSyntaxException) {
                Log.e("MainViewModel", "Error parsing JSON for person: $jsonPerson", e)
            }
        }
    }

    private fun setupGameData() {
        // Selecciona cinco personas aleatorias y extrae sus IDs y nombres
        val selectedPersons = persons.shuffled().take(5)

        // Extrae los IDs y nombres en listas separadas
        _fivePersonIds.value = selectedPersons.map { "p${it.id}" }
        _fivePersonNames.value = selectedPersons.shuffled().map { it.name }
    }

    fun pressedId(i: String) {
        id = i
        checkSelected()
    }

    fun pressedName(s: String) {
        name = s
        checkSelected()
    }

    private fun checkSelected() {
        if (id != null && name != null) {
            var found = false
            for (person in persons) {
                if (person.name == name && "p"+person.id == id) {
                    Log.d("MainViewModel", "Person found: $person")
                    found = true
                }
            }
            id = null
            name = null
            if (!found) {
                Log.d("MainViewModel", "Person not found")
            }
        }
    }
}
