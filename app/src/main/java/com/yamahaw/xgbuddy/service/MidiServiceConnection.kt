package com.yamahaw.xgbuddy.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

class MidiServiceConnection(private val context: Context) : ServiceConnection {

    private var midiService: MidiService? = null

    override fun onServiceConnected(name: ComponentName?, service: IBinder) {
        val binder: MidiService.LocalBinder = service as MidiService.LocalBinder
        midiService = binder.service
        context.startForegroundService(Intent(context, MidiService::class.java))

        // This is the key: Without waiting Android Framework to call this method
        // inside Service.onCreate(), immediately call here to post the notification.
        midiService?.startForeground(NOTIFICATION_ID, midiService?.getNotification())

        // Release the connection to prevent leaks.
        context.unbindService(this)
    }

    override fun onServiceDisconnected(name: ComponentName?) { midiService = null }

    companion object {
        const val TAG = "MidiServiceConnection"
    }
}