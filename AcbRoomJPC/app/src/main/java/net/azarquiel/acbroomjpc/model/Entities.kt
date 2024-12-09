package net.azarquiel.acbroomjpc.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity(tableName = "equipo")
data class Equipo(@PrimaryKey
                 var id: Int=0,
                 var nombre:String="",
                 var imgestadio:String="",
                 var imgescudo:String=""): Serializable
@Entity(tableName = "jugador",
    foreignKeys = [ForeignKey(entity = Equipo::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("equipo"))])
data class Jugador(@PrimaryKey
                    var id: Int=0,
                    var equipo: Int=0,
                    var nombre:String="",
                    var imagen:String="",
                    var link:String="",
                    var pais:String="",
                    var estatura:Float=0F,
                    var edad:Int=0,
                    var likes:Int=0):Serializable
// Relación uno a muchos (un equipo => muchas jugadores)
data class EquipoWithJugadores(
    @Embedded val equipo: Equipo,
    @Relation(
        parentColumn = "id",
        entityColumn = "equipo"
    )
    val jugadores: List<Jugador>
):Serializable

// Relación uno a uno (una jugador => un equipo)
data class JugadorWE(
    @Embedded val jugador: Jugador,
    @Relation(
        parentColumn = "equipo",
        entityColumn = "id"
    )
    val equipo: Equipo
):Serializable

