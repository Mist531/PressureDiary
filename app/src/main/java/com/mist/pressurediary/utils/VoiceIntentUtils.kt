package com.mist.pressurediary.utils

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResult

object VoiceIntentUtils {

    val voiceIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Say something"
        )
    }

    fun voiceLauncher(
        activityResult: ActivityResult,
        onValueChange: (String) -> Unit
    ) {
        activityResult.data?.let { data ->
            data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { listStr ->
                listStr.firstOrNull()?.let { str ->
                    onValueChange(str)
                }
            }
        }
    }
}