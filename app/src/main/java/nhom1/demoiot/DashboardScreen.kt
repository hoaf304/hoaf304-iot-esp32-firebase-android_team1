package nhom1.demoiot

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import nhom1.demoiot.ui.PieChart
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    temperature: Double,
    humidity: Double,
    ledState: Boolean,
    isDeviceConnected: Boolean,
    signalStrength: Int,
    onToggleLed: () -> Unit,
    lastTemperatureUpdate: Long,
    lastHumidityUpdate: Long
) {
    var visible by remember { mutableStateOf(false) }
    var lastUpdateTime by remember { mutableStateOf(currentTimeString()) }

    LaunchedEffect(temperature, humidity, ledState, isDeviceConnected, signalStrength) {
        lastUpdateTime = currentTimeString()
    }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Dashboard,
                            contentDescription = "Dashboard Icon",
                            tint = Color(0xFF00FFFF),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "IoT Dashboard",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1F1F1F),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(600)) + slideInVertically(),
                exit = fadeOut()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        PieChart(
                            percentage = temperature.toFloat().coerceIn(0f, 100f),
                            title = "Temp",
                            color = Color(0xFF00FFFF)
                        )
                        PieChart(
                            percentage = humidity.toFloat().coerceIn(0f, 100f),
                            title = "Humidity",
                            color = Color(0xFF64B5F6)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Last update: $lastUpdateTime",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.End).padding(end = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LedControlCard(ledState = ledState, onToggleLed = onToggleLed)

                    Spacer(modifier = Modifier.height(16.dp))

                    ExtraInfoCard(
                        title = "Connection",
                        value = if (isDeviceConnected) "Online" else "Offline",
                        color = if (isDeviceConnected) Color(0xFF00C853) else Color(0xFFD32F2F),
                        icon = if (isDeviceConnected) Icons.Filled.Wifi else Icons.Filled.WifiOff
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ExtraInfoCard(
                        title = "Signal Strength",
                        value = when {
                            !isDeviceConnected -> "No signal"
                            signalStrength > 80 -> "Excellent (${signalStrength}%)"
                            signalStrength > 60 -> "Good (${signalStrength}%)"
                            signalStrength > 40 -> "Fair (${signalStrength}%)"
                            else -> "Weak (${signalStrength}%)"
                        },
                        color = when {
                            !isDeviceConnected -> Color.Gray
                            signalStrength > 80 -> Color(0xFF00C853)
                            signalStrength > 60 -> Color(0xFF8BC34A)
                            signalStrength > 40 -> Color(0xFFFFC107)
                            else -> Color(0xFFF44336)
                        },
                        icon = Icons.Filled.SignalCellularAlt
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ClockCard()
                }
            }
        }
    }
}

@Composable
fun ExtraInfoCard(
    title: String,
    value: String,
    color: Color = Color.White,
    icon: ImageVector? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = title,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text = title, color = Color.Gray, fontSize = 14.sp)
            }
            Text(text = value, color = color, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun LedControlCard(ledState: Boolean, onToggleLed: () -> Unit) {
    val icon = if (ledState) Icons.Filled.Lightbulb else Icons.Outlined.Lightbulb
    val glowColor = if (ledState) Color(0xFFFFC107) else Color(0xFF555555)
    val background = if (ledState) Color(0x33FFC107) else Color(0x22AAAAAA)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .shadow(
                            elevation = if (ledState) 20.dp else 2.dp,
                            shape = CircleShape,
                            ambientColor = glowColor,
                            spotColor = glowColor
                        )
                        .background(background, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "LED Status",
                        tint = glowColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "LED Status",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (ledState) "ON" else "OFF",
                        color = glowColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Switch(
                checked = ledState,
                onCheckedChange = { onToggleLed() },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color(0xFFFFC107),
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun ClockCard() {
    var currentTime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        while (true) {
            currentTime = formatter.format(Date())
            delay(1000)
        }
    }

    ExtraInfoCard(title = "Time", value = currentTime)
}

fun currentTimeString(): String {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}