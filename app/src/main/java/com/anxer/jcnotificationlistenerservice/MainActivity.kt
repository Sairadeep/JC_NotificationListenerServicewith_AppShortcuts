package com.anxer.jcnotificationlistenerservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anxer.jcnotificationlistenerservice.ui.theme.JCNotificationListenerServiceTheme

class MainActivity : ComponentActivity() {

    private val listenerComponent =
        ComponentName("com.anxer.jcnotificationlistenerservice", "NotificationService")
    private var permissionGranted = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val isPermissionGranted = checkNotificationListenerPermission(this, listenerComponent)

        setContent {
            JCNotificationListenerServiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JCNLS()
                }
            }
        }
        if (!isPermissionGranted && !permissionGranted) {
            val permissionIntentLaunch =
                Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            this.startActivity(permissionIntentLaunch)
            permissionGranted = true
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun JCNLS() {

    val notifiedAppDetails = appDetails()
    val textOnNotified = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "JC Navigation Listener Service",
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
                                    Image(
                                        painter = painterResource(id = nta.appIcon),
                                        contentDescription = nta.appName,
                                        modifier = Modifier.size(80.dp),
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center
                                    )
                                    val badgeCount: String = when (index) {
                                        0 -> {
                                            NotificationService.Utils.getInstaBadgeCount()
                                                .toString()
                                        }


                                        1 -> NotificationService.Utils.getWhatsAppBadgeCount()
                                            .toString()

                                        2 ->
                                            NotificationService.Utils.getMessageAppBadgeCount()
                                                .toString()

                                        else ->
                                            NotificationService.Utils.getOtherAppBadgeCount()
                                                .toString()

                                    }

                                    when (badgeCount.toInt()) {
                                        0 -> textOnNotified.value = false
                                        else -> textOnNotified.value = true
                                    }

                                    // if(index == 1) textOnBadge.value = badgeCount else textOnBadge.value = "8"
                                    BadgedBox(
                                        badge = {
                                            Text(
                                                text = badgeCount,
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


fun checkNotificationListenerPermission(
    context: Context,
    listenerComponent: ComponentName
): Boolean {
    return Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        ?.contains(listenerComponent.flattenToString()) == true
}

