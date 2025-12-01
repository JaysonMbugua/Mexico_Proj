package com.example.mexico_proj

enum class UserType {
    JOB_SEEKER,
    EMPLOYER
}

// Employer user data model
data class Employer(
    val companyName: String,
    val contactEmail: String,
    val userType: UserType = UserType.EMPLOYER
)

// Model for a job posted by an employer
data class PostedJob(
    val jobId: Int,
    val title: String,
    val description: String,
    val location: String = "",
    val hoursPerWeek: String = "",
    val contractType: String = "",
    val hasBenefits: Boolean = false,
    val payRate: String = "",
    val applicants: List<JobApplication> = emptyList()
)

// Model for a job application submitted by a job seeker
data class JobApplication(
    val applicantName: String,
    val applicantId: String,
    val applicationDate: String
)
