package com.mist.mobile_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.api.models.Gender
import com.example.api.models.GetPaginatedPressureRecordsModel
import com.example.api.models.LoginModel
import com.example.api.models.PostUserRequestModel
import com.mist.common.data.repository.UserRepository
import com.mist.common.data.stores.impl.TokensDataStore
import com.mist.mobile_app.ui.theme.PDTheme
import org.koin.android.ext.android.inject
import org.koin.compose.getKoin
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class MainActivityMobile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tokensDataStore by inject<TokensDataStore>()

        setContent {
            PDTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //TODO подписаться на errorFlow выводить ошибки
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    userRepository: UserRepository = getKoin().get()
) {
    LaunchedEffect(key1 = Unit) {
        userRepository.login(
            model = LoginModel(
                password = "test",
                email = "test"
            )
        ).fold(
            ifLeft = {
                Log.e("TesLOG", it.toString())
            },
            ifRight = {
                Log.e("TesLOG", it.toString())
            }
        )


        userRepository.postUser(
            model = PostUserRequestModel(
                email = "work",
                password = "work",
                firstName = "work",
                lastName = "work",
                dateOfBirth = LocalDate.now().minusYears(20),
                gender = Gender.M
            )
        ).fold(
            ifLeft = {
                Log.e("TESTNET", it.toString())
            },
            ifRight = {
                Log.e("TESTNET", "WORK!!")
            }
        )
    }

    val dsf = GetPaginatedPressureRecordsModel(
        userUUID = UUID.randomUUID(),
        fromDateTime = LocalDateTime.now(),
        toDateTime = LocalDateTime.now(),
        page = 1,
        pageSize = 1
    )
    Text(
        text = "Hello ${dsf.userUUID}!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PDTheme {
        Greeting("Android")
    }
}