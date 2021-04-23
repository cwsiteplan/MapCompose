package ovh.plrapps.mapcompose.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.rotation
import ovh.plrapps.mapcompose.api.shouldLoopScale
import ovh.plrapps.mapcompose.api.smoothRotateTo
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.InputStream

class RotationDemoViewModel(application: Application) : AndroidViewModel(application) {
    val appContext: Context by lazy {
        getApplication<Application>().applicationContext
    }
    private val tileStreamProvider = object : TileStreamProvider {
        override suspend fun getTileStream(row: Int, col: Int, zoomLvl: Int): InputStream? {
            return try {
                appContext.assets?.open("tiles/mont_blanc/$zoomLvl/$row/$col.jpg")
            } catch (e: Exception) {
                null
            }
        }
    }

    val state: MapState by mutableStateOf(
        MapState(4, 4096, 4096, tileStreamProvider).apply {
            shouldLoopScale = true
            addMarker("red", 0.5, 0.5) {
                Box(modifier = Modifier.background(Color.Red).size(25.dp))
            }
        }
    )

    fun onRotate() {
        state.smoothRotateTo(state.rotation + 90f)
    }
}