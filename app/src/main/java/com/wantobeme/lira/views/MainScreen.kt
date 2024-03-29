package com.wantobeme.lira.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wantobeme.lira.model.Katalog
import com.wantobeme.lira.ui.theme.LIRATheme
import com.wantobeme.lira.ui.theme.ranchoFamily
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.viewmodel.guest.KatalogViewModel
import com.wantobeme.lira.views.navigation.MainNavHost

@Composable
fun MainScreen(){

    var showTopBar by rememberSaveable { mutableStateOf(true) }
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showTopBar = when (navBackStackEntry?.destination?.route) {
        Screen.Auth.Login.route -> false
        Screen.Auth.Registrasi.route -> false // on this screen bottom bar should be hidden
        else -> true // in all other cases show bottom bar
    }
    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Screen.Auth.Login.route -> false
        Screen.Auth.Registrasi.route -> false // on this screen bottom bar should be hidden
        else -> true // in all other cases show bottom bar
    }
    Scaffold(
        topBar = { if(showTopBar) MainTopBar() },
        bottomBar = { if(showBottomBar) MainBottomBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            MainNavHost(navController = navController)
        }
    }
}

@Composable
fun MainTopBar(){
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
fun MainBottomBar(navController: NavController){
    val itemNav = listOf(
        Screen.Katalog,
        Screen.Katalog.Search,
        Screen.Auth.Login
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomNavigation(backgroundColor = MaterialTheme.colors.background) {

        itemNav.forEach{ item ->
            BottomNavigationItem(
                selected = item.route == currentRoute,
                label = {
                        Text(text = item.title)
                },
                icon = {
                       Icon(
                           painter = painterResource(item.icon),
                           contentDescription = item.title
                       )
                },
                selectedContentColor = Primary,
                unselectedContentColor = Color.Black,
                onClick = {
                    navController.navigate(item.route){
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }

                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LIRATheme {
        val katalog  =
            Katalog(
            "5778",
            "0010-0422000001",
            "Prosedur Penelitian : Suatu Pendekatan Praktik / Suharsimi Arikunto",
            "Arikunto Suharsimi",
            "2013",
            "https://firebasestorage.googleapis.com/v0/b/lira-6bad3.appspot.com/o/5778_result.webp?alt=media&token=253ba2ec-3b00-4f00-9c61-0a50ea8fc6a3",
            4
            )

        KatalogCardItem(katalog = katalog, onClick = {})
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}


//@Preview(showBackground = true)
//@Composable
//fun KScreen(){
//    LIRATheme {
//        KatalogScreen()
//    }
//}
