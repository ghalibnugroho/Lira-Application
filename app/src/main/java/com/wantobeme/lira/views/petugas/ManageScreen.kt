package com.wantobeme.lira.views.petugas

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wantobeme.lira.MainActivity
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.viewmodel.guest.AuthViewModel
import com.wantobeme.lira.viewmodel.petugas.ManageViewModel
import com.wantobeme.lira.views.uiState.LoginState
import com.wantobeme.lira.views.utils.startNewActivity

@Composable
fun ManageScreen(viewModel: ManageViewModel, authViewModel: AuthViewModel){
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Button(
            modifier = Modifier
                .width(320.dp)
                .height(50.dp)
                .padding(top = 10.dp),
            onClick = {
//                viewModel.logout()
                authViewModel.logout()
                context.startNewActivity(MainActivity::class.java)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red
            )
        ) {
            Text(text = "Logout", color = Color.White)
        }
    }
}
