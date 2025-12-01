package com.example.mexico_proj

import android.content.Context
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Singleton logger for tracking usability metrics:
 * - Task Completion Time (TCT)
 * - Task Error Rate (TER)
 */
object UsabilityLogger {
    private const val TAG = "UsabilityLogger"
    private val events = mutableListOf<TaskEvent>()
    private val taskStartTimes = mutableMapOf<String, Long>()
    private val taskErrors = mutableMapOf<String, Int>()
    
    // Track current task
    private var currentTaskId: String? = null
    
    /**
     * Start tracking a task
     */
    fun startTask(taskId: String, mode: AppMode, details: String = "") {
        val timestamp = System.currentTimeMillis()
        currentTaskId = taskId
        taskStartTimes[taskId] = timestamp
        taskErrors[taskId] = 0
        
        val event = TaskEvent(
            taskId = taskId,
            prototypeMode = mode,
            eventType = EventType.TASK_START,
            timestamp = timestamp,
            details = details
        )
        events.add(event)
        Log.d(TAG, "Task Started: $taskId in ${mode.name} mode")
    }
    
    /**
     * Complete a task
     */
    fun completeTask(taskId: String, mode: AppMode, details: String = "") {
        val timestamp = System.currentTimeMillis()
        val startTime = taskStartTimes[taskId]
        val duration = if (startTime != null) {
            timestamp - startTime
        } else {
            -1L
        }
        
        val event = TaskEvent(
            taskId = taskId,
            prototypeMode = mode,
            eventType = EventType.TASK_COMPLETE,
            timestamp = timestamp,
            details = "Duration: ${duration}ms | $details"
        )
        events.add(event)
        currentTaskId = null
        Log.d(TAG, "Task Completed: $taskId in ${duration}ms")
    }
    
    /**
     * Log an error during task execution
     */
    fun logError(taskId: String, mode: AppMode, errorDetails: String) {
        val timestamp = System.currentTimeMillis()
        taskErrors[taskId] = (taskErrors[taskId] ?: 0) + 1
        
        val event = TaskEvent(
            taskId = taskId,
            prototypeMode = mode,
            eventType = EventType.TASK_ERROR,
            timestamp = timestamp,
            details = errorDetails
        )
        events.add(event)
        Log.d(TAG, "Task Error: $taskId - $errorDetails")
    }
    
    /**
     * Log a navigation event
     */
    fun logNavigation(from: String, to: String, mode: AppMode) {
        val event = TaskEvent(
            taskId = currentTaskId ?: "no_active_task",
            prototypeMode = mode,
            eventType = EventType.NAVIGATION,
            timestamp = System.currentTimeMillis(),
            details = "From: $from To: $to"
        )
        events.add(event)
        Log.d(TAG, "Navigation: $from -> $to")
    }
    
    /**
     * Log a general interaction
     */
    fun logInteraction(interactionType: String, mode: AppMode, details: String = "") {
        val event = TaskEvent(
            taskId = currentTaskId ?: "no_active_task",
            prototypeMode = mode,
            eventType = EventType.INTERACTION,
            timestamp = System.currentTimeMillis(),
            details = "$interactionType | $details"
        )
        events.add(event)
        Log.d(TAG, "Interaction: $interactionType - $details")
    }
    
    /**
     * Get all logged events
     */
    fun getEvents(): List<TaskEvent> = events.toList()
    
    /**
     * Get task completion time for a specific task
     */
    fun getTaskCompletionTime(taskId: String): Long? {
        val startEvent = events.findLast { it.taskId == taskId && it.eventType == EventType.TASK_START }
        val completeEvent = events.findLast { it.taskId == taskId && it.eventType == EventType.TASK_COMPLETE }
        
        return if (startEvent != null && completeEvent != null) {
            completeEvent.timestamp - startEvent.timestamp
        } else null
    }
    
    /**
     * Get error count for a specific task
     */
    fun getTaskErrorCount(taskId: String): Int {
        return taskErrors[taskId] ?: 0
    }
    
    /**
     * Get summary statistics
     */
    fun getSummary(): String {
        val sb = StringBuilder()
        sb.appendLine("=== USABILITY STUDY METRICS ===")
        sb.appendLine("Total Events: ${events.size}")
        sb.appendLine("\n--- TASK COMPLETION TIMES ---")
        
        taskStartTimes.keys.forEach { taskId ->
            val tct = getTaskCompletionTime(taskId)
            val errors = getTaskErrorCount(taskId)
            val mode = events.find { it.taskId == taskId }?.prototypeMode?.name ?: "UNKNOWN"
            
            if (tct != null) {
                sb.appendLine("Task: $taskId")
                sb.appendLine("  Mode: $mode")
                sb.appendLine("  TCT: ${tct}ms (${tct/1000.0}s)")
                sb.appendLine("  Errors: $errors")
            }
        }
        
        return sb.toString()
    }
    
    /**
     * Export logs to CSV format for analysis
     */
    fun exportToCSV(): String {
        val sb = StringBuilder()
        sb.appendLine("TaskID,PrototypeMode,EventType,Timestamp,Details")
        
        events.forEach { event ->
            sb.appendLine("${event.taskId},${event.prototypeMode.name},${event.eventType.name},${event.timestamp},\"${event.details}\"")
        }
        
        return sb.toString()
    }
    
    /**
     * Save logs to file
     */
    fun saveToFile(context: Context, filename: String = "usability_log.csv") {
        try {
            val file = File(context.getExternalFilesDir(null), filename)
            file.writeText(exportToCSV())
            Log.d(TAG, "Logs saved to: ${file.absolutePath}")
        } catch (e: Exception) {
            Log.e(TAG, "Error saving logs: ${e.message}")
        }
    }
    
    /**
     * Clear all logs
     */
    fun clearLogs() {
        events.clear()
        taskStartTimes.clear()
        taskErrors.clear()
        currentTaskId = null
        Log.d(TAG, "All logs cleared")
    }
}

