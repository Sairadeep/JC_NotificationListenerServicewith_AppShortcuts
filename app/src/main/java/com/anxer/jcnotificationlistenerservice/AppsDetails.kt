package com.anxer.jcnotificationlistenerservice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
fun appDetails(): SnapshotStateList<AppsList> {

    val appNotificationDetails = remember {
        mutableStateListOf(
            AppsList(
                appName = "Instagram",
                appPackage = "com.instagram.android",
                appIcon = R.drawable.insta,
                appMessage = "You have a notification from Instagram"
            ),
            AppsList(
                appName = "Whatsapp",
                appPackage = "com.whatsapp",
                appIcon = R.drawable.whatsapp,
                appMessage = "You have a notification from Whatsapp"
            ),
            AppsList(
                appName = "Messages",
                appPackage = "com.google.android.apps.messaging",
                appIcon = R.drawable.baseline_message_24,
                appMessage = "You have a notification from Messages app"
            ),
            AppsList(
                appName = "Notification Badge",
                appPackage = "com.anxer.notificationbadge",
                appIcon = R.drawable.baseline_notifications_24,
                appMessage = "You have notification from other apps."
            )
        )
    }
    return appNotificationDetails
}