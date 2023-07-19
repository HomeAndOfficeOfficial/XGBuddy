package com.yamahaw.xgbuddy.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.yamahaw.xgbuddy.midi.MidiSession
import com.yamahaw.xgbuddy.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val CHANNEL_ID = "11"
const val NOTIFICATION_ID = 12

@AndroidEntryPoint
class MidiService : Service() {
    @Inject
    lateinit var midiSession: MidiSession
    class LocalBinder(val service: MidiService): Binder() {}
    private val binder = LocalBinder(this)
    override fun onBind(intent: Intent?): IBinder = binder

    fun getNotification(): Notification {
        createNotificationChannel()
        return buildNotification()
    }

    private fun buildNotification(): Notification =
        Notification.Builder(this, CHANNEL_ID)
            .setOngoing(true)
            .setContentTitle("MidiService is running")
            .setContentText("Feel free to do MIDI work")
            .setSmallIcon(R.drawable.ic_stat_name)
            .build()

    private fun createNotificationChannel() {
        val chan = NotificationChannel(
            CHANNEL_ID,
            "XGBuddy", NotificationManager.IMPORTANCE_NONE
        )
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
    }
}