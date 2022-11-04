package com.wantobeme.lira.views.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wantobeme.lira.model.KatalogDetail
import com.wantobeme.lira.viewmodel.KatalogViewModel
import com.wantobeme.lira.views.*
import com.wantobeme.lira.views.guest.RegistrasiScreen
import com.wantobeme.lira.views.utils.getViewModelInstance

@Composable
fun MainNavHost(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Katalog.route
    ){
        composable(Screen.Katalog.route){
            KatalogScreen(navController = navController)
        }
        composable(Screen.Katalog.Search.route){
            SearchScreen()
        }
        composable(Screen.Katalog.DetailKatalog.route + "/{Id}",
            arguments = listOf(navArgument(name = "Id") {
                type = NavType.StringType
            })){ entry ->
            DetailKatalogScreen(id = entry.arguments?.getString("Id"), navController = navController)
        }
        composable(Screen.Auth.Login.route){
            LoginScreen()
        }
        composable(Screen.Auth.Registrasi.route){
            RegistrasiScreen()
        }
    }
}