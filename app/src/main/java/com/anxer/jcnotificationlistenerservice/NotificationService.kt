package com.anxer.jcnotificationlistenerservice

import android.annotation.SuppressLint
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf

class NotificationService : NotificationListenerService() {

    // val notificationManager : NotificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    @SuppressLint("QueryPermissionsNeeded")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNotificationPosted(sbn: StatusBarNotification) {

        when (sbn.packageName) {
            "com.instagram.android" -> {
                    Utils.setInstaBadgeCount(sbn.notification.number)
                    Log.d(
                        "Notification_PostedBy_Instagram",
                        "Package Name: ${sbn.packageName} and current badgeCount: ${Utils.getInstaBadgeCount()}"
                    )
                    //saveNotificationCount()
            }

            "com.whatsapp" -> {
                Utils.setWhatsAppBadgeCount(sbn.notification.number)
                Log.d(
                    "Notification_PostedBy_WhatsApp",
                    "Package Name: ${sbn.packageName} and current badgeCount: ${Utils.getWhatsAppBadgeCount()}"
                )
                //saveNotificationCount()
            }

            "com.google.android.apps.messaging" -> {
                Utils.setMessageAppBadgeCount(sbn.notification.number)
                Log.d(
                    "Notification_PostedBy_MessageApp",
                    "Package Name: ${sbn.packageName} and current badgeCount: ${Utils.getMessageAppBadgeCount()}"
                )
                //saveNotificationCount()
            }

            else -> {
                Utils.setOtherAppBadgeCount(this.activeNotifications.size)
                Log.d(
                    "Notification_Posted_ByOthers",
                    "Package Name: ${sbn.packageName} and current badgeCount: ${Utils.getOtherAppBadgeCount()}"
                )
                //saveNotificationCount()
            }
        }
    }


    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        when (sbn.packageName) {
            "com.instagram.android" -> {
                Utils.setInstaBadgeCount(0)
                Log.d(
                    "Notification_Removed_FromInstagram",
                    "Removed app: ${sbn.packageName} notification and current badgeCount: ${Utils.getInstaBadgeCount()}"
                )
                // saveNotificationCount()
            }

            "com.whatsapp" -> {
                Utils.setWhatsAppBadgeCount(0)
                Log.d(
                    "Notification_Removed_FromWhatsApp",
                    "Removed app: ${sbn.packageName} notification and current badgeCount: ${Utils.getWhatsAppBadgeCount()}"
                )
                //saveNotificationCount()
            }

            "com.google.android.apps.messaging" -> {
                Utils.setMessageAppBadgeCount(0)
                Log.d(
                    "Notification_Removed_FromMessaging",
                    "Removed app: ${sbn.packageName} notification and current badgeCount: ${Utils.getMessageAppBadgeCount()}"
                )
            }

            else -> {
                Utils.setOtherAppBadgeCount(0)
                Log.d(
                    "Notification_Removed_FromOthers",
                    "Removed app name: ${sbn.packageName} and current badgeCount: ${Utils.getOtherAppBadgeCount()}"
                )
            }
        }
    }

    object Utils {

        private val instaBadgeCount = mutableIntStateOf(0)
        private val whatsAppBadgeCount = mutableIntStateOf(0)
        private val messageAppBadgeCount = mutableIntStateOf(0)
        private val otherAppBadgeCount = mutableIntStateOf(0)

        fun setInstaBadgeCount(badgeCount: Int) {
            instaBadgeCount.intValue = badgeCount
        }

        fun getInstaBadgeCount(): Int {
            return instaBadgeCount.intValue
        }

        fun setWhatsAppBadgeCount(badgeCount: Int) {
            whatsAppBadgeCount.intValue = badgeCount
        }

        fun getWhatsAppBadgeCount(): Int {
            return whatsAppBadgeCount.intValue
        }

        fun setMessageAppBadgeCount(badgeCount: Int) {
            messageAppBadgeCount.intValue = badgeCount
        }

        fun getMessageAppBadgeCount(): Int {
            return messageAppBadgeCount.intValue
        }

        fun setOtherAppBadgeCount(badgeCount: Int) {
            otherAppBadgeCount.intValue = badgeCount
        }

        fun getOtherAppBadgeCount(): Int {
            return otherAppBadgeCount.intValue
        }
    }

    /* private fun saveNotificationCount() {
         sharedPreferences = this.getSharedPreferences(
             "Save_Notification_Count",
             MODE_PRIVATE
         )
         val editor = sharedPreferences.edit()

         editor.putInt(
             "instaBadgeCount",
             Utils.getInstaBadgeCount()
         )

         editor.putInt(
             "whatsAppBadgeCount",
             Utils.getWhatsAppBadgeCount()
         )

         editor.putInt(
             "otherAppBadgeCount",
             Utils.getOtherAppBadgeCount()
         )

         editor.apply()

         Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
     }*/
}
