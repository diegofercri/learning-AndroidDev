package net.azarquiel.famouspersonsgame

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
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

    // Definimos colores en el ViewModel para observar los cambios en la UI
    private val _idColors = MutableLiveData<List<Color>>()
    val idColors: LiveData<List<Color>> = _idColors

    private val _nameColors = MutableLiveData<List<Color>>()
    val nameColors: LiveData<List<Color>> = _nameColors

    // Lista completa de personas
    private val persons = ArrayList<Person>()
    private var id: String? = null
    private var name: String? = null

    init {
        loadPersons(context)
        setupGameData()
        _idColors.value = List(5) { Color.DarkGray }
        _nameColors.value = List(5) { Color.DarkGray }
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
                if (person.name == name && "p" + person.id == id) {
                    found = true
                    break
                }
            }

            // Actualizamos los colores según si el par es correcto o incorrecto
            val idIndex = _fivePersonIds.value?.indexOf(id) ?: -1
            val nameIndex = _fivePersonNames.value?.indexOf(name) ?: -1

            if (idIndex != -1 && nameIndex != -1) {
                _idColors.value = _idColors.value?.toMutableList()?.apply {
                    this[idIndex] = if (found) Color.Green else Color.Red
                }
                _nameColors.value = _nameColors.value?.toMutableList()?.apply {
                    this[nameIndex] = if (found) Color.Green else Color.Red
                }
            }

            // Limpiamos las selecciones
            id = null
            name = null

            // Verifica si todos los pares están en verde para reiniciar el juego
            if (_idColors.value?.all { it == Color.Green } == true &&
                _nameColors.value?.all { it == Color.Green } == true) {
                restartGame()
            }
        }
    }

    // Reinicia los datos del juego (IDs, nombres y colores)
    private fun restartGame() {
        setupGameData()
        _idColors.value = List(5) { Color.Gray }
        _nameColors.value = List(5) { Color.Gray }
    }
}
