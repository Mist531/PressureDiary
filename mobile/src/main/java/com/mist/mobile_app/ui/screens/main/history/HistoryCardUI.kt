package com.mist.mobile_app.ui.screens.main.history

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.api.models.PressureRecordModel
import com.mist.common.R
import com.mist.common.ui.PDColors
import com.mist.mobile_app.ui.theme.PDTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

enum class HistoryStatus(
    val color: Color,
    val id: Int
) {
    BLUE(
        color = Color(0xFF16C8EF),
        id = 0
    ),
    GREEN(
        color = Color(0xFF23F15D),
        id = 1
    ),
    ORANGE(
        color = Color(0xFFFF8934),
        id = 2
    ),
    RED(
        color = Color(0xFFF92D2D),
        id = 3
    ),
    DEFAULT(
        color = Color(0xFFE7E7E7),
        id = 4
    );

    companion object {
        val countLine = 0..3
    }
}

//TODO: replace with settings value
fun PressureRecordModel.getInfoColor() = when {
    this.diastolic <= 60 || this.systolic <= 90 -> HistoryStatus.BLUE

    (this.diastolic in 61..80 && this.systolic in 91..120) -> HistoryStatus.GREEN

    (this.diastolic in 81..90 && this.systolic in 121..140) -> HistoryStatus.ORANGE

    (this.diastolic >= 91 || this.systolic >= 141) -> HistoryStatus.RED

    else -> HistoryStatus.DEFAULT
}


@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    record: PressureRecordModel,
) {
    val isNeedCommentBlock = remember(record.note) {
        record.note.isNotEmpty()
    }

    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = PDColors.GreyBOrder,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(
                PDColors.White
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 25.dp,
                vertical = 15.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        HistoryCardItemTop(
            modifier = Modifier,
            record = record
        )
        HistoryCardItemCenter(
            modifier = Modifier,
            record = record
        )
        if (isNeedCommentBlock) {
            HistoryCardItemBottom(
                modifier = Modifier,
                comment = record.note
            )
        }
    }
}

@Composable
fun HistoryCardItemTop(
    modifier: Modifier = Modifier,
    record: PressureRecordModel,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        HistoryCardItemTimeInfo(
            dateTimeRecord = record.dateTimeRecord
        )
        HistoryLineInfoStatus(
            modifier = Modifier
                .padding(
                    start = 32.dp
                ),
            record = record,
        )
    }
}

@Composable
fun HistoryLineInfoStatus(
    modifier: Modifier = Modifier,
    record: PressureRecordModel,
) {
    val selectColor = remember(record) {
        record.getInfoColor()
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HistoryStatus.countLine.forEach { index ->
            LinearProgressIndicator(
                modifier = Modifier
                    .height(6.dp)
                    .weight(1f),
                progress = remember(selectColor) {
                    if (selectColor.id == index) {
                        1f
                    } else
                        0f
                },
                color = selectColor.color,
                trackColor = HistoryStatus.DEFAULT.color,
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun HistoryCardItemCenter(
    modifier: Modifier = Modifier,
    record: PressureRecordModel,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HistoryCardDataItem(
            modifier = Modifier,
            iconId = R.drawable.ic_systolic,
            data = record.systolic,
            title = "SYS."
        )
        HistoryCardDataItem(
            modifier = Modifier,
            iconId = R.drawable.ic_diastolic,
            data = record.diastolic,
            title = "DIA."
        )
        HistoryCardDataItem(
            modifier = Modifier,
            iconId = R.drawable.ic_heart,
            data = record.pulse,
            title = "Пульс"
        )
    }
}

@Composable
fun HistoryCardItemTimeInfo(
    modifier: Modifier = Modifier,
    dateTimeRecord: LocalDateTime,
) {
    val localTime = remember(dateTimeRecord) {
        dateTimeRecord.toLocalTime().format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }

    Row(
        modifier = modifier
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier
                .size(35.dp)
                .padding(
                    end = 10.dp
                ),
            painter = painterResource(
                id = R.drawable.ic_clock
            ),
            contentDescription = null
        )
        Text(
            text = localTime,
            color = PDColors.Black,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HistoryCardDataItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    data: Int,
    title: String
) {
    Row(
        modifier = modifier
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    end = 10.dp
                )
                .size(28.dp),
            painter = painterResource(id = iconId),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        bottom = 2.dp
                    ),
                text = title,
                color = PDColors.Black,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = data.toString(),
                color = PDColors.Black,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HistoryCardItemBottom(
    modifier: Modifier = Modifier,
    comment: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = modifier
                .padding(
                    bottom = 8.dp
                )
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier
                    .size(35.dp)
                    .padding(
                        end = 10.dp
                    ),
                painter = painterResource(
                    id = R.drawable.ic_comment
                ),
                contentDescription = null
            )
            Text(
                text = "Комментарий",
                color = PDColors.Black,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = comment,
            color = PDColors.Black,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewHistoryCard() {
    PDTheme {
        HistoryCard(
            modifier = Modifier.padding(10.dp),
            record = PressureRecordModel(
                pressureRecordUUID = UUID.randomUUID(),
                dateTimeRecord = LocalDateTime.now(),
                systolic = 120,
                diastolic = 80,
                pulse = 68,
                note = "",
            )
        )
    }
}

@Preview
@Composable
fun PreviewHistoryLineInfoStatus() {
    PDTheme {
        Column {
            HistoryLineInfoStatus(
                record = PressureRecordModel(
                    pressureRecordUUID = UUID.randomUUID(),
                    dateTimeRecord = LocalDateTime.now(),
                    systolic = 120,
                    diastolic = 80,
                    pulse = 80,
                    note = ""
                )
            )
            HistoryLineInfoStatus(
                record = PressureRecordModel(
                    pressureRecordUUID = UUID.randomUUID(),
                    dateTimeRecord = LocalDateTime.now(),
                    systolic = 130,
                    diastolic = 90,
                    pulse = 80,
                    note = ""
                )
            )
            HistoryLineInfoStatus(
                record = PressureRecordModel(
                    pressureRecordUUID = UUID.randomUUID(),
                    dateTimeRecord = LocalDateTime.now(),
                    systolic = 50,
                    diastolic = 100,
                    pulse = 80,
                    note = ""
                )
            )
        }
    }
}