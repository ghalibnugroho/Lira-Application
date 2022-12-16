package com.wantobeme.lira.views.petugas

import android.util.Log
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.TextFieldState
import com.wantobeme.lira.ui.theme.OptionColor
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.unSelectedColor
import com.wantobeme.lira.viewmodel.petugas.PelanggaranViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.uiState.PelanggaranState
import com.wantobeme.lira.views.uiState.RegistrasiState
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@Composable
fun TambahPelanggaranScreen(pelanggaranViewModel: PelanggaranViewModel, navController: NavController){
    var memberNo_Args = navController.currentBackStackEntry?.arguments?.getString("memberNo")
    var loanId_Args = navController.currentBackStackEntry?.arguments?.getString("loanId")
    var collectionId_Args = navController.currentBackStackEntry?.arguments?.getString("collectionId")
//    Log.i("Katalog Detail - Arguments", "${memberNo_Args}")
//    Log.i("Katalog Detail - Arguments", "${loanId_Args}")
//    Log.i("Katalog Detail - Arguments", "${collectionId_Args}")

    val formState = remember{ pelanggaranViewModel.addPelanggaranFormState }
    val jenisPelanggaran: ChoiceState = formState.getState("jenisPelanggaran")
    val jumlahDenda: TextFieldState = formState.getState("jumlahDenda")
    val optJenisPelanggaran = setOf("Telat", "Rusak", "Hilang", "Telat & Rusak")

    val pelanggaranFlow = pelanggaranViewModel.addPelanggaranFlow.collectAsState()
    var showSuccessDialog = remember{ mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        Text(text = "Tambah Denda",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Text(text = "koleksi ${collectionId_Args}",
            style = TextStyle(
                fontWeight = FontWeight(400),
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = "Sebab Pelanggaran:",
            style = TextStyle(
                fontWeight = FontWeight(400),
                fontSize = 14.sp
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            optJenisPelanggaran.forEach{
                RadioButton(
                    selected = jenisPelanggaran.value == it,
                    onClick = { jenisPelanggaran.change(it) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Primary
                    ),
                )
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight(400),
                        fontSize = 13.sp
                    )
                )
            }
        }
        if(jenisPelanggaran.hasError){
            Text(
                text = "Sebab denda harus dipilih",
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        OutlinedTextField(
            value = jumlahDenda.value,
            isError = jumlahDenda.hasError,
            onValueChange = { jumlahDenda.change(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
//                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
            placeholder = { Text(text = "Jumlah Denda (Rp)") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = OptionColor,
                unfocusedBorderColor = unSelectedColor
            ),
            modifier= Modifier
                .width(320.dp)
                .align(Alignment.CenterHorizontally)
        )
        if(jumlahDenda.hasError){
            Text(
                text = "Jumlah Denda harus diisi",
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            modifier = Modifier
                .width(320.dp)
                .height(50.dp)
                .padding(top = 10.dp),
            onClick = {
                if(formState.validate()){
//                    Log.i("formState", "${}")
                    pelanggaranViewModel.addPelanggaran(memberNo_Args,loanId_Args, collectionId_Args, formState.getData(PelanggaranState::class))
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Primary
            )
        ) {
            Text(text = "Submit Denda", color = Color.White)
        }
        if(showSuccessDialog.value){
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog.value = false
                    navController.navigate(Screen.Petugas.Sirkulasi.route){
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                    }
                },
                title = { Text(text = "Berhasil!") },
                text = { Text(text = "Pelanggaran berhasil direcord") },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccessDialog.value = false
                            navController.navigate(Screen.Petugas.Sirkulasi.route){
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Primary
                        )) {
                        Text(text = "Proceed", color = Color.White)
                    }
                }
            )
        }
        pelanggaranFlow.value?.let {
            when(it){
                is Resource.Failure -> {
                    TODO()
                }
                Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    LaunchedEffect(key1 = Unit){
                        if(it.result.status == 1){
                            showSuccessDialog.value = true
                        }
                    }
                }
            }
        }
    }


    BackHandler() {
//        navController.navigate(Screen.Petugas.Sirkulasi.Loan.Item.route + "/{$loanId_Args}")
        navController.navigate(Screen.Petugas.Sirkulasi.route){
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                    saveState = true
                }
            }
        }
    }

}