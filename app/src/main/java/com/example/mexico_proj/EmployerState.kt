package com.example.mexico_proj

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object EmployerState {
    var isLoggedIn by mutableStateOf(false)
    var currentEmployer by mutableStateOf<Employer?>(null)

    private val _postedJobs = mutableStateListOf<PostedJob>(
        // Mock initial data
        PostedJob(
            jobId = 101,
            title = "Supervisor de Almacén",
            description = "Supervisar las operaciones diarias del almacén.",
            applicants = listOf(
                JobApplication("Elena", "USER-002", "2024-05-20"),
                JobApplication("Carlos", "USER-003", "2024-05-21")
            )
        )
    )
    val postedJobsState: SnapshotStateList<PostedJob> = _postedJobs

    fun login(employer: Employer) {
        isLoggedIn = true
        currentEmployer = employer
    }

    fun logout() {
        isLoggedIn = false
        currentEmployer = null
    }

    fun addJob(job: PostedJob) {
        _postedJobs.add(job)
    }
}