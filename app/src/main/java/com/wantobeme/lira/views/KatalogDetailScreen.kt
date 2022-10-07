package com.wantobeme.lira.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wantobeme.lira.viewmodel.KatalogViewModel

@Composable
fun DetailKatalogScreen(
    katalogViewModel: KatalogViewModel = KatalogViewModel(),
                        id: String ,modifer: Modifier = Modifier){
    Text(text = "Detail Katalog ID = ${id}")
}