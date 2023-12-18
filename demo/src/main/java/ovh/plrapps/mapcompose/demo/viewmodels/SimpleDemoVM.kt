package ovh.plrapps.mapcompose.demo.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import ovh.plrapps.mapcompose.api.*
import ovh.plrapps.mapcompose.demo.providers.makeTileStreamProvider
import ovh.plrapps.mapcompose.demo.ui.widgets.CircleMarker
import ovh.plrapps.mapcompose.ui.state.MapState

class SimpleDemoVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider = makeTileStreamProvider(application.applicationContext)

    val state: MapState by mutableStateOf(
        MapState(4, 4096, 4096) {
            scale(1.2f)
        }.apply {
            maxScale = 100f
            addLayer(tileStreamProvider)
            addMarker(id = "circle",0.5,0.5) {
                CircleMarker(size = 450.dp * scale)
            }
            shouldLoopScale = true
            enableRotation()
        }
    )
}