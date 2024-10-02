package net.azarquiel.openweather.model

data class Result(
    val list: List<Day>
)
data class Day(
    val main: Temps,
    val weather: List<Weather>,
    val pop: Int,
    val dt_txt: String
)
data class Temps(
    val temp_min: Float,
    val temp_max: Float
)
data class Weather(
    val description: String,
    val icon: String
)