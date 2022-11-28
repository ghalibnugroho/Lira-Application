package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wantobeme.lira.viewmodel.guest.KatalogViewModel
import com.wantobeme.lira.views.KatalogCardItem
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: KatalogViewModel, navController: NavController){

    LaunchedEffect(key1 = AnggotaActivity::class){
        viewModel.getKatalogList()
    }

    val katalog = viewModel.katalogResponse.collectAsState()
    katalog.value?.let {
        when(it){
            is Resource.Failure -> {
                Text(text = "${it.exception.message}")
            }
            Resource.Loading -> {
                showProgressBar()
            }
            is Resource.Success -> {
                if (it.result.isEmpty()){
                    Text(text = "Daftar Katalog Buku tidak tersedia.")
                }else{
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            end = 5.dp,
                            top = 12.dp,
                            bottom = 12.dp
                        ),
                        content = {
                            items(it.result.size){ item ->
                                KatalogCardItem(
                                    katalog = it.result[item],
                                    onClick = {
                                        navController.navigate(Screen.Katalog.DetailKatalog.route + "/${it.result[item].id}")
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}