package com.wantobeme.lira.views.anggota.qrscanner

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.TimeUnit


@SuppressLint("UnsafeOptInUsageError")
class QRAnalyzer(
    private val onQrcodeDetected: (qrcodes: List<Barcode>) -> Unit,
): ImageAnalysis.Analyzer {
    private var lastAnalyzedTimeStamp = 0L

    override fun analyze(image: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimeStamp >= TimeUnit.SECONDS.toMillis(2)) {
            image.image?.let { imageToAnalyze ->
                val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                    .build()
                //_ALL_FORMATS
                val qrScanner = BarcodeScanning.getClient(options)
                val imageToProcess = InputImage.fromMediaImage(imageToAnalyze, image.imageInfo.rotationDegrees)
                qrScanner.process(imageToProcess)
                    .addOnSuccessListener { qrcodes ->
                        if (qrcodes.isNotEmpty()) {
                            onQrcodeDetected(qrcodes)
                        } else {
                            Log.d("QR Analyzer", "analyze: No QR Scanned")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("QR Analyzer", "QRAnalyser: Something went wrong $exception")
                    }
                    .addOnCompleteListener {
                        image.close()
                    }
            }
            lastAnalyzedTimeStamp = currentTimestamp
        } else {
            image.close()
        }
    }

}