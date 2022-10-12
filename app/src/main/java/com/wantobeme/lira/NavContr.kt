package com.wantobeme.lira

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wantobeme.lira.navigation.Screen
import com.wantobeme.lira.views.DetailKatalogScreen
import com.wantobeme.lira.views.LoginScreen
import com.wantobeme.lira.views.MainScreen
import com.wantobeme.lira.views.SearchScreen


@Composable
fun myNavigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Katalog.route) {
        composable(Screen.Katalog.route) {
            MainScreen()
        }
        composable(
            Screen.DetailKatalog.route,
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ) {
                navBackStackEntry ->
            navBackStackEntry.arguments?.getString("id")
                ?.let { DetailKatalogScreen(id = it.toInt()) }
        }
        composable(Screen.Search.route) {
            SearchScreen()
        }
        composable(Screen.Login.route) {
            LoginScreen()
        }
//                    composable(Screen.Movies.route) {
//                        MoviesScreen()
//                    }
//                    composable(Screen.Books.route) {
//                        BooksScreen()
//                    }
//                    composable(Screen.Profile.route) {
//                        ProfileScreen()
    }
}