package com.wantobeme.lira.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(modifier: Modifier = Modifier){
    Column(modifier.fillMaxWidth()) {
        
    }
    Text(text = "Login Screen")
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    LoginScreen()
}
