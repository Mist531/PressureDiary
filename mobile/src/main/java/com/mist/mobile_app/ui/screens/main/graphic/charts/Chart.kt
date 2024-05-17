package com.mist.mobile_app.ui.screens.main.graphic.charts

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mist.common.ui.PDColors

enum class PDChartColors(
    colors: List<Color>
) {
    BLOOD_PRESSURE(
        colors = listOf(
            PDColors.error,
            PDColors.lightBlue
        )
    ),
    HEART_RATE(
        colors = listOf(
            PDColors.error
        )
    )
}

sealed class SeasonChartType {
    abstract val items: List<Any>
    abstract val colors: PDChartColors


}

@Composable
fun Chart(

) {
    /*CartesianChartHost(

    )*/
}