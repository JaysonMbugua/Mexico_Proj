package com.example.mexico_proj.data

import kotlinx.coroutines.flow.Flow

class JobRepository(private val jobDao: JobDao) {
    
    val allJobs: Flow<List<JobEntity>> = jobDao.getAllJobs()
    
    suspend fun getJobById(jobId: Int): JobEntity? {
        return jobDao.getJobById(jobId)
    }
    
    suspend fun insertJob(job: JobEntity): Long {
        return jobDao.insertJob(job)
    }
    
    suspend fun updateJob(job: JobEntity) {
        jobDao.updateJob(job)
    }
    
    suspend fun deleteJob(job: JobEntity) {
        jobDao.deleteJob(job)
    }
    
    suspend fun getJobCount(): Int {
        return jobDao.getJobCount()
    }
}

