package com.yamahaw.xgbuddy.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.yamahaw.xgbuddy.MidiSession
import com.yamahaw.xgbuddy.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val CHANNEL_ID = "11"
const val NOTIFICATION_ID = 12

@AndroidEntryPoint
class MidiService : Service() {

    @Inject
    lateinit var midiSession: MidiSession

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)
        Log.d("MidiService", "Starting service")
        return START_STICKY
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

//    override fun onDestroy() {
//        super.onDestroy()
//        midiSession.terminateConnection()
//    }
}