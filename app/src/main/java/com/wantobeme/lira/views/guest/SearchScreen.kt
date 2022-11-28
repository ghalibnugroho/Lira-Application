package com.wantobeme.lira.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wantobeme.lira.viewmodel.guest.KatalogViewModel
import com.wantobeme.lira.views.utils.showProgressBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(viewModel: KatalogViewModel, navController:NavController){

    val query : MutableState<String> =  rememberSaveable { mutableStateOf("") }
    val search = viewModel.searchList

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(value = query.value, onValueChange = {
            query.value = it
            viewModel.searchKatalogList(query.value)
        }, enabled = true,
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            label = { Text(text = "Cari Judul, Penulis, atau Tahun terbit") },
            modifier= Modifier
                .width(350.dp)
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 0.dp, 0.dp, 10.dp)
        )

        if (search.isLoading) {
            Log.i("Search Loading State", "MainContent: in the loading")
            showProgressBar()
        }

        if (search.error.isNotBlank()) {
            Log.i("Search Error State", "MainContent: ${search.error}")
            Box(modifier = Modifier
                .fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = viewModel.searchList.error
                )
            }
        }

        if (search.data.isNotEmpty()) {
            Log.i("Search Data State", "MainContent: ${search.data}")
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 10.dp,
                    end = 5.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
                content = {
                    items(search.data.size){ item ->
                        KatalogCardItem(
                            katalog = search.data[item],
                            onClick = {
                                navController.navigate(Screen.Katalog.DetailKatalog.route + "/${search.data[item].id}")
                            }
                        )
                    }
                }
            )
        }
    }
}