package com.wantobeme.lira.views.anggota

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wantobeme.lira.ui.theme.LIRATheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnggotaActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            LIRATheme {
                LandingScreen()
            }
        }
    }
}