package com.wantobeme.lira.views.guest

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.TextFieldState
import com.wantobeme.lira.ui.theme.OptionColor
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.ranchoFamily
import com.wantobeme.lira.ui.theme.unSelectedColor
import com.wantobeme.lira.viewmodel.guest.AuthViewModel
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.anggota.AnggotaActivity
import com.wantobeme.lira.views.petugas.PetugasActivity
import com.wantobeme.lira.views.uiState.LoginState
import com.wantobeme.lira.views.uiState.RegistrasiState
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import com.wantobeme.lira.views.utils.startNewActivity

@Composable
fun RegistrasiScreen(authViewModel: AuthViewModel, navController: NavController){

    val registrasiState = remember{ authViewModel.registrasiState }
    val emailState: TextFieldState = registrasiState.getState("email")
    val passwordState: TextFieldState = registrasiState.getState("password")
    val namaLengkapState: TextFieldState = registrasiState.getState("nama_lengkap")
    val jenisIdentitasState: ChoiceState = registrasiState.getState("jenis_identitas")
    val optJenisIdentitas = setOf("KTM", "KTP")
    val noIdentitasState: TextFieldState = registrasiState.getState("no_identitas")
    val jenisKelaminState: ChoiceState = registrasiState.getState("jenis_kelamin")
    val optJenisKelamin = setOf("Pria", "Wanita")
    val alamatState: TextFieldState = registrasiState.getState("alamat")
    val noHPState: TextFieldState = registrasiState.getState("no_hp")
    val institusiState: TextFieldState = registrasiState.getState("institusi")

    var showAlertDialog = remember{ mutableStateOf(false) }
    var showSuccessDialog = remember{ mutableStateOf(false) }

    val guestRegistrasi = authViewModel.guestRegistrasi.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Literasi Raden",
                style = TextStyle(
                    fontFamily = ranchoFamily,
                    fontWeight = FontWeight(400),
                    fontSize = 64.sp
                ),
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(text = "Pendaftaran baru",
                style = TextStyle(
                    fontWeight = FontWeight(250),
                    fontSize = 25.sp
                ),
            )
            Spacer(modifier = Modifier.size(40.dp))
            OutlinedTextField(
                value = emailState.value,
                isError = emailState.hasError,
                onValueChange = { emailState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                ),
//                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                placeholder = { Text(text = "Email") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if(emailState.hasError){
                Text(
                    text = "Email harus diisi",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            OutlinedTextField(
                value = passwordState.value,
                isError = passwordState.hasError,
                onValueChange = { passwordState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                ),
//                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "passwordIcon") },
                placeholder = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if(passwordState.hasError){
                Text(
                    text = "Password harus diisi",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            OutlinedTextField(
                value = namaLengkapState.value,
                isError = namaLengkapState.hasError,
                onValueChange = { namaLengkapState.change(it) },
//                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                placeholder = { Text(text = "Nama Lengkap") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if(namaLengkapState.hasError){
                Text(
                    text = "Nama Lengkap harus diisi",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Identitas:")
                optJenisIdentitas.forEach{
                    RadioButton(
                        selected = jenisIdentitasState.value == it,
                        onClick = { jenisIdentitasState.change(it) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary
                        ),
                    )
                    Text(
                        text = it,
                        textAlign = TextAlign.Center
                    )
                }
            }
            if(jenisIdentitasState.hasError){
                Text(
                    text = "Identitas harus dipilih",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            OutlinedTextField(
                value = noIdentitasState.value,
                isError = noIdentitasState.hasError,
                onValueChange = { noIdentitasState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
//                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                placeholder = { Text(text = "Nomor Identitas") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if(noIdentitasState.hasError){
                Text(
                    text = "Nomor Identitas harus diisi",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Jenis Kelamin: ")
                optJenisKelamin.forEach{
                    RadioButton(
                        selected = jenisIdentitasState.value == it,
                        onClick = { jenisIdentitasState.change(it) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Primary
                        ),
                    )
                    Text(
                        text = it,
                        textAlign = TextAlign.Center
                    )
                }
            }
            if(jenisKelaminState.hasError){
                Text(
                    text = "Jenis Kelamin harus dipilih",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                value = alamatState.value,
                isError = alamatState.hasError,
                onValueChange = { alamatState.change(it) },
//                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                placeholder = { Text(text = "Alamat Lengkap") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if(alamatState.hasError){
                Text(
                    text = "Alamat harus diisi",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            OutlinedTextField(
                value = noHPState.value,
                isError = noHPState.hasError,
                onValueChange = { noHPState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                ),
//                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                placeholder = { Text(text = "Nomor Handphone") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if(noHPState.hasError){
                Text(
                    text = "Nomor Handphone harus diisi",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            OutlinedTextField(
                value = institusiState.value,
                isError = institusiState.hasError,
                onValueChange = { institusiState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                ),
//                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                placeholder = { Text(text = "Institusi") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if(institusiState.hasError){
                Text(
                    text = "Institusi harus diisi",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Button(
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .padding(top = 10.dp),
                onClick = {
                    if(registrasiState.validate()){
                        authViewModel.registrasi(registrasiState.getData(RegistrasiState::class))
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Primary
                )
            ) {
                Text(text = "Submit Data", color = Color.White)
            }
            if(showAlertDialog.value){
                AlertDialog(
                    onDismissRequest = {showAlertDialog.value = false},
                    title = { Text(text = "Warning Dialog") },
                    text = { Text(text = "Email / Nomor Identitas yang anda masukan telah terdaftar !!") },
                    confirmButton = {
                        Button(onClick = { showAlertDialog.value = false },
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
                        showAlertDialog.value = false
                        navController.navigate(Screen.Auth.Login.route)
                    },
                    title = { Text(text = "Success Dialog") },
                    text = { Text(text = "Registrasi Berhasil") },
                    confirmButton = {
                        Button(onClick = {
                            showAlertDialog.value = false
                            navController.navigate(Screen.Auth.Login.route)
                        },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Primary
                            )) {
                            Text(text = "Proceed", color = Color.White)
                        }
                    }
                )
            }
            guestRegistrasi.value?.let {
                when(it){
                    is Resource.Failure -> {
                        Text(text = "${it.exception.message}")
                    }
                    Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Success -> {
                        LaunchedEffect(key1 = Unit){
                            if (it.result.status == 1){
                                showSuccessDialog.value = true
                            }else{
                                showAlertDialog.value = true
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegPreview(){
    val navController = rememberNavController()
    RegistrasiScreen(authViewModel = hiltViewModel(), navController = navController )
}