package com.example.bugcatyyo

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicaService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.lofirecortado)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && (intent.getStringExtra("caller") == "com.example.bugcatyyo.MainActivity" || intent.getStringExtra("caller") == "com.example.bugcatyyo.RegistroUsuario")) {
            mediaPlayer.start()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}

