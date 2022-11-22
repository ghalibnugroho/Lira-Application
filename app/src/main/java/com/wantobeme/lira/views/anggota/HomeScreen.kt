package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.ranchoFamily
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.navigation.PetugasNav
import com.wantobeme.lira.views.petugas.PetugasBottomBar
import com.wantobeme.lira.views.petugas.PetugasTopBar

@Composable
fun HomeScreen(){
    val navController = rememberNavController()

    Scaffold(
        topBar = { AnggotaTopBar() },
        bottomBar = { AnggotaBottomBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            PetugasNav(navController = navController)
        }
    }
}

@Composable
fun AnggotaTopBar(){
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
fun AnggotaBottomBar(navController: NavController){
    val itemNav = listOf(
        Screen.Anggota.Presensi,
        Screen.Petugas.Search,
        Screen.Petugas.DaftarPresensi,
        Screen.Petugas.Manage
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
                    navController.navigate(item.route) {
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
//                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
//                        restoreState = true
                    }

                }
            )
        }

    }
}