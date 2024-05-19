package com.mist.mobile_app

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.api.models.PressureRecordModel
import com.mist.common.modules.listModules
import com.mist.mobile_app.modules.viewModelModule
import com.mist.mobile_app.ui.components.PDButton
import com.mist.mobile_app.ui.screens.main.records.new_rec.NewRecordBottomSheet
import com.mist.mobile_app.ui.screens.main.records.new_rec.NewRecordViewModel
import com.mist.mobile_app.ui.screens.main.records.refactor.RefactorRecordBottomSheet
import com.mist.mobile_app.ui.screens.main.records.refactor.RefactorRecordViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import java.time.LocalDateTime
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class TestUI : KoinTest {

    @Before
    fun beforeAndroidTest() {
        loadKoinModules(listModules + viewModelModule)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomSheetVisibility() {
        composeTestRule.setContent {
            NewRecordBottomSheet(
                isVisible = true,
            )
        }
        composeTestRule.onNodeWithTag("NewRecordBottomSheet").assertIsDisplayed()
    }

    @Test
    fun testInputFieldsInNewRecord() {
        val viewModel = get().get<NewRecordViewModel>()

        composeTestRule.setContent {
            NewRecordBottomSheet(
                isVisible = true,
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag("SystolicInput").performTextInput("120")
        composeTestRule.onNodeWithTag("DiastolicInput").performTextInput("80")
        composeTestRule.onNodeWithTag("PulseInput").performTextInput("70")
        composeTestRule.onNodeWithTag("NoteInput").performTextInput("Feeling good")

        composeTestRule.runOnUiThread {
            assert(viewModel.state.systolic == 120)
            assert(viewModel.state.diastolic == 80)
            assert(viewModel.state.pulse == 70)
            assert(viewModel.state.note == "Feeling good")
        }
    }

    @Test
    fun testInputFieldsInRefactorRecord() {
        val viewModel = get().get<RefactorRecordViewModel>(
            parameters = {
                parametersOf(
                    PressureRecordModel(
                        pressureRecordUUID = UUID.randomUUID(),
                        dateTimeRecord = LocalDateTime.now(),
                        systolic = 10,
                        diastolic = 10,
                        pulse = 10,
                        note = ""
                    )
                )
            }
        )

        composeTestRule.setContent {
            RefactorRecordBottomSheet(
                isVisible = true,
                pressureRecord = null,
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag("SystolicInput").performTextClearance()
        composeTestRule.onNodeWithTag("DiastolicInput").performTextClearance()
        composeTestRule.onNodeWithTag("PulseInput").performTextClearance()
        composeTestRule.onNodeWithTag("NoteInput").performTextClearance()

        composeTestRule.onNodeWithTag("SystolicInput").performTextInput("120")
        composeTestRule.onNodeWithTag("DiastolicInput").performTextInput("80")
        composeTestRule.onNodeWithTag("PulseInput").performTextInput("70")
        composeTestRule.onNodeWithTag("NoteInput").performTextInput("Feeling good")

        composeTestRule.runOnUiThread {
            assert(viewModel.state.pressureRecord?.systolic == 120)
            assert(viewModel.state.pressureRecord?.diastolic == 80)
            assert(viewModel.state.pressureRecord?.pulse == 70)
            assert(viewModel.state.pressureRecord?.note == "Feeling good")
        }
    }

    @Test
    fun testProgressBarVisibility() {
        val showProgressBar = mutableStateOf(true)
        composeTestRule.setContent {
            NewRecordBottomSheet(
                isVisible = true,
                viewModel = get().get<NewRecordViewModel>().apply {
                    state = state.copy(showProgressBar = showProgressBar.value)
                },
            )
        }

        composeTestRule.onNodeWithTag("ProgressBar").assertIsDisplayed()
        showProgressBar.value = false
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("ProgressBar").assertDoesNotExist()
    }

    @Test
    fun testPdButtonEnabled() {
        val buttonText = "Click me"
        composeTestRule.setContent {
            PDButton(
                text = buttonText,
                onClick = { },
                enabled = true
            )
        }

        composeTestRule.onNodeWithText(buttonText).assertIsEnabled()
    }

    @Test
    fun testPdButtonDisabled() {
        val buttonText = "Click me"
        composeTestRule.setContent {
            PDButton(
                text = buttonText,
                onClick = { },
                enabled = false
            )
        }

        composeTestRule.onNodeWithText(buttonText).assertIsNotEnabled()
    }
}