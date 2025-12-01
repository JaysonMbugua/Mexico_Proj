package com.example.mexico_proj.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [JobEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mexico_proj_database"
                )
                .addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
    
    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.jobDao())
                }
            }
        }
        
        suspend fun populateDatabase(jobDao: JobDao) {
            // Pre-populate with sample jobs
            val sampleJobs = listOf(
                JobEntity(
                    title = "Trabajador de Construcción",
                    payRate = "$200 MXN/día",
                    location = "Ciudad de México",
                    hasBenefits = true,
                    isSafe = true,
                    description = "Trabajo en construcción con seguro médico, equipo de seguridad proporcionado, y pagos puntuales",
                    hoursPerWeek = "40 horas",
                    contractType = "Temporal - 3 meses",
                    postedBy = "Constructora ABC"
                ),
                JobEntity(
                    title = "Trabajador Agrícola",
                    payRate = "$150 MXN/día",
                    location = "Sinaloa",
                    hasBenefits = false,
                    isSafe = false,
                    description = "Trabajo en campos agrícolas, largas horas bajo el sol, sin seguro médico",
                    hoursPerWeek = "60+ horas",
                    contractType = "Temporal - Por cosecha",
                    postedBy = "Granja Los Campos"
                ),
                JobEntity(
                    title = "Ayudante de Almacén",
                    payRate = "$180 MXN/día",
                    location = "Monterrey",
                    hasBenefits = true,
                    isSafe = true,
                    description = "Trabajo en almacén con prestaciones de ley, ambiente seguro, y oportunidad de crecimiento",
                    hoursPerWeek = "48 horas",
                    contractType = "Permanente",
                    postedBy = "Almacenes del Norte"
                )
            )
            jobDao.insertJobs(sampleJobs)
        }
    }
}

