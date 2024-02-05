package com.anxer.jcnotificationlistenerservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import com.anxer.jcnotificationlistenerservice.ui.theme.JCNotificationListenerServiceTheme

class MainActivity : ComponentActivity() {

    private val listenerComponent =
        ComponentName(
            "com.anxer.jcnotificationlistenerservice",
            "com.anxer.jcnotificationlistenerservice.NotificationService"
        )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JCNotificationListenerServiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JCNLS()
                    Shortcut(this)
                }
            }
        }
        val isPermissionGranted = checkNotificationListenerPermission(this, listenerComponent)
        if (!isPermissionGranted) {
            val permissionIntentLaunch =
                Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            this.startActivity(permissionIntentLaunch)
        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun JCNLS() {
    val badgeCount = remember { mutableStateOf("Dummy") }
    val aIBtoIB = remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    val myContext = LocalContext.current
    val notifiedAppDetails = appDetails()
    val textOnNotified = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.my_app_name),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.purple_700)
                )
            )
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(190.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(
                    count = 4,
                    itemContent = { index ->
                        val nta = notifiedAppDetails[index]
                        Card(
                            modifier = Modifier
                                .height(350.dp)
                                .padding(top = 50.dp, start = 10.dp, end = 5.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(id = R.color.purple_500),
                                contentColor = Color.White,
                                disabledContainerColor = Color.Cyan,
                                disabledContentColor = Color.Black
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp,
                                pressedElevation = 10.dp,
                            ),
                            border = BorderStroke(3.dp, Color.Magenta)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row {
                                    when (index) {
                                        0 -> {
                                            aIBtoIB.value =
                                                imageFetchBitmap(myContext, nta.appPackage)
                                            badgeCount.value =
                                                NotificationService.Utils.getInstaBadgeCount()
                                                    .toString()
                                        }

                                        1 -> {
                                            aIBtoIB.value =
                                                imageFetchBitmap(myContext, nta.appPackage)
                                            badgeCount.value =
                                                NotificationService.Utils.getWhatsAppBadgeCount()
                                                    .toString()
                                        }

                                        2 -> {
                                            aIBtoIB.value =
                                                imageFetchBitmap(myContext, nta.appPackage)
                                            badgeCount.value =
                                                NotificationService.Utils.getMessageAppBadgeCount()
                                                    .toString()
                                        }

                                        else -> {
                                            aIBtoIB.value =
                                                imageFetchBitmap(myContext, nta.appPackage)
                                            badgeCount.value =
                                                NotificationService.Utils.getOtherAppBadgeCount()
                                                    .toString()
                                        }
                                    }

                                    aIBtoIB.value?.let {
                                        Image(
                                            bitmap = it,
                                            contentDescription = nta.appName,
                                            modifier = Modifier.size(100.dp),
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center
                                        )
                                    }
                                    when (badgeCount.value.toInt()) {
                                        0 -> textOnNotified.value = false
                                        else -> textOnNotified.value = true
                                    }

                                    BadgedBox(
                                        badge = {
                                            Text(
                                                text = badgeCount.value,
                                                textAlign = TextAlign.Right
                                            )
                                        }
                                    ) {
                                        // To be Implemented
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                if (textOnNotified.value) {
                                    Text(
                                        text = nta.appMessage,
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                )
            }
        },
        containerColor = colorResource(id = R.color.black)
    )
}

@Composable
private fun Shortcut(myContext: Context) {
    val shortCut = ShortcutInfoCompat.Builder(myContext, "shortCut1")
        .setShortLabel(stringResource(id = R.string.shortcut_shortLabel))
        .setLongLabel(stringResource(id = R.string.shortcut_LongLabel)).setRank(1)
        // Need to add dynamic shortcuts with capabilities.
        // .addCapabilityBinding(Intent.ACTION_VIEW)
        .setIcon(
            IconCompat.createWithResource(myContext, R.drawable.baseline_web_24)
        ).setIntent(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/Sairadeep?tab=repositories")
            )
        ).build()
    ShortcutManagerCompat.pushDynamicShortcut(myContext, shortCut)
    Log.d(
        "ShortCutCount",
        ShortcutManagerCompat.getMaxShortcutCountPerActivity(myContext).toString()
    )
}

fun checkNotificationListenerPermission(
    context: Context,
    listenerComponent: ComponentName
): Boolean {
    val enabledListeners =
        Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
    Log.d("ABCGS", "Enabled: $enabledListeners")
    Log.d("ABCGS", listenerComponent.flattenToString())
    val checkPermission =
        enabledListeners?.split(":")
            ?.contains(listenerComponent.flattenToString()) == true
    Log.d("ABCGS", "$checkPermission")
    return checkPermission
}

fun imageFetchBitmap(context: Context, packName: String): ImageBitmap {
    val packManager = context.packageManager
    val appInfo = packManager.getApplicationInfo(
        packName,
        PackageManager.GET_META_DATA
    )
    val appIcon = packManager.getApplicationIcon(appInfo)

    // convert drawable to bitmap
    val appIconToBitmap = (appIcon as BitmapDrawable).toBitmap()

    // convert bitmap to Image bitmap
    return appIconToBitmap.asImageBitmap()
}

