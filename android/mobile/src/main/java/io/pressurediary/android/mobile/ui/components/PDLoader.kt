package io.pressurediary.android.mobile.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pressurediary.android.common.ui.PDColors

@Composable
fun PDCircularLoader(
    modifier: Modifier = Modifier,
    color: Color = PDColors.black
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color
    )
}

@Composable
fun PDLoaderIndicator(
    modifier: Modifier = Modifier,
    circleColor: Color
) {
    val circleSize = 8.dp

    val density = LocalDensity.current

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val loaderSizeDp = remember { (circleSize * 6) }

    val bouncingHeightPx = remember {
        with(density) {
            val distance = (loaderSizeDp - circleSize) / 2
            distance.toPx()
        }
    }

    val circle1 = rememberCircleAnimatedFloat(
        infiniteTransition = infiniteTransition,
        durationMillis = 1200,
        delay = 0
    )
    val circle2 = rememberCircleAnimatedFloat(
        infiniteTransition = infiniteTransition,
        durationMillis = 1100,
        delay = 100
    )
    val circle3 = rememberCircleAnimatedFloat(
        infiniteTransition = infiniteTransition,
        durationMillis = 1000,
        delay = 200
    )

    Row(
        modifier = modifier.requiredSize(loaderSizeDp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = circleSize,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .graphicsLayer { translationY = -circle1.value * bouncingHeightPx }
                .background(color = circleColor, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .size(circleSize)
                .graphicsLayer { translationY = -circle2.value * bouncingHeightPx }
                .background(color = circleColor, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .size(circleSize)
                .graphicsLayer { translationY = -circle3.value * bouncingHeightPx }
                .background(color = circleColor, shape = CircleShape)

        )
    }
}

@Composable
private fun rememberCircleAnimatedFloat(
    infiniteTransition: InfiniteTransition,
    durationMillis: Int,
    delay: Int
) = infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            this.durationMillis = durationMillis
            delayMillis = delay
            0f at 0 using FastOutLinearInEasing
            1f at 300 using FastOutLinearInEasing
            0f at 600 using FastOutLinearInEasing
            0f at durationMillis using FastOutLinearInEasing
        }
    ),
    label = ""
)

@Preview
@Composable
private fun PDLoaderIndicatorPreview() {
    PDLoaderIndicator(circleColor = Color.White)
}

@Preview
@Composable
private fun PreviewPDCircularLoader() {
    PDCircularLoader()
}