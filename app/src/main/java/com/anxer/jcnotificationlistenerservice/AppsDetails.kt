package com.anxer.jcnotificationlistenerservice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext

@Composable
fun appDetails(): SnapshotStateList<AppsList> {

    val myContext = LocalContext.current


    val appNotificationDetails = remember {
        mutableStateListOf(
            AppsList(
                appName = myContext.getString(R.string.insta_app_name),
                appPackage = myContext.getString(R.string.insta_app_package),
                appMessage = "${myContext.getString(R.string.app_notify_message)} ${myContext.getString(R.string.insta_app_name)}"
            ),
            AppsList(
                appName = myContext.getString(R.string.whatsapp_app_name),
                appPackage = myContext.getString(R.string.whatsapp_app_package),
                appMessage = "${myContext.getString(R.string.app_notify_message)} ${myContext.getString(R.string.whatsapp_app_name)}"
            ),
            AppsList(
                appName = myContext.getString(R.string.message_app_name),
                appPackage = myContext.getString(R.string.message_app_package),
                appMessage = "${myContext.getString(R.string.app_notify_message)} ${myContext.getString(R.string.message_app_name)}"
            ),
            AppsList(
                appName = myContext.getString(R.string.my_app_name),
                appPackage = myContext.getString(R.string.my_app_package),
                appMessage = "${myContext.getString(R.string.app_notify_message)} ${myContext.getString(R.string.my_app_name)}"
            )
        )
    }
    return appNotificationDetails
}