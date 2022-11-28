package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.ui.theme.Primary
import com.wantobeme.lira.ui.theme.ranchoFamily
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.anggota.qrscanner.ScannerActivity
import com.wantobeme.lira.views.navigation.AnggotaNav
import com.wantobeme.lira.views.utils.startNewActivity

@Composable
fun LandingScreen(){
    var showTopBar by rememberSaveable { mutableStateOf(true) }
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showTopBar = when (navBackStackEntry?.destination?.route) {
        Screen.Anggota.NotifAnggota.route -> false
        else -> true // in all other cases show bottom bar
    }
    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Screen.Anggota.NotifAnggota.route -> false
        else -> true // in all other cases show bottom bar
    }

    Scaffold(
        topBar = { if(showTopBar) AnggotaTopBar(navController) },
        floatingActionButton = { FloatingButtonAction(navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = { if(showBottomBar) AnggotaBottomBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AnggotaNav(navController = navController)
        }
    }
}

@Composable
fun AnggotaTopBar(navController: NavController){
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
        actions = {
            TopAppBarActionButton(
                icon = Screen.Anggota.NotifAnggota.icon,
                description = Screen.Anggota.NotifAnggota.title
            ) {
                navController.navigate(Screen.Anggota.NotifAnggota.route)
            }
        },
        backgroundColor = if (isSystemInDarkTheme()) Color.White else Color.White
    )
}

@Composable
fun TopAppBarActionButton(
    icon: Int,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .crossfade(true)
                    .data(icon)
                    .build()
            ),
            contentDescription = description)
    }
}

@Composable
fun FloatingButtonAction(navController: NavController){
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            context.startNewActivity(ScannerActivity::class.java)
        },
        shape = RoundedCornerShape(50),
        backgroundColor = Primary
    ) {
        Icon(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .crossfade(true)
                    .data(R.drawable.ic_baseline_qr_code_scanner_24)
                    .build()
            ),
            tint = Color.White,
            contentDescription = "QR Icon"
        )
    }
}

@Composable
fun AnggotaBottomBar(navController: NavController){
    val itemNav = listOf(
        Screen.Anggota.Katalog,
        Screen.Anggota.Katalog.Search,
        Screen.Anggota.HistoryLoan,
        Screen.Anggota.More
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