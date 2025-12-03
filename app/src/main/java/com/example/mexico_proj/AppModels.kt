package com.example.mexico_proj

import androidx.compose.runtime.mutableStateListOf

// App Mode for Prototype Switching
enum class AppMode {
    SPEECH_BASED,  // Prototype A - Blue Theme
    IMAGE_BASED,    // Prototype B - Green Theme
    EMPLOYER
}

// User data model
data class User(
    val name: String,
    val literacyLevel: String
)

// Job data model
data class Job(
    val id: Int,
    val title: String,
    val payRate: String,
    val location: String,
    val hasBenefits: Boolean,
    val isSafe: Boolean,
    val description: String,
    val hoursPerWeek: String,
    val contractType: String
)

// Payment Receipt data model
data class PaymentReceipt(
    val receiptId: String,
    val netPay: Double,
    val grossPay: Double,
    val deductions: Double,
    val date: String,
    val breakdown: List<PaymentBreakdownItem>
)

data class PaymentBreakdownItem(
    val label: String,
    val amount: Double
)

// Task event data for logging
data class TaskEvent(
    val taskId: String,
    val prototypeMode: AppMode,
    val eventType: EventType,
    val timestamp: Long,
    val details: String = ""
)

enum class EventType {
    TASK_START,
    TASK_COMPLETE,
    TASK_ERROR,
    NAVIGATION,
    INTERACTION
}

// App Settings
data class AppSettings(
    var language: AppLanguage = AppLanguage.ENGLISH,
    var currentMode: AppMode = AppMode.SPEECH_BASED
)

// Mock Data
object MockData {
    val currentUser = User(
        name = "Juan",
        literacyLevel = "Bajo"
    )

    private val _jobs = mutableStateListOf(
        Job(id = 1, title = "Trabajador de Construcción", payRate = "$200 MXN/día", location = "Ciudad de México", hasBenefits = true, isSafe = true, description = "Trabajo en construcción con seguro médico, equipo de seguridad proporcionado, y pagos puntuales", hoursPerWeek = "40 horas", contractType = "Temporal - 3 meses"),
        Job(id = 2, title = "Trabajador Agrícola", payRate = "$150 MXN/día", location = "Sinaloa", hasBenefits = false, isSafe = false, description = "Trabajo en campos agrícolas, largas horas bajo el sol, sin seguro médico", hoursPerWeek = "60+ horas", contractType = "Temporal - Por cosecha"),
        Job(id = 3, title = "Ayudante de Almacén", payRate = "$180 MXN/día", location = "Monterrey", hasBenefits = true, isSafe = true, description = "Trabajo en almacén con prestaciones de ley, ambiente seguro, y oportunidad de crecimiento", hoursPerWeek = "48 horas", contractType = "Permanente")
    )
    val jobs: List<Job> = _jobs

    fun addJob(job: Job) {
        _jobs.add(job)
    }

    val lastPaymentReceipt = PaymentReceipt(
        receiptId = "REC-2024-001",
        netPay = 3600.0,
        grossPay = 4200.0,
        deductions = 600.0,
        date = "15 de Noviembre, 2025",
        breakdown = listOf(
            PaymentBreakdownItem("Salario Base", 4200.0),
            PaymentBreakdownItem("Seguro Social", -300.0),
            PaymentBreakdownItem("ISR", -200.0),
            PaymentBreakdownItem("Otros", -100.0)
        )
    )
}
