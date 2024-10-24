package com.baka.composeapp.features.logicaction

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baka.composeapp.helper.Logger
import com.baka.composeapp.ui.dialog.ConfirmDialog
import com.baka.composeapp.ui.dialog.DialogInput
import com.baka.composeapp.ui.dialog.DialogProgress
import com.baka.composeapp.ui.drawermenu.HomeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SomeActionDemo(viewModel: HomeViewModel) {
    val count: State<Int?> = viewModel.count.observeAsState()
    val isShowDialogInput = remember { mutableStateOf(false) }
    val isShowDialogProgress = remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }

    if (isShowDialogProgress.value) {
        DialogProgress(
            title = "Loading",
            description = "This is loading dialog",
            timeOut = 5000,
            setShowDialog = { isShowDialogProgress.value = it }
        )
    }

    if (isShowDialogInput.value)
        DialogInput(title = "Dialog input", timeOut = 30_000, setShowDialog = {
            isShowDialogInput.value = it
        }, setValue = {
            Logger.i("HomePage, value inputted: $it")
        })

    if (showCustomDialog) {
        ConfirmDialog(
            onDismiss = {
                showCustomDialog = !showCustomDialog
            },
            onConfirm = {
                showCustomDialog = !showCustomDialog
            }
        )
    }

    Color(0xFF93EBF7)
    FlowRow(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp)) {
        Button(onClick = {
            viewModel.incrementCount()
        }) {
            Text(text = "Clicked: ${count.value}")
        }
        Button(onClick = {
            isShowDialogInput.value = true
        }) {
            Text(text = "Input Dialog")
        }
        Button(onClick = {
            isShowDialogProgress.value = true
        }) {
            Text(text = "Progress Dialog")
        }
        Button(
            onClick = {
                showCustomDialog = !showCustomDialog
            },
        ) {
            Text(text = "Confirm Dialog")
        }
    }
}