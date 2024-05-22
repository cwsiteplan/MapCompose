package ovh.plrapps.mapcompose.demo.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import ovh.plrapps.mapcompose.api.ExperimentalClusteringApi
import ovh.plrapps.mapcompose.api.addClusterer
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.scale
import ovh.plrapps.mapcompose.demo.R
import ovh.plrapps.mapcompose.demo.providers.makeTileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import ovh.plrapps.mapcompose.ui.state.markers.model.RenderingStrategy


/**
 * Shows how to define and use a marker clusterer.
 */
@OptIn(ExperimentalClusteringApi::class)
class MarkersClusteringVM(application: Application) : AndroidViewModel(application) {
    private val tileStreamProvider = makeTileStreamProvider(application.applicationContext)

    val state = MapState(4, 4096, 4096) {
        scale(0.2f)
        maxScale(5000f)
        scroll(0.5, 0.5)
    }.apply {
        addLayer(tileStreamProvider)
    }

    init {
        /* Add a marker clusterer to manage markers. In this example, we use "default" for the id */
        state.addClusterer("default") { ids ->
            {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(Color.Green)
                )
            }
        }

        /* Add some markers to the map, using the same clusterer id we just defined (if a marker
         * is added without any clusterer, it won't be managed by any clusterer)*/
        listOf(
            0.540850668617005 to 0.45718904964459506,
            0.4596102437479404 to 0.4996550971206928,
            0.48772307487417915 to 0.4950777690860227,
            0.5343055501967795 to 0.34073990562820683,
            0.48753737719552426 to 0.4952807666849729,
            0.497616192033747 to 0.34551293793754934,
            0.39220828358629267 to 0.4020202746384823,
            0.5262990038301244 to 0.4641094016734508,
            0.4766794654443037 to 0.4334603261382928,
            0.4766794654443037 to 0.4334603261382928,
            0.5267374753538467 to 0.701762264515675,
            0.5267374753538467 to 0.701762264515675,
            0.6210934086359277 to 0.3403301583764613,
            0.46539791206303865 to 0.491167179526875,
            0.45027398673618524 to 0.4659832466693633,
            0.45027398673618524 to 0.4659832466693633,
            0.5325690427844274 to 0.45569679856943474,
        ).forEachIndexed { i, pair ->
            state.addMarker(
                "marker-regular-$i", pair.first, pair.second,
                renderingStrategy = RenderingStrategy.Clustering("default"),
                clickable = false, relativeOffset = Offset(-.5f, -.5f)
            ) {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(Color.Red))
            }
        }

        state.addMarker("accuracy", 0.5, 0.5, zIndex = 10000f) {
            val size = 20 * state.scale
            SideEffect {
                Log.d("Accuracy Circle", "circle size: $size")
            }
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(Color.Magenta)
                    .requiredSize(size.dp)
            )
        }
    }

    @Composable
    private fun Marker() {
        Icon(
            painter = painterResource(id = R.drawable.map_marker),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = Color(0xEE2196F3)
        )
    }

    @Composable
    private fun Cluster(size: Int) {
        /* Here we can customize the cluster style */
        Box(
            modifier = Modifier
                .background(
                    Color(0x992196F3),
                    shape = CircleShape
                )
                .size(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = size.toString(), color = Color.White)
        }
    }
}
