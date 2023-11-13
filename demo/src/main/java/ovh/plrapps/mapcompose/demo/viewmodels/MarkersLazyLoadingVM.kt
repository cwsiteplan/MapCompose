package ovh.plrapps.mapcompose.demo.viewmodels

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import ovh.plrapps.mapcompose.api.ExperimentalClusteringApi
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addLazyLoader
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.onMarkerClick
import ovh.plrapps.mapcompose.api.removeAllMarkers
import ovh.plrapps.mapcompose.api.shouldLoopScale
import ovh.plrapps.mapcompose.ui.layout.Forced
import ovh.plrapps.mapcompose.ui.state.MapState
import ovh.plrapps.mapcompose.ui.state.markers.model.RenderingStrategy
import ovh.plrapps.mapcompose.utils.Point
import kotlin.random.Random

/**
 * Shows how to define and use a marker lazy-loader.
 */
@OptIn(ExperimentalClusteringApi::class)
class MarkersLazyLoadingVM(application: Application) : AndroidViewModel(application) {

    var viewRotation = 0f

    val points = mutableListOf<Point>()

    fun onRotationUpdate(rotation: Float) {
        viewRotation = rotation
        state.removeAllMarkers()
        addMarkers()
    }

    private val tileStreamProvider =
        ovh.plrapps.mapcompose.demo.providers.makeTileStreamProvider(application.applicationContext)

    val state: MapState by mutableStateOf(
        MapState(4, 4096, 4096) {
            minimumScaleMode(Forced(1f))
            scale(1f)
            maxScale(4f)
            scroll(0.5, 0.5)
        }.apply {
            addLayer(tileStreamProvider)
            shouldLoopScale = true
        }
    )

    init {
        /* Add a marker lazy loader. In this example, we use "default" for the id */
        state.addLazyLoader("default")

        generateLocations()
        addMarkers()
        state.onMarkerClick { id, x, y ->
            println("marker click $id $x $y")
        }
    }

    private fun generateLocations() {
        points.clear()
        repeat(200) { i ->
            points.add(Point(Random.nextDouble(), Random.nextDouble()))
        }
    }

    private fun addMarkers() {
        points.mapIndexed { index, point ->
            /* Notice how we set the rendering strategy to lazy loading with the same id */
            state.addMarker(
                "marker-$index", point.x, point.y,
                renderingStrategy = RenderingStrategy.LazyLoading(lazyLoaderId = "default")
            ) {
                Box(
                    Modifier
                        .size(40.dp)
                        .background(Color.Blue)
                ) {
                    Icon(
                        tint = Color.White,
                        painter = rememberVectorPainter(image = Icons.Rounded.ArrowBack),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .rotate(viewRotation),
                    )
                }
            }
        }

    }
}

