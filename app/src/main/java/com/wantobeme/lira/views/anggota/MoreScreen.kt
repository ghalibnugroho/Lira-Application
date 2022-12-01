package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.wantobeme.lira.ui.theme.ranchoFamily
import com.wantobeme.lira.ui.theme.vLightGray
import com.wantobeme.lira.viewmodel.guest.AuthViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import com.wantobeme.lira.views.utils.startNewActivity
import kotlinx.coroutines.delay

@Composable
fun MoreScreen(authViewModel: AuthViewModel, navController: NavController){
    val context = LocalContext.current
    var success by rememberSaveable{ mutableStateOf(true) }

    LaunchedEffect(key1 = AnggotaActivity::class){
        val member = authViewModel.getMemberNo()
        authViewModel.getDataAnggota(member.identitas)
    }

    val dataAnggota = authViewModel.dataAnggota.collectAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        dataAnggota.value?.let {
            when(it){
                is Resource.Failure -> {
                    Text(text = "Literasi Raden",
                        style = TextStyle(
                            fontFamily = ranchoFamily,
                            fontWeight = FontWeight(400),
                            fontSize = 50.sp
                        ),
                        modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp)
                    )
                    success=false
                }
                Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    LaunchedEffect(key1 = Unit){
                        success=true
                    }
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Profile",
                            style = TextStyle(
                                fontFamily = ranchoFamily,
                                fontWeight = FontWeight(400),
                                fontSize = 54.sp
                            )
                        )
                        Spacer(modifier = Modifier.size(30.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = "Nama",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Light
                                )
                            )
                            Text(
                                text = it.result.namaLengkap,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = "Nomor Identitas",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Light
                                )
                            )
                            Text(
                                text = it.result.nomorIdentitas,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Jenis Kelamin",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Light
                                )
                            )
                            if (it.result.jenisKelamin == 1){
                                Text(
                                    text = "Pria",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }else{
                                Text(
                                    text = "Wanita",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Nomor Hp",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Light
                                )
                            )
                            if (it.result.no_hp != ""){
                                Text(
                                    text = it.result.no_hp,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }else{
                                Text(
                                    text = "-",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = "Alamat",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Light
                                )
                            )
                            Text(
                                text = it.result.alamatLengkap,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = "Institusi",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Light
                                )
                            )
                            if(it.result.institution != ""){
                                Text(
                                    text = it.result.institution,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }else{
                                Text(
                                    text = "-",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(
                            modifier = Modifier
                                .width(320.dp)
                                .height(50.dp)
                                .padding(top = 10.dp),
                            onClick = {
                                navController.navigate(Screen.Anggota.Presensi.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = vLightGray
                            )
                        ) {
                            Text(text = "Log Presensi", color = Color.Black)
                        }
                        Button(
                            modifier = Modifier
                                .width(320.dp)
                                .height(50.dp)
                                .padding(top = 10.dp),
                            onClick = {
                                authViewModel.logout()
                                context.startNewActivity(MainActivity::class.java)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Red
                            )
                        ) {
                            Text(text = "Logout", color = Color.White)
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
        if(!success){
            Button(
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .padding(top = 10.dp),
                onClick = {
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

