package com.poverka.pro.presentation.feature.shared.fileProvider

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.poverka.pro.R
import java.io.File

class PhotoFileProvider : FileProvider(R.xml.filepaths) {
    companion object {
        fun getUriForImage(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val imageFile = File.createTempFile(
                "captured_image_",
                ".jpg",
                directory
            )
            val authority = "${context.packageName}.fileprovider"
            return getUriForFile(context, authority, imageFile)
        }
    }
}