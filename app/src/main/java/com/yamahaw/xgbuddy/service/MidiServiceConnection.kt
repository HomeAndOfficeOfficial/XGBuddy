package com.yamahaw.xgbuddy.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log

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

    override fun onBindingDied(name: ComponentName?) {
        Log.w(TAG, "Binding has died.")
    }

    override fun onNullBinding(name: ComponentName?) {
        Log.w(TAG, "Bind was null.")
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        midiService = null
        Log.w(TAG, "Service is disconnected..")
    }

    companion object {
        const val TAG = "MidiServiceConnection"
    }
}