package com.example.oxentesushi.data

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sushi::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sushiDao(): SushiDao

    companion object{

        @Volatile
        private var INSTACE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTACE

            if(tempInstance != null){
                return tempInstance
            }else{
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "sushi_table"
                    ).build()

                    INSTACE = instance
                    return instance
                }
            }

        }
    }

}