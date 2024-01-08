package ovh.plrapps.mapcompose.demo.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ovh.plrapps.mapcompose.demo.viewmodels.SimpleDemoVM
import ovh.plrapps.mapcompose.ui.MapUI
import androidx.lifecycle.viewmodel.compose.viewModel
import ovh.plrapps.mapcompose.api.onMarkerClick
import ovh.plrapps.mapcompose.ui.state.MapState
import kotlin.random.Random

@Composable
fun MapDemoSimple(
    modifier: Modifier = Modifier, viewModel: SimpleDemoVM = viewModel()
) {
    var myData by remember { mutableStateOf(MyData(emptyList())) }

    Box {
        MyMapComposable(myData = myData, mapState = viewModel.state)
        Button(onClick = {  myData = MyData(myData.someList + "test ${Random.nextInt()}") }) {
            Text("add data")
        }
    }
}

@Composable
fun MyMapComposable(modifier: Modifier = Modifier, myData: MyData, mapState: MapState) {
    LaunchedEffect(Unit) {
        mapState.onMarkerClick { id, x, y ->
            Log.d("Bug", "my marker state: ${myData.someList.size}")
        }
    }

    MapUI(modifier, state = mapState)
}

data class MyData(
    val someList: List<String>
)