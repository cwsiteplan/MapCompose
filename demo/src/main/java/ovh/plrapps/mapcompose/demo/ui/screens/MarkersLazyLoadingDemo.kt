package ovh.plrapps.mapcompose.demo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ovh.plrapps.mapcompose.demo.viewmodels.MarkersLazyLoadingVM
import ovh.plrapps.mapcompose.ui.MapUI

@Composable
fun MarkersLazyLoadingDemo(
    modifier: Modifier = Modifier,
    viewModel: MarkersLazyLoadingVM = viewModel()
) {
    var rotation by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(rotation) {
        viewModel.onRotationUpdate(rotation)
    }

    Box {
        MapUI(modifier.padding(bottom = 80.dp), state = viewModel.state)
        Slider(
            value = rotation, onValueChange = {
                rotation = it
            }, valueRange = 0f..360f, modifier = Modifier.padding(16.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }

}