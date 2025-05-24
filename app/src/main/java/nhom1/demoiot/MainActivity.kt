package nhom1.demoiot

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.database.*
import nhom1.demoiot.ui.theme.Theme

class MainActivity : ComponentActivity() {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var tempRef: DatabaseReference
    private lateinit var humRef: DatabaseReference
    private lateinit var ledRef: DatabaseReference
    private lateinit var deviceConnectionRef: DatabaseReference
    private lateinit var wifiSignalRef: DatabaseReference
    private lateinit var firebaseConnectionRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        tempRef = firebaseDatabase.getReference("sensors/temperature")
        humRef = firebaseDatabase.getReference("sensors/humidity")
        ledRef = firebaseDatabase.getReference("led/state")

        // ✅ SỬA 2 DÒNG NÀY
        deviceConnectionRef = firebaseDatabase.getReference("device/connected")
        wifiSignalRef = firebaseDatabase.getReference("device/signal")

        firebaseConnectionRef = firebaseDatabase.getReference(".info/connected")

        setContent {
            Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var temperature by remember { mutableDoubleStateOf(0.0) }
                    var humidity by remember { mutableDoubleStateOf(0.0) }
                    var isLedOn by remember { mutableStateOf(false) }
                    var isDeviceConnected by remember { mutableStateOf(false) }
                    var wifiStrength by remember { mutableStateOf(0) }
                    var isFirebaseOnline by remember { mutableStateOf(false) }
                    var lastTempUpdate by remember { mutableLongStateOf(0L) }
                    var lastHumUpdate by remember { mutableLongStateOf(0L) }

                    val onLedToggle: () -> Unit = {
                        ledRef.setValue(if (isLedOn) 0 else 1)
                    }

                    firebaseConnectionRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            isFirebaseOnline = snapshot.getValue(Boolean::class.java) ?: false
                            if (!isFirebaseOnline) {
                                isDeviceConnected = false
                                wifiStrength = 0
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("Firebase", "Connection check failed: ${error.message}")
                        }
                    })

                    tempRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            temperature = snapshot.getValue(Double::class.java) ?: 0.0
                            lastTempUpdate = System.currentTimeMillis()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showErrorToast(error.message)
                        }
                    })

                    humRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            humidity = snapshot.getValue(Double::class.java) ?: 0.0
                            lastHumUpdate = System.currentTimeMillis()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showErrorToast(error.message)
                        }
                    })

                    ledRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            isLedOn = snapshot.getValue(Int::class.java) == 1
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showErrorToast(error.message)
                        }
                    })

                    deviceConnectionRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            isDeviceConnected = isFirebaseOnline && (snapshot.getValue(Boolean::class.java) ?: false)
                            Log.d("Connection", "Status updated: $isDeviceConnected")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showErrorToast(error.message)
                            isDeviceConnected = false
                        }
                    })

                    wifiSignalRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            wifiStrength = if (isFirebaseOnline && snapshot.exists()) {
                                snapshot.getValue(Int::class.java) ?: 0
                            } else {
                                0
                            }
                            Log.d("Signal", "Strength updated: $wifiStrength")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showErrorToast(error.message)
                            wifiStrength = 0
                        }
                    })

                    DashboardScreen(
                        temperature = temperature,
                        humidity = humidity,
                        ledState = isLedOn,
                        isDeviceConnected = isDeviceConnected,
                        signalStrength = wifiStrength,
                        lastTemperatureUpdate = lastTempUpdate,
                        lastHumidityUpdate = lastHumUpdate,
                        onToggleLed = onLedToggle
                    )
                }
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Theme {
        DashboardScreen(
            temperature = 25.0,
            humidity = 60.0,
            ledState = true,
            isDeviceConnected = true,
            signalStrength = 85,
            lastTemperatureUpdate = System.currentTimeMillis(),
            lastHumidityUpdate = System.currentTimeMillis(),
            onToggleLed = {}
        )
    }
}
