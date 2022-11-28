package com.wantobeme.lira.views.anggota

import android.Manifest
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.common.util.concurrent.ListenableFuture
import com.wantobeme.lira.views.Screen
import com.wantobeme.lira.views.anggota.qrscanner.QRAnalyzer
import com.wantobeme.lira.views.utils.startNewActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var cameraPerm by rememberSaveable{ mutableStateOf(false) }
    var fineLocation by rememberSaveable{ mutableStateOf(false) }
    var coarsePerm by rememberSaveable{ mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        val multiPermissionState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        multiPermissionState.permissions.forEach{ perm ->
            when(perm.permission){
                Manifest.permission.CAMERA -> {
                    when{
                        perm.hasPermission -> {
                            cameraPerm = true
                        }
                        perm.shouldShowRationale -> {
                            Text("Need Camera Permission")
                        }
                        perm.isPermanentlyDenied() -> {
                            Text("Camera Permission has Permanently Denied, Enable it in the App Setting")
                        }
                    }
                }
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    when{
                        perm.hasPermission -> {
                            fineLocation = true
                        }
                        perm.shouldShowRationale -> {
                            Text("Need Camera Permission")
                        }
                        perm.isPermanentlyDenied() -> {
                            Text("Location Permission has Permanently Denied, Enable it in the App Setting")
                        }
                    }
                }
                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    when{
                        perm.hasPermission -> {
                            coarsePerm = true
                        }
                        perm.shouldShowRationale -> {
                            Text("Need Camera Permission")
                        }
                        perm.isPermanentlyDenied() -> {
                            Text("Location Permission has Permanently Denied, Enable it in the App Setting")
                        }
                    }
                }
            }
        }
    DisposableEffect(key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if(event == Lifecycle.Event.ON_RESUME){
                    multiPermissionState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
        CameraPreview(navController)
    }
    BackHandler() {
        context.startNewActivity(AnggotaActivity::class.java)
    }
}

@Composable
fun CameraPreview(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
    val qrCodeVal = remember { mutableStateOf("") }

    AndroidView(
        factory = { AndroidViewContext ->
            PreviewView(AndroidViewContext).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                implementationMode = PreviewView.ImplementationMode.PERFORMANCE
            }
        },
        modifier = Modifier
            .fillMaxSize(),
        update = { previewView ->
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = QRAnalyzer { qrcodes ->
                    qrcodes.forEach { qrcode ->
                        qrcode.displayValue?.let { qrcodeValue ->
                            qrCodeVal.value = qrcodeValue
                            navController.navigate(Screen.Anggota.QRScanner.Koleksi.route + "/${qrCodeVal.value}")
//                            Toast.makeText(context, qrcodeValue, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied(): Boolean{
    return !shouldShowRationale && !hasPermission
}