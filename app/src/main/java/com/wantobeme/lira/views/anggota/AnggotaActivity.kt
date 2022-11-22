package com.wantobeme.lira.views.anggota

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.wantobeme.lira.ui.theme.LIRATheme
import com.wantobeme.lira.views.MainScreen
import com.wantobeme.lira.views.navigation.AnggotaNav
import com.wantobeme.lira.views.navigation.PetugasNav
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnggotaActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            LIRATheme {
                HomeScreen()
            }
        }
    }
}