package com.mist.mobile_app.ui.screens.main.graphic.charts

import android.graphics.Typeface
import android.text.Layout
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api.models.PressureRecordModel
import com.mist.common.ui.PDColors
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.compose.common.rememberLegendItem
import com.patrykandpatrick.vico.compose.common.shape.dashed
import com.patrykandpatrick.vico.compose.common.shape.markerCornered
import com.patrykandpatrick.vico.compose.common.shape.toVicoShape
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasureContext
import com.patrykandpatrick.vico.core.cartesian.HorizontalDimensions
import com.patrykandpatrick.vico.core.cartesian.Insets
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.axis.AxisPosition
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.ChartValues
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun rememberColumnLineComponent(
    color: Color
) = rememberLineComponent(
    color = color,
    thickness = 10.dp,
    shape = RoundedCornerShape(2.dp).toVicoShape(),
    strokeWidth = 2.dp
)

@Composable
fun rememberAxisTextComponent(
    padding: Dimensions = Dimensions.Empty,
) = rememberTextComponent(
    color = PDColors.middleGrey,
    padding = padding
)

@Composable
fun rememberAxisLineComponent(
    shape: Shape = Shape.dashed(Shape.Rectangle, 4.dp, 2.dp),
) = rememberLineComponent(
    color = PDColors.middleGrey,
    shape = shape
)

@Composable
fun rememberEndAxis() = com.patrykandpatrick.vico.compose.cartesian.axis.rememberEndAxis(
    label = null,
    tick = null,
    guideline = null,
    axis = rememberAxisLineComponent()
)

@Composable
fun rememberBottomAxis(
    valueFormatter: CartesianValueFormatter = CartesianValueFormatter.decimal(),
) = com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis(
    label = rememberAxisTextComponent(),
    axis = rememberAxisLineComponent(),
    tick = null,
    guideline = rememberAxisLineComponent(),
    valueFormatter = valueFormatter
)

@Composable
fun rememberStartAxis() = com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis(
    label = rememberAxisTextComponent(
        padding = Dimensions(
            startDp = 0f,
            endDp = 4f,
            bottomDp = 0f,
            topDp = 0f,
        )
    ),
    axis = rememberAxisLineComponent(),
    tick = null,
    guideline = rememberAxisLineComponent(),
    horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Outside,
    itemPlacer = AxisItemPlacer.Vertical.step(
        shiftTopLines = true
    )
)

@Composable
fun rememberTopAxis() = com.patrykandpatrick.vico.compose.cartesian.axis.rememberTopAxis(
    label = null,
    tick = null,
    guideline = null,
    axis = rememberAxisLineComponent()
)

@Composable
fun rememberLegend(
    colors: List<Color>
) = rememberHorizontalLegend<CartesianMeasureContext, CartesianDrawContext>(
    items = colors.map { chartColor ->
        rememberLegendItem(
            icon = rememberShapeComponent(Shape.Pill, chartColor),
            label = rememberTextComponent(
                color = PDColors.black,
                textSize = 14.sp,
                typeface = Typeface.DEFAULT,
            ),
            labelText = when (chartColor) {
                PDColors.darkGreen -> "Сист."
                PDColors.error -> "Пульс"
                PDColors.lightBlue -> "Диаст."
                else -> ""
            },
        )
    },
    iconSize = 20.dp,
    iconPadding = 8.dp,
    spacing = 20.dp,
    padding = Dimensions.of(top = 8.dp),
)

class DayFormatValueFormatter(
    val records: List<PressureRecordModel>
) : CartesianValueFormatter {
    override fun format(
        value: Float,
        chartValues: ChartValues,
        verticalAxisPosition: AxisPosition.Vertical?,
    ): String = records.getOrNull(value.toInt())?.dateTimeRecord?.toChartFormat() ?: "-"
}

fun LocalDateTime.toChartFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM")
    return this.format(formatter)
}

@Composable
fun rememberComposeHorizontalLine(
    color: Color,
    value: Float,
): HorizontalLine {
    return rememberHorizontalLine(
        y = { value },
        line = rememberLineComponent(
            color = color,
            thickness = 2.dp,
            shape = Shape.dashed(Shape.Rectangle, 16.dp, 9.dp)
        ),
        labelComponent = rememberTextComponent(
            background = rememberShapeComponent(Shape.Pill, color),
            padding = Dimensions.of(
                6.dp,
                3.dp,
            ),
            margins = Dimensions.of(4.dp),
            typeface = Typeface.DEFAULT,
        ),
    )
}

@Composable
internal fun rememberMarker(
    labelPosition: DefaultCartesianMarker.LabelPosition = DefaultCartesianMarker.LabelPosition.Top,
    showIndicator: Boolean = true,
): CartesianMarker {
    val labelBackgroundShadowRadiusDp = 4f
    val labelBackgroundShadowDyDp = 2f
    val clippingFreeShadowRadiusMultiplier = 1.4f

    val labelBackgroundShape = Shape.markerCornered(Corner.FullyRounded)
    val labelBackground =
        rememberShapeComponent(labelBackgroundShape, MaterialTheme.colorScheme.surface)
            .setShadow(
                radius = labelBackgroundShadowRadiusDp,
                dy = labelBackgroundShadowDyDp,
                applyElevationOverlay = true,
            )
    val label =
        rememberTextComponent(
            color = MaterialTheme.colorScheme.onSurface,
            background = labelBackground,
            padding = Dimensions.of(8.dp, 4.dp),
            typeface = Typeface.DEFAULT,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )
    val indicatorFrontComponent = rememberShapeComponent(
        Shape.Pill, MaterialTheme.colorScheme.surface
    )
    val indicatorCenterComponent = rememberShapeComponent(Shape.Pill)
    val indicatorRearComponent = rememberShapeComponent(Shape.Pill)
    val indicator = rememberLayeredComponent(
        rear = indicatorRearComponent,
        front = rememberLayeredComponent(
            rear = indicatorCenterComponent,
            front = indicatorFrontComponent,
            padding = Dimensions.of(5.dp),
        ),
        padding = Dimensions.of(10.dp),
    )
    val guideline = rememberAxisGuidelineComponent()
    return remember(label, labelPosition, indicator, showIndicator, guideline) {
        object : DefaultCartesianMarker(
            label = label,
            labelPosition = labelPosition,
            indicator = if (showIndicator) indicator else null,
            indicatorSizeDp = 36f,
            setIndicatorColor = if (showIndicator) {
                { color ->
                    indicatorRearComponent.color = Color(color).copy(0.15f).toArgb()
                    indicatorCenterComponent.color = color
                    indicatorCenterComponent.setShadow(radius = 12f, color = color)
                }
            } else {
                null
            },
            guideline = guideline,
        ) {
            override fun getInsets(
                context: CartesianMeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) {
                with(context) {
                    outInsets.top =
                        (
                                clippingFreeShadowRadiusMultiplier * labelBackgroundShadowRadiusDp -
                                        labelBackgroundShadowDyDp
                                )
                            .pixels
                    if (labelPosition == LabelPosition.AroundPoint) return
                    outInsets.top += label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels
                }
            }
        }
    }
}