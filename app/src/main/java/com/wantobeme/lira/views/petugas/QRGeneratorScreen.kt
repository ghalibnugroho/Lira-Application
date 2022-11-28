package com.wantobeme.lira.views.petugas

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wantobeme.lira.viewmodel.ViewModelFactoryProvider
import com.wantobeme.lira.viewmodel.petugas.QRViewModel
import com.wantobeme.lira.views.utils.Resource
import com.wantobeme.lira.views.utils.generateQRCode
import com.wantobeme.lira.views.utils.showProgressBar
import dagger.hilt.android.EntryPointAccessors

@Composable
fun provideQRViewModel(kodeQR: String?): QRViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).qrViewModelFactory()

    return viewModel(factory = QRViewModel.provideQRViewModelFactory(factory, kodeQR))
}


@Composable
fun QRGeneratorScreen(qrViewModel: QRViewModel, navController: NavController){

    val kodeQRFlow = qrViewModel.kodeQRFlow.collectAsState()

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val param = qrViewModel.getParam()

    val current_Args = navController.currentBackStackEntry?.arguments?.getString("kodeQR")
    Log.i("Katalog Detail - Arguments", "${current_Args}")

    if(current_Args!=null){
        kodeQRFlow.value?.let {
            when(it){
                is Resource.Failure -> {

                }
                Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp, 15.dp, 15.dp, 0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        bitmap.value = generateQRCode(it.result.qrcode)
                        bitmap.value?.asImageBitmap()?.let { it ->
                            Image(
                                bitmap = it,
                                contentDescription = "Generate Kode-QR Image",
                                modifier = Modifier.size(350.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(25.dp))
                        Row{
                            Text("Kode QR: ")
                            Text(it.result.qrcode)
                        }
                    }
                }
            }
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp, 15.dp, 15.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            bitmap.value = generateQRCode(param!!)
            bitmap.value?.asImageBitmap()?.let { it ->
                Image(
                    bitmap = it,
                    contentDescription = "Generate Kode-QR Image",
                    modifier = Modifier.size(350.dp)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row{
                Text("Kode QR: ")
                Text(param)
            }
        }
    }


}

