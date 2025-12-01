package com.example.mexico_proj.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs ORDER BY postedAt DESC")
    fun getAllJobs(): Flow<List<JobEntity>>
    
    @Query("SELECT * FROM jobs WHERE id = :jobId")
    suspend fun getJobById(jobId: Int): JobEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobEntity>)
    
    @Update
    suspend fun updateJob(job: JobEntity)
    
    @Delete
    suspend fun deleteJob(job: JobEntity)
    
    @Query("DELETE FROM jobs")
    suspend fun deleteAllJobs()
    
    @Query("SELECT COUNT(*) FROM jobs")
    suspend fun getJobCount(): Int
}

