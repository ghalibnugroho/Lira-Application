package com.wantobeme.lira.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dsc.form_builder.TextFieldState
import com.wantobeme.lira.ui.theme.OptionColor
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.ranchoFamily
import com.wantobeme.lira.ui.theme.unSelectedColor
import com.wantobeme.lira.viewmodel.guest.AuthViewModel
import com.wantobeme.lira.views.anggota.AnggotaActivity
import com.wantobeme.lira.views.petugas.PetugasActivity
import com.wantobeme.lira.views.uiState.LoginState
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import com.wantobeme.lira.views.utils.startNewActivity

@Composable
fun LoginScreen(authViewModel: AuthViewModel, navController: NavController){
    val formState = remember{authViewModel.loginFormState}
    val emailState: TextFieldState = formState.getState("email")
    val passwordState: TextFieldState = formState.getState("password")
    var showAlertDialog = remember{ mutableStateOf(false) }

    val userToken = authViewModel.guestToken.collectAsState()
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(text = "Literasi Raden",
                style = TextStyle(
                    fontFamily = ranchoFamily,
                    fontWeight = FontWeight(400),
                    fontSize = 64.sp
                ),
                modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp)
            )
            OutlinedTextField(
                value = emailState.value,
                isError = emailState.hasError,
                onValueChange = { emailState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                ),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                placeholder = { Text(text = "Email") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
            )
            if(emailState.hasError){
                Text(
                    text = "Masukkan format Email",
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            OutlinedTextField(
                value = passwordState.value,
                isError = passwordState.hasError,
                onValueChange = { passwordState.change(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                ),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "passwordIcon") },
                placeholder = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptionColor,
                    unfocusedBorderColor = unSelectedColor
                ),
                modifier= Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 20.dp, 0.dp, 10.dp)
            )
            if(passwordState.hasError){
                Text(
                    text = "Password harus diisi",
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
                    if(formState.validate()){
                        authViewModel.login(formState.getData(LoginState::class))
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Primary
                )
            ) {
                Text(text = "Masuk", color = Color.White)
            }
            TextButton(onClick = {
                navController.navigate(Screen.Auth.Registrasi.route)
            }) {
                Text("daftar baru")
            }
            if(showAlertDialog.value){
                AlertDialog(
                    onDismissRequest = {showAlertDialog.value = false},
                    title = { Text(text = "Warning Dialog") },
                    text = { Text(text = "Email / Password anda salah") },
                    confirmButton = {
                        Button(onClick = { showAlertDialog.value = false },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Primary
                            )) {
                            Text(text = "Ok.", color = Color.White)
                        }
                    }
                )
            }
        }
        userToken.value?.let {
            when(it){
                is Resource.Failure -> {
                    Text(text = "${it.exception.message}")
                }
                Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    LaunchedEffect(key1 = Unit){
                        if (it.result.role == 1){
                            context.startNewActivity(PetugasActivity::class.java)
                        }else if(it.result.role == 2){
                            context.startNewActivity(AnggotaActivity::class.java)
                        }else{
                            showAlertDialog.value = true
                        }
                    }
                }
            }
        }
    }
}
