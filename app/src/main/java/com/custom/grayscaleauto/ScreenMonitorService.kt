package com.custom.grayscaleauto

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import java.lang.reflect.Method

class ScreenMonitorService : Service() {

    private fun setSystemSaturation(level: Int) {
        try {
            val colorDisplayManagerClass = Class.forName("android.hardware.display.ColorDisplayManager")
            val colorDisplayManager = getSystemService(colorDisplayManagerClass)
            val method: Method = colorDisplayManagerClass.getMethod("setSaturationLevel", Int::class.javaPrimitiveType)
            method.invoke(colorDisplayManager, level)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == Intent.ACTION_SCREEN_OFF || action == Intent.ACTION_SCREEN_ON || action == Intent.ACTION_USER_PRESENT) {
                applyGrayscale()
            }
        }
    }

    private fun applyGrayscale() {
        try {
            Settings.Secure.putInt(
                contentResolver,
                "accessibility_display_daltonizer_enabled",
                1
            )
            Settings.Secure.putInt(
                contentResolver,
                "accessibility_display_daltonizer",
                0
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()
        startAsForeground()

        setSystemSaturation(50)

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_USER_PRESENT)
        }
        registerReceiver(screenReceiver, filter)
        applyGrayscale()
    }

    private fun startAsForeground() {
        val channelId = "monoscreen_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "MonoScreen Service",
                NotificationManager.IMPORTANCE_MIN
            ).apply {
                description = "MonoScreen background monitor"
                setShowBadge(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)
                .setContentTitle("MonoScreen")
                .setContentText("Grayscale monitor active")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(this)
                .setContentTitle("MonoScreen")
                .setContentText("Grayscale monitor active")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
        }

        startForeground(1001, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(screenReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        setSystemSaturation(50)
        applyGrayscale()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
