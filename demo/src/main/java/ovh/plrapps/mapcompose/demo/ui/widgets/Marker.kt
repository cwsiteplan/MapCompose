package ovh.plrapps.mapcompose.demo.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ovh.plrapps.mapcompose.demo.R

@Composable
fun Marker() = Icon(
    painter = painterResource(id = R.drawable.map_marker),
    contentDescription = null,
    modifier = Modifier.size(50.dp),
    tint = Color(0xCC2196F3)
)

@Composable
fun CircleMarker(size: Dp) {
    Box(Modifier.size(size).clip(CircleShape).background(Color.Red))
}