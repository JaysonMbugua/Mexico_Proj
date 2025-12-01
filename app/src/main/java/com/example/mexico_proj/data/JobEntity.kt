package com.example.mexico_proj.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val payRate: String,
    val location: String,
    val hasBenefits: Boolean,
    val isSafe: Boolean,
    val description: String,
    val hoursPerWeek: String,
    val contractType: String,
    val postedBy: String = "Empleador",
    val postedAt: Long = System.currentTimeMillis()
)

