package com.example.mexico_proj

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

class TextToSpeechManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private val queuedText = mutableListOf<Pair<String, (() -> Unit)?>>()
    private var utteranceId = 0
    private val mainHandler = Handler(Looper.getMainLooper())
    private val completionCallbacks = mutableMapOf<String, () -> Unit>()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            tts?.language = Locale("es", "MX")
            
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                    utteranceId?.let { id ->
                        synchronized(completionCallbacks) {
                            val callback = completionCallbacks.remove(id)
                            if (callback != null) {
                                mainHandler.post { callback() }
                            }
                        }
                    }
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                    utteranceId?.let { id ->
                        synchronized(completionCallbacks) {
                            completionCallbacks.remove(id) // Remove but don't invoke on error? Or should we? 
                            // Usually we might want to proceed or handle error. 
                            // For this use case (starting mic), maybe we shouldn't start if TTS failed, or maybe we should. 
                            // Let's assume we don't start listening if TTS fails for now, or user can retry.
                        }
                    }
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    _isSpeaking.value = false
                    utteranceId?.let { id ->
                        synchronized(completionCallbacks) {
                            completionCallbacks.remove(id)
                        }
                    }
                }
            })
            
            queuedText.forEach { (text, callback) -> speak(text, callback) }
            queuedText.clear()
        }
    }

    fun speak(text: String, onCompletion: (() -> Unit)? = null) {
        if (isInitialized) {
            val id = "utterance_${utteranceId++}"
            if (onCompletion != null) {
                synchronized(completionCallbacks) {
                    completionCallbacks[id] = onCompletion
                }
            }
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, id)
        } else {
            queuedText.add(text to onCompletion)
        }
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        synchronized(completionCallbacks) {
            completionCallbacks.clear()
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        _isSpeaking.value = false
        synchronized(completionCallbacks) {
            completionCallbacks.clear()
        }
    }
}
