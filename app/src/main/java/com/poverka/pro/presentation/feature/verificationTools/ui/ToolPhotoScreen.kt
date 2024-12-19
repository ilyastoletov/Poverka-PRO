package com.poverka.pro.presentation.feature.verificationTools.ui

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.shared.PhotoBox
import com.poverka.pro.presentation.provider.PhotoFileProvider
import com.poverka.pro.presentation.feature.verificationTools.model.Stage
import com.poverka.pro.presentation.feature.verificationTools.viewmodel.VerificationToolsSharedViewModel

@Composable
fun ToolPhotoScreen(
    stage: Stage,
    sharedViewModel: VerificationToolsSharedViewModel,
    openNextStage: () -> Unit,
) {

    val photoUri by sharedViewModel.getPhotoStateForStage(stage).collectAsState()

    val photoBitmap = remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current
    var recaptureCounter by remember { mutableIntStateOf(0) }

    LaunchedEffect(recaptureCounter, photoUri) {
        photoUri?.let { uri ->
            val contentResolver = context.contentResolver
            val photoParcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
            if (photoParcelFileDescriptor != null) {
                val fileDescriptor = photoParcelFileDescriptor.fileDescriptor
                photoBitmap.value = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            }
            photoParcelFileDescriptor?.close()
        }
    }

    val capturedPhotoUri = remember { PhotoFileProvider.getUriForImage(context) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { captured ->
        if (captured) {
            sharedViewModel.updatePhotoForStage(stage, capturedPhotoUri)
            recaptureCounter++
        }
    }

    Content(
        resources = context.resources,
        currentStage = stage,
        photoBitmap = photoBitmap.value,
        onClickCapture = {
            if (photoBitmap.value != null) {
                if (stage.next != null) {
                    openNextStage()
                } else {
                    sharedViewModel.submitVerificationToolsForm()
                }
            } else {
                cameraLauncher.launch(capturedPhotoUri)
            }
        },
        onClickRecapture = { cameraLauncher.launch(capturedPhotoUri) }
    )

}

@Composable
private fun Content(
    resources: Resources,
    currentStage: Stage,
    photoBitmap: Bitmap?,
    onClickCapture: () -> Unit,
    onClickRecapture: () -> Unit,
) {
    val titleText = remember(photoBitmap) {
        if (photoBitmap == null) {
            currentStage.title
        } else {
            resources.getString(R.string.tool_photo_recapture_instruction)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Text(
            text = stringResource(
                id = R.string.tool_photo_stage_title,
                currentStage.number,
                currentStage.tool
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Text(
            text = titleText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.85f)
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        PhotoBox(
            photoBitmap = photoBitmap,
            onClickCapture = onClickCapture,
            onClickRecapture = onClickRecapture
        )
    }
}