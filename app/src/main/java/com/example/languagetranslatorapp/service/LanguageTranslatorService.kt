package com.example.languagetranslatorapp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class LanguageTranslatorService : Service() {

    val binder = TranslatorBinder()

    inner class TranslatorBinder : Binder() {
        fun getService() = this@LanguageTranslatorService
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun translate(text: String, onResult: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()
        val client = Translation.getClient(options)
        client.downloadModelIfNeeded().addOnSuccessListener {
            client.translate(text).addOnSuccessListener {
                onResult.invoke(it)
            }.addOnFailureListener {
                onFailure.invoke(it)
            }
        }
    }

}