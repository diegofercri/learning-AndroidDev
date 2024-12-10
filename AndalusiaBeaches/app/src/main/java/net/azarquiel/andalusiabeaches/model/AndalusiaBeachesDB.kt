package net.azarquiel.andalusiabeaches.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Coast::class, Beach::class], version = 1)
abstract class AndalusiaBeachesDB: RoomDatabase() {
    abstract fun coastDAO(): CoastDAO
    abstract fun beachesDAO(): BeachesDAO
    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE:  AndalusiaBeachesDB? = null

        fun getDatabase(context: Context): AndalusiaBeachesDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AndalusiaBeachesDB::class.java, "playasandalu.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
