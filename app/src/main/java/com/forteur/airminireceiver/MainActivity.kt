package com.forteur.airminireceiver

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.sheentech.apsdk.AirPlayConfig
import com.sheentech.apsdk.AirPlayServer
import com.sheentech.apsdk.AirPlaySession
import com.sheentech.apsdk.IAirPlayHandler
import com.sheentech.apsdk.IAirPlayMirroringHandler
import kotlinx.coroutines.launch
import androidx.compose.material3.Text


class MainActivity : AppCompatActivity() {


    private lateinit var airPlayServer: AirPlayServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set UI con Jetpack Compose
        setContent {
            AirPlayScreen()
        }

        // Avvia il server AirPlay
        startAirPlayServer()
    }

    private fun startAirPlayServer() {
        Log.d("AirPlayReceiver", "🔵 Avvio del server AirPlay...")

        // Creare la configurazione AirPlay
        val config = AirPlayConfig.defaultInstance().apply {
            setName("Android AirPlay Receiver")
//            setModel("AppleTV3,2") // Finge di essere un Apple TV 3
//            setFeatures(0x527FFFF7) // Abilita AirPlay Mirroring
        }

        // Creare il server AirPlay
        airPlayServer = AirPlayServer(applicationContext).apply {
            setConfig(config)
            setHandler(object : IAirPlayHandler {
                override fun on_session_begin(session: AirPlaySession) {
                    Log.d("AirPlayReceiver", "🔵 Sessione iniziata: ${session.getSessionId()}")
                }

                override fun on_session_end(session_id: Long) {
                    Log.d("AirPlayReceiver", "⚪ Sessione terminata: $session_id")
                }
            })
        }

        // Avvia il server
        if (airPlayServer.start()) {
            Log.d("AirPlayReceiver", "✅ Server AirPlay avviato sulla porta: ${airPlayServer.getServicePort()}")
        } else {
            Log.e("AirPlayReceiver", "❌ Errore nell'avvio del server AirPlay")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AirPlayReceiver", "🔴 Arresto del server AirPlay...")
        airPlayServer.stop()
    }
}

@Composable
fun AirPlayScreen() {
    val context = LocalContext.current
    var session by remember { mutableStateOf<AirPlaySession?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("AirPlay Receiver Avviato", color = Color.White)

        Spacer(modifier = Modifier.height(20.dp))

        AndroidView(
            factory = { createSurfaceView(context, session) },
            modifier = Modifier.fillMaxSize()
        )
    }
}

fun createSurfaceView(context: Context, session: AirPlaySession?): SurfaceView {
    return SurfaceView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                session?.setMirrorHandler(object : IAirPlayMirroringHandler {
                    override fun on_video_stream_started() {
                        Log.d("AirPlayReceiver", "🔵 Streaming video iniziato")
                    }

                    override fun on_video_stream_codec(p0: ByteArray?) {
                        TODO("Not yet implemented")
                    }

                    override fun on_video_stream_data(p0: ByteArray?, p1: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun on_video_stream_heartbeat() {
                        TODO("Not yet implemented")
                    }

                    override fun on_video_stream_stopped() {
                        Log.d("AirPlayReceiver", "🔴 Streaming video interrotto")
                    }

                    override fun on_audio_set_volume(p0: Float, p1: Float) {
                        TODO("Not yet implemented")
                    }

                    override fun on_audio_set_progress(p0: Float, p1: Long, p2: Long, p3: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun on_audio_set_cover(p0: String?, p1: ByteArray?) {
                        TODO("Not yet implemented")
                    }

                    override fun on_audio_set_meta_data(p0: ByteArray?) {
                        TODO("Not yet implemented")
                    }

                    override fun on_audio_stream_started(p0: Int) {
                        TODO("Not yet implemented")
                    }

                    override fun on_audio_stream_data(p0: ByteArray?, p1: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun on_audio_stream_stopped() {
                        TODO("Not yet implemented")
                    }
                })
            }


            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
            override fun surfaceDestroyed(holder: SurfaceHolder) {}
        })
    }
}
