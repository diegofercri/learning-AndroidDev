package net.azarquiel.personsgame.model

data class Person(var id:String="", var cat:String="", var nombre:String="") {
    fun getId(): Int {
        return id.toInt()
    }
    fun getCat(): Int {
        return cat.toInt()
    }
}

data class Category(var id:String="", var nombre:String="") {
    fun getId(): Int {
        return id.toInt()
    }
}