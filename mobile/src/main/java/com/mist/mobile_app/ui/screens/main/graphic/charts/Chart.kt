package com.mist.mobile_app.ui.screens.main.graphic.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.api.models.PressureRecordModel
import com.mist.common.ui.PDColors
import com.mist.mobile_app.ui.theme.PDTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.core.cartesian.DefaultPointConnector
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

enum class PDChartColors(
    val colors: List<Color>
) {
    BLOOD_PRESSURE(
        colors = listOf(
            PDColors.darkGreen,
            PDColors.lightBlue
        )
    ),
    HEART_RATE(
        colors = listOf(
            PDColors.error
        )
    )
}

sealed class PDChartType(
    val colors: List<Color>
) {
    abstract val records: List<PressureRecordModel>

    data class BloodPressure(
        override val records: List<PressureRecordModel>
    ) : PDChartType(colors = PDChartColors.BLOOD_PRESSURE.colors)

    data class HeartRate(
        override val records: List<PressureRecordModel>
    ) : PDChartType(colors = PDChartColors.HEART_RATE.colors)
}

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    info: PDChartType,
) {
    val modelProducer = remember { CartesianChartModelProducer.build() }

    LaunchedEffect(Unit, info) {
        withContext(Dispatchers.Default) {
            modelProducer.tryRunTransaction {
                lineSeries {
                    when (info) {
                        is PDChartType.BloodPressure -> {
                            series(
                                info.records.map {
                                    it.systolic.toFloat()
                                }
                            )
                            series(
                                info.records.map {
                                    it.diastolic.toFloat()
                                }
                            )
                        }

                        is PDChartType.HeartRate -> {
                            series(info.records.map { it.pulse.toFloat() })
                        }
                    }
                }
            }
        }
    }

    CartesianChartHost(
        modifier = modifier
            .height(
                300.dp
            ),
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lines = info.colors.map { color ->
                    rememberLineSpec(
                        shader = DynamicShader.color(color),
                        pointConnector = DefaultPointConnector(cubicStrength = 0f),
                    )
                },
                spacing = 50.dp
            ),
            bottomAxis = rememberBottomAxis(
                valueFormatter = DayFormatValueFormatter(
                    records = info.records
                )
            ),
            startAxis = rememberStartAxis(),
            topAxis = rememberTopAxis(),
            endAxis = rememberEndAxis(),
            legend = rememberLegend(
                colors = info.colors
            )
        ),
        marker = rememberMarker(),
        modelProducer = modelProducer,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewChart() {
    PDTheme {
        val records = List(100) {
            PressureRecordModel(
                pressureRecordUUID = UUID.randomUUID(),
                dateTimeRecord = LocalDateTime.now().plusDays(it.toLong()),
                systolic = Random.nextInt(100, 160),
                diastolic = Random.nextInt(70, 100),
                pulse = Random.nextInt(60, 120),
                note = ""
            )
        }

        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
        ) {
            Chart(
                info = PDChartType.HeartRate(
                    records = records
                )
            )
            Chart(
                info = PDChartType.BloodPressure(
                    records = records
                )
            )
        }
    }
}