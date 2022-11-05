@file:OptIn(ExperimentalMaterialApi::class)

package com.wantobeme.lira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wantobeme.lira.ui.theme.LIRATheme
import com.wantobeme.lira.viewmodel.KatalogDetailViewModel
//import com.wantobeme.lira.viewmodel.KatalogViewModel
import com.wantobeme.lira.views.MainScreen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            LIRATheme {
                MainScreen()
            }
        }
    }
}


