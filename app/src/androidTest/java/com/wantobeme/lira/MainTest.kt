package com.wantobeme.lira

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.wantobeme.lira.di.Constant
import com.wantobeme.lira.di.RetrofitApi
import com.wantobeme.lira.network.ApiServices
import com.wantobeme.lira.repository.AuthRepository
import com.wantobeme.lira.viewmodel.guest.AuthViewModel
import com.wantobeme.lira.viewmodel.guest.KatalogViewModel
import com.wantobeme.lira.views.KatalogScreen
import com.wantobeme.lira.views.LoginScreen
import com.wantobeme.lira.views.MainScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidTest
class MainTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    val apiServices = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(RetrofitApi.getOkHttpClient())
        .build()
        .create(ApiServices::class.java)

    @get:Rule
    var composeRule = createComposeRule()

//    @get:Rule
//    var mainComposeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun test(){
//    composeRule.setContent {
//        MainActivity()
//    }
//        composeRule.onNodeWithText("Literasi Raden").assertExists()
//        composeRule.onNodeWithText("email").performTextInput("ghalib@student.ub.ac.id")
    }
}