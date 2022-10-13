@file:OptIn(ExperimentalMaterialApi::class)

package com.wantobeme.lira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wantobeme.lira.navigation.Screen
import com.wantobeme.lira.ui.theme.LIRATheme
import com.wantobeme.lira.viewmodel.KatalogViewModel
//import com.wantobeme.lira.viewmodel.KatalogViewModel
import com.wantobeme.lira.views.DetailKatalogScreen
import com.wantobeme.lira.views.LoginScreen
import com.wantobeme.lira.views.MainScreen
import com.wantobeme.lira.views.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

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


