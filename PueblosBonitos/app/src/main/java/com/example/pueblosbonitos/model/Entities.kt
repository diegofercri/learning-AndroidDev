package com.example.pueblosbonitos.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity(tableName = "comunidad")
data class Comunidad(@PrimaryKey
                     @ColumnInfo(name = "id")
                     var idComunidad : Int=0,
                     @ColumnInfo(name = "nombre")
                     var nombreComunidad : String=""): Serializable


@Entity(tableName = "provincia",
    foreignKeys = [ForeignKey(entity= Comunidad::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("comunidad"))])
data class Provincia(@PrimaryKey
                     @ColumnInfo(name = "id")
                     var idProvincia : Int=0,
                     @ColumnInfo(name = "nombre")
                     var nombreProvincia : String="",
                     var comunidad : Int=0):Serializable


@Entity(tableName = "pueblo",
    foreignKeys = [ForeignKey(entity= Provincia::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("provincia"))])
data class Pueblo(@PrimaryKey
                  @ColumnInfo(name = "id")
                  var idPueblo : Int=0,
                  @ColumnInfo(name = "nombre")
                  var nombrePueblo : String="",
                  var imagen : String="",
                  var provincia : Int=0,
                  var link : String="",
                  var fav : Int=0):Serializable


data class ProvinciaWithPueblos(
    @Embedded val provincia: Provincia,
    @Relation(
        parentColumn = "id",
        entityColumn = "provincia"
    )
    val pueblos: List<Pueblo>
):Serializable


data class ComunidadWithAll(
    @Embedded val comunidad: Comunidad,
    @Relation(
        entity = Provincia::class,  // esto hace una relación con provincias con pueblos
        parentColumn = "id",
        entityColumn = "comunidad"
    )
    val provincias: List<ProvinciaWithPueblos>
):Serializable


// Relación uno a uno (una pueblo => una provincia)
data class PuebloWp(
    @Embedded val pueblo: Pueblo,
    @Relation(
        parentColumn = "provincia",
        entityColumn = "id"
    )
    val provincia: Provincia
):Serializable
