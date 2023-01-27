package com.wantobeme.lira.views.anggota

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wantobeme.lira.R


@Composable
fun NotifikasiAnggotaScreen(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Belum ada notifikasi masuk")
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .crossfade(true)
                    .data(R.drawable.undraw_push_notification)
                    .build(),
                filterQuality = FilterQuality.High,
                placeholder = painterResource(id = R.drawable.image_placeholder),
            ),
            contentDescription = null,
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
                .alpha(0.2F)
                .clip(shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
    }

}
