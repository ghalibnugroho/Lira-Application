package com.wantobeme.lira.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.model.Katalog
import com.wantobeme.lira.viewmodel.guest.KatalogViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wantobeme.lira.MainActivity
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.showProgressBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KatalogScreen(viewModel: KatalogViewModel, navController: NavController){
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val currentNav = navController.currentDestination?.route

    LaunchedEffect(key1 = MainActivity::class){
        viewModel.jumpActivity()
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

// stateless
@Composable
fun KatalogCardItem(
    katalog: Katalog,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 7.dp,
                top = 5.dp,
                end = 5.dp,
                bottom = 15.dp
            )
            .clickable {
                onClick.invoke()
            },
        backgroundColor = if (isSystemInDarkTheme()) Color.White else Color.White,
        elevation = 10.dp,
    ){
        Column(
            modifier = modifier.fillMaxSize()
        ){
            if(katalog.coverURL != null){
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .crossfade(true)
                            .data(katalog.coverURL)
                            .build(),
                        filterQuality = FilterQuality.High,
                        placeholder = painterResource(id = R.drawable.image_placeholder),
                    ),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(270.dp)
                        .clip(shape = RoundedCornerShape(3.dp)),
                    contentScale = ContentScale.Crop
                )
            }else{
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .crossfade(true)
                            .data(R.drawable.no_cover)
                            .build(),
                        filterQuality = FilterQuality.High,
                        placeholder = painterResource(id = R.drawable.image_placeholder),
                    ),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(270.dp)
                        .clip(shape = RoundedCornerShape(3.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Text(maxLines = 1,text = katalog.title, fontWeight = FontWeight.ExtraBold, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(3.dp,0.dp,0.dp,0.dp))
            Text(maxLines = 1, text = katalog.author, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(3.dp,0.dp,0.dp,0.dp))
            Text(text = katalog.publishYear, modifier = Modifier.padding(3.dp,0.dp,0.dp,0.dp))
            Row(
                modifier.padding(
                    start = 3.dp,
                    top = 10.dp,
                    end = 0.dp,
                    bottom = 5.dp
                )
            ){
                Text(text = "Qty: ")
                Text(text = katalog.quantity.toString(), fontWeight = FontWeight.ExtraBold, color = Color.Red)
            }

        }
    }
}
