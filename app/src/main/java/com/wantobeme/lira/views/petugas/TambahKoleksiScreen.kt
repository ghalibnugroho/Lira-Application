package com.wantobeme.lira.views.petugas

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dsc.form_builder.TextFieldState
import com.wantobeme.lira.ui.theme.OptionColor
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.unSelectedColor
import com.wantobeme.lira.viewmodel.petugas.KoleksiViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.uiState.KoleksiState
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun TambahKoleksiScreen(viewModel: KoleksiViewModel, navController: NavController){

    val formState = remember{viewModel.addKoleksiFormState}
    val nomorQRcode: TextFieldState = formState.getState("nomorQRCode")
    val nomorKoleksi: TextFieldState = formState.getState("nomorKoleksi")

    val parameter = viewModel.getVMParameter()
//    println(parameter)

    var showSuccessDialog = remember{ mutableStateOf(false) }
    var showAlertDialog = remember{ mutableStateOf(false) }
    val koleksi = viewModel.koleksiKatalog.collectAsState()
    val addKoleksi = viewModel.addKoleksiFlow.collectAsState()

    koleksi.value?.let {
        when(it){
            is Resource.Failure -> {
                Text(text = "${it.exception.message}")
            }
            Resource.Loading -> {
                showProgressBar()
            }
            is Resource.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.size(30.dp))
                    Text(text = "Tambah Koleksi (Unit) Buku",
                        style = TextStyle(
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        value = nomorQRcode.value,
                        isError = nomorQRcode.hasError,
                        onValueChange = { nomorQRcode.change(it) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            autoCorrect = false
                        ),
                        placeholder = { Text(text = "Kode QR (Unique)") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = OptionColor,
                            unfocusedBorderColor = unSelectedColor
                        ),
                        modifier= Modifier
                            .width(320.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp, 20.dp, 0.dp, 10.dp)
                    )
                    if(nomorQRcode.hasError){
                        Text(
                            text = "Kode-QR harus diisi",
                            color = Color.Red,
                            style = MaterialTheme.typography.caption
                        )
                    }
                    OutlinedTextField(
                        value = nomorKoleksi.value,
                        isError = nomorKoleksi.hasError,
                        onValueChange = { nomorKoleksi.change(it) },
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false
                        ),
                        placeholder = { Text(text = "Nomor Koleksi buku - (DDC)") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = OptionColor,
                            unfocusedBorderColor = unSelectedColor
                        ),
                        modifier= Modifier
                            .width(320.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp, 20.dp, 0.dp, 10.dp)
                    )
                    if(nomorKoleksi.hasError){
                        Text(
                            text = "Nomor Koleksi harus diisi",
                            color = Color.Red,
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Spacer(modifier = Modifier.size(15.dp))
                    Button(
                        modifier = Modifier
                            .width(320.dp)
                            .height(50.dp),
                        onClick = {
                            if(formState.validate()){
                                viewModel.addKoleksiKatalog(parameter, formState.getData(KoleksiState::class))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Primary
                        )
                    ) {
                        Text(text = "Tambah Koleksi", color = Color.White)
                    }
                    TextButton(onClick = {
                        navController.navigate(Screen.Petugas.Search.DetailKatalog.Koleksi.route + "/${it.result[0].katalogId}")
                    }) {
                        Text("back")
                    }
                    if(showAlertDialog.value){
                        AlertDialog(
                            onDismissRequest = {
                                showAlertDialog.value = false
                                navController.navigate(Screen.Petugas.Search.DetailKatalog.Koleksi.Tambah.route + "/${it.result[0].katalogId}")  // coba di un komen setelah bisa backhandler
                            },
                            title = { Text(text = "Perhatian!?") },
                            text = { Text(text = "Kode-QR Telah terdaftar") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showAlertDialog.value = false
                                        navController.navigate(Screen.Petugas.Search.DetailKatalog.Koleksi.Tambah.route + "/${it.result[0].katalogId}")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Primary
                                    )) {
                                    Text(text = "Ulangi kembali", color = Color.White)
                                }
                            }
                        )
                    }
                    if(showSuccessDialog.value){
                        AlertDialog(
                            onDismissRequest = {
                                showSuccessDialog.value = false
                            },
                            title = { Text(text = "Berhasil!") },
                            text = { Text(text = "Koleksi telah ditambahkan") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showSuccessDialog.value = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Primary
                                    )) {
                                    Text(text = "Proceed", color = Color.White)
                                }
                            }
                        )
                    }

                    addKoleksi.value?.let {
                        when(it){
                            Resource.Loading -> {
                                showProgressBar()
                            }
                            is Resource.Success -> {
                                LaunchedEffect(key1 = Unit){
                                    if(it.result.status == 1){
                                        showSuccessDialog.value = true
                                    }else{
                                        showAlertDialog.value = true
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                }
                BackHandler {
                    navController.navigate(Screen.Petugas.Search.DetailKatalog.Koleksi.route + "/${it.result[0].katalogId}")
                }
            }
        }
    }
}