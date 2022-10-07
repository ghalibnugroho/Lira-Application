package com.wantobeme.lira.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wantobeme.lira.model.KatalogCover
import com.wantobeme.lira.navigation.Screen
import com.wantobeme.lira.ui.theme.LIRATheme
import com.wantobeme.lira.viewmodel.KatalogViewModel
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.ranchoFamily

@Composable
fun MainScreen(katalogViewModel: KatalogViewModel = KatalogViewModel()){

    Scaffold(
        topBar = { TopBar()},
        bottomBar = {}
    ) {
//        Greeting(name = "ghalib")
        KatalogScreen()
    }
}

@Composable
fun TopBar(){
    TopAppBar(
        title = {
            Text("Literasi Raden",
                style = TextStyle(
                    fontFamily = ranchoFamily,
                    fontWeight = FontWeight(400),
                    fontSize = 32.sp
                )
            )
                },
        backgroundColor = if (isSystemInDarkTheme()) Color.White else Color.White
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LIRATheme {
        val katalog  =
            KatalogCover(
            "5778",
            "0010-0422000001",
            "Prosedur Penelitian : Suatu Pendekatan Praktik / Suharsimi Arikunto",
            "Arikunto Suharsimi",
            "2013",
            "https://firebasestorage.googleapis.com/v0/b/lira-6bad3.appspot.com/o/5778_result.webp?alt=media&token=253ba2ec-3b00-4f00-9c61-0a50ea8fc6a3",
            4
            )

        CardItem(katalog)
    }
}

@Preview(showBackground = true)
@Composable
fun KScreen(){
    LIRATheme {
        KatalogScreen()
    }
}
