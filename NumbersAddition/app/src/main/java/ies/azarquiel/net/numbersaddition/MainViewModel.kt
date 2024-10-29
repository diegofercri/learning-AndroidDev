package ies.azarquiel.net.numbersaddition

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _targetNumber = MutableLiveData(0)
    val targetNumber: LiveData<Int> = _targetNumber

    private val _firstNumber = MutableLiveData("?")
    val firstNumber: LiveData<String> = _firstNumber

    private val _secondNumber = MutableLiveData("?")
    val secondNumber: LiveData<String> = _secondNumber

    private val _rowColor = MutableLiveData(Color.White)
    val rowColor: LiveData<Color> = _rowColor

    private val _attempts = MutableLiveData(0)
    val attempts: LiveData<Int> = _attempts

    private val _successes = MutableLiveData(0)
    val successes: LiveData<Int> = _successes

    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog

    init {
        generateNewTarget()
    }

    fun generateNewTarget() {
        viewModelScope.launch {
            _targetNumber.value = Random.nextInt(2, 19)
        }
    }

    fun onClick(number: Int) {
        if (_firstNumber.value == "?") {
            _firstNumber.value = number.toString()
        } else if (_secondNumber.value == "?") {
            _secondNumber.value = number.toString()
            checkSum()
        }
    }

    private fun checkSum() {
        viewModelScope.launch {
            val sum = _firstNumber.value!!.toInt() + _secondNumber.value!!.toInt()
            _attempts.value = _attempts.value!! + 1
            if (sum == _targetNumber.value) {
                _successes.value = _successes.value!! + 1
                _rowColor.value = Color.Green
            } else {
                _rowColor.value = Color.Red
            }
            delay(1000)
            _rowColor.value = Color.White
            if ((_attempts.value ?: 0) == 10) {
                _showDialog.value = true
            } else {
                resetNumbers()
                generateNewTarget()
            }
        }
    }

    private fun resetNumbers() {
        _firstNumber.value = "?"
        _secondNumber.value = "?"
    }

    fun onDialogDismiss() {
        _showDialog.value = false
        resetGame()
    }

    private fun resetGame() {
        _attempts.value = 0
        _successes.value = 0
        resetNumbers()
        generateNewTarget()
    }
}
