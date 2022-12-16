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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wantobeme.lira.MainActivity
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.ranchoFamily
import com.wantobeme.lira.ui.theme.vLightGray
import com.wantobeme.lira.ui.theme.vPrimary
import com.wantobeme.lira.viewmodel.guest.AuthViewModel
import com.wantobeme.lira.viewmodel.petugas.ManageViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.uiState.LoginState
import com.wantobeme.lira.views.utils.startNewActivity

@Composable
fun ManageScreen(viewModel: ManageViewModel, authViewModel: AuthViewModel, navController: NavController){
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Literasi Raden",
                style = TextStyle(
                    fontFamily = ranchoFamily,
                    fontWeight = FontWeight(400),
                    fontSize = 64.sp
                )
            )
            Text(text = "v1.0.0.1",
                style = TextStyle(
                    fontWeight = FontWeight.Thin,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = Modifier.size(40.dp))
            Button(
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .padding(top = 10.dp),
                onClick = {
                    navController.navigate(Screen.Petugas.Pelanggaran.route)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = vPrimary
                )
            ) {
                Text(text = "Pelanggaran Anggota", color = Color.White)
            }
            Spacer(modifier = Modifier.size(15.dp))
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
}
