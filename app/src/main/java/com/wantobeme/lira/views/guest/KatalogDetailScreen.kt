package com.wantobeme.lira.views

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R
import com.wantobeme.lira.model.KatalogDetail
import com.wantobeme.lira.ui.theme.vLightGray
import com.wantobeme.lira.viewmodel.KatalogDetailViewModel
import com.wantobeme.lira.viewmodel.ViewModelFactoryProvider
import com.wantobeme.lira.views.uimodel.Resource
import dagger.hilt.android.EntryPointAccessors

@Composable
fun provideKatalogDetailViewModel(bookId: String?): KatalogDetailViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).katalogDetailViewModelFactory()

    return viewModel(factory = KatalogDetailViewModel.provideKatalogDetailViewModelFactory(factory, bookId))
}

@Composable
fun KatalogDetailScreen(viewModel: KatalogDetailViewModel, navController: NavController){

    val katalogDetail = viewModel.katalogDetailResponse.collectAsState()

    Log.d("KatalogDetail Screen", "$katalogDetail")
    katalogDetail.value?.let {
        when(it){
            is Resource.Failure -> {
                Text(text = "${it.exception.message}")
            }
            Resource.Loading -> {
                showProgressBar()
            }
            is Resource.Success -> {
                DetailKatalogItem(katalogDetail = it.result)
            }
        }
    }
}

@Composable
fun DetailKatalogItem(
    katalogDetail: KatalogDetail,
    modifier: Modifier = Modifier
){
    if(katalogDetail == null){
        Text(text = "Null Detail")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .background(vLightGray)
            .align(Alignment.CenterHorizontally)
        ) {
            if(katalogDetail.coverURL != null){
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .crossfade(true)
                            .data(katalogDetail.coverURL)
                            .build(),
                        filterQuality = FilterQuality.High,
                        placeholder = painterResource(id = R.drawable.image_placeholder),
                    ),
                    contentDescription = null,
                    modifier = modifier
                        .width(200.dp)
                        .height(280.dp)
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .align(Alignment.Center),
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
                        .width(200.dp)
                        .height(280.dp)
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Text(text = "In-Stock (${katalogDetail.quantity})",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = modifier.padding(25.dp,30.dp, 20.dp, 10.dp))
        Text(text = katalogDetail.title,
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = modifier.padding(25.dp,0.dp,0.dp,0.dp))
        Text(text = katalogDetail.author,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            color = Color.Gray,
            modifier = modifier.padding(25.dp,1.dp,0.dp,25.dp))
        Box(
            modifier = modifier
                .background(color = vLightGray)
                .width(330.dp)
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
                .clip(shape = RoundedCornerShape(7.dp))
        ){
            Row(modifier = modifier.align(Alignment.Center)){
                Box{
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = "No. Rak",
                            modifier = modifier.widthIn(0.dp,105.dp)
                        )
                        Text(
                            text = katalogDetail.callNumber,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = modifier
                                .widthIn(0.dp,105.dp)
                        )
                    }
                }
                Box(modifier = modifier
                    .padding(10.dp, 0.dp)
                ){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Tahun Terbit",
                            modifier = modifier.widthIn(0.dp,105.dp)
                        )
                        Text(
                            text = katalogDetail.publishYear,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = modifier.widthIn(0.dp,50.dp)
                        )
                    }
                }
                Box{
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = "Penerbit",
                            modifier = modifier.widthIn(0.dp,105.dp)
                        )
                        Text(
                            text = katalogDetail.publisher,
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = modifier.widthIn(0.dp, 80.dp)
                        )
                    }
                }
            }
        }
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(30.dp, 20.dp)
        ){
            Column{
                Row{
                    Text(
                        text = "Kota: ",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        modifier = modifier.padding(10.dp, 0.dp,0.dp,0.dp),
                        text = katalogDetail.publishLocation
                    )
                }
                Row{
                    Text(
                        text = "ISBN:",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold
                    )
                        )
                    Text(
                        modifier = modifier.padding(10.dp, 0.dp,0.dp,0.dp),
                        text = katalogDetail.isbn
                    )
                }
                Row{
                    Text(
                        text = "Subjek:",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        modifier = modifier.padding(10.dp, 0.dp,0.dp,0.dp),
                        text = katalogDetail.subject
                    )
                }
                Row{
                    Text(
                        text = "Fisik Buku:",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        modifier = modifier.padding(10.dp, 0.dp,0.dp,0.dp),
                        text = katalogDetail.physicalDescription
                    )
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PDetailKatalog(){
    val katalogDetail =
        KatalogDetail(
            id = "5778",
            bibid = "awdaw",
            title = "awdad",
            author = "awdadw",
            publisher = "awdad",
            publishLocation = "awdadw",
            publishYear = "awdad",
            subject = "awdadw",
            physicalDescription = "awdadwa",
            isbn = "awdadw",
            callNumber = "awdad",
            coverURL = "https://firebasestorage.googleapis.com/v0/b/lira-6bad3.appspot.com/o/5778_result.webp?alt=media&token=253ba2ec-3b00-4f00-9c61-0a50ea8fc6a3",
            quantity = 2
        )


    DetailKatalogItem(katalogDetail = katalogDetail)
}