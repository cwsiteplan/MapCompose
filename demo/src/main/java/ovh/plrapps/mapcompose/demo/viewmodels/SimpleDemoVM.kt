package ovh.plrapps.mapcompose.demo.viewmodels

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.setScrollOffsetRatio
import ovh.plrapps.mapcompose.api.setTilePadding
import ovh.plrapps.mapcompose.demo.providers.makeTileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState

class SimpleDemoVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider = makeTileStreamProvider(application.applicationContext)

    val state: MapState by mutableStateOf(
        MapState(6, 8192, 8192)
            .apply {
                addLayer(tileStreamProvider)
                setTilePadding(paddingX = 685, paddingY = 1760)
                setScrollOffsetRatio(0.5f, 0.5f)

                addMarker("test", 0.0, 0.0, relativeOffset = Offset(-0.5f, -0.5f)) {
                    Box(
                        Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.Blue)
                    )
                }
                addMarker("test2", 1.0, 1.0, relativeOffset = Offset(-0.5f, -0.5f)) {
                    Box(
                        Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                }
            }
    )
}