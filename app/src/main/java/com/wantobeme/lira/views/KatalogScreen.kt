package com.wantobeme.lira.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.wantobeme.lira.model.KatalogCover
import com.wantobeme.lira.viewmodel.KatalogViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun KatalogScreen(viewModel: KatalogViewModel = hiltViewModel()){

    val result = viewModel.list
    viewModel.getKatalogList()


//    if (result.isLoading) {
//        Log.d("KatalogScreen", "MainContent: in the loading")
//        Box(modifier = Modifier
//            .fillMaxSize()) {
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//        }
//    }
//
//
//    if (result.error.isNotBlank()) {
//        Log.e("KatalogScreen", "MainContent: ${result.error}")
//        Box(modifier = Modifier
//            .fillMaxSize()) {
//            Text(
//                modifier = Modifier.align(Alignment.Center),
//                text = viewModel.list.error
//            )
//        }
//    }


    if (result.data.isNotEmpty()) {
        KatalogList(katalogList = result.data)
        Log.d("KatalogScreen", "MainContent: ${result.data}")

    }

//    LaunchedEffect(key1 = Unit, block = {
//        viewModel.getKatalogCoverList()
//    })
//    if(!viewModel.katalogResponse.isEmpty()){
//        KatalogList(katalogList = viewModel.katalogResponse)
//    }

//    if(viewModel.loadKatalog == true){
//        showProgressBar()
//    }


}


@OptIn(ExperimentalFoundationApi::class)
//@ExperimentalFoundationApi
@Composable
fun KatalogList(katalogList: List<KatalogCover>){
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 10.dp,
            end = 5.dp,
            top = 12.dp,
            bottom = 12.dp
        ),
        content = {
            items(katalogList.size){ item ->
                CardItem(katalog = katalogList[item])
            }
        }
    )
}

// stateless
@Composable
fun CardItem(
    katalog: KatalogCover,
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
                Toast
                    .makeText(context, "Test", Toast.LENGTH_SHORT)
                    .show()
//                navController.navigate("DetailKatalog/${katalog.id}")
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

@Composable
fun showProgressBar(){
    Box(
        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
    ){
        CircularProgressIndicator()
    }
}