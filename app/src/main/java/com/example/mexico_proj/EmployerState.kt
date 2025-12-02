package com.example.mexico_proj

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object EmployerState {
    var isLoggedIn by mutableStateOf(false)
    var currentEmployer by mutableStateOf<Employer?>(null)

    private val _postedJobs = mutableStateListOf<PostedJob>() // Initialize with an empty list
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
