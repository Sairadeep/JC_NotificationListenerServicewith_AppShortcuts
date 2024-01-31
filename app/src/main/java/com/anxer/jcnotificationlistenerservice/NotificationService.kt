package com.anxer.jcnotificationlistenerservice

import android.annotation.SuppressLint
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf

class NotificationService : NotificationListenerService() {
    @SuppressLint("QueryPermissionsNeeded")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNotificationPosted(sbn: StatusBarNotification) {

        when (sbn.packageName) {
            this.getString(R.string.insta_app_package) -> {
                Utils.setInstaBadgeCount(sbn.notification.number)
                Log.d(
                    this.getString(R.string.notification_posted_log),
                    "${this.getString(R.string.notification_posted_app)} ${sbn.packageName} ${this.getString(R.string.notification_badge_Count)} ${Utils.getInstaBadgeCount()}"
                )
            }

            this.getString(R.string.whatsapp_app_package) -> {
                Utils.setWhatsAppBadgeCount(sbn.notification.number)
                Log.d(
                    this.getString(R.string.notification_posted_log),
                    "${this.getString(R.string.notification_posted_app)} ${sbn.packageName} ${this.getString(R.string.notification_badge_Count)} ${Utils.getWhatsAppBadgeCount()}"
                )
            }

            this.getString(R.string.message_app_package) -> {
                Utils.setMessageAppBadgeCount(sbn.notification.number)
                Log.d(
                    this.getString(R.string.notification_posted_log),
                    "${this.getString(R.string.notification_posted_app)} ${sbn.packageName} ${this.getString(R.string.notification_badge_Count)} ${Utils.getMessageAppBadgeCount()}"
                )
            }

            else -> {
                Utils.setOtherAppBadgeCount(this.activeNotifications.size)
                Log.d(
                    this.getString(R.string.notification_posted_log),
                    "${this.getString(R.string.notification_posted_app)} ${sbn.packageName} ${this.getString(R.string.notification_badge_Count)} ${Utils.getOtherAppBadgeCount()}"
                )
            }
        }
    }


    /*
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        when (sbn.packageName) {
            this.getString(R.string.insta_app_package) -> {
                Utils.setInstaBadgeCount(0)
                Log.d(
                    this.getString(R.string.notification_removed_log),
                    "${this.getString(R.string.notification_removed_app)} ${sbn.packageName} ${this.getString(R.string.notification_badge_Count)} ${Utils.getInstaBadgeCount()}"
                )
            }

            this.getString(R.string.whatsapp_app_package) -> {
                Utils.setWhatsAppBadgeCount(0)
                Log.d(
                    this.getString(R.string.notification_removed_log),
                    "${this.getString(R.string.notification_removed_app)} ${sbn.packageName} ${this.getString(R.string.notification_badge_Count)} ${Utils.getWhatsAppBadgeCount()}"
                )
            }

            this.getString(R.string.message_app_package) -> {
                Utils.setMessageAppBadgeCount(0)
                Log.d(
                    this.getString(R.string.notification_removed_log),
                    "${this.getString(R.string.notification_removed_app)} ${sbn.packageName} ${this.getString(R.string.notification_badge_Count)} ${Utils.getMessageAppBadgeCount()}"
                )
            }

            else -> {
                Utils.setOtherAppBadgeCount(0)
                Log.d(
                    this.getString(R.string.notification_removed_log),
                    "${this.getString(R.string.notification_removed_app)} ${sbn.packageName} ${this.getString(R.string.notification_badge_Count)} ${Utils.getOtherAppBadgeCount()}"
                )
            }
        }
    }*/

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
}
