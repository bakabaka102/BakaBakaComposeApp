package com.baka.composeapp.ui.bottomnavigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baka.composeapp.ui.dialog.DialogInput
import com.baka.composeapp.ui.dialog.DialogProgress
import com.baka.composeapp.ui.dialog.ShowConfirmDialog
import com.baka.composeapp.ui.drawermenu.HomeViewModel

@Composable
fun Screen3() {
    val viewModel = viewModel<HomeViewModel>()
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val count: State<Int?> = viewModel.count.observeAsState()

        Text(
            text = "Home Screen ${count.value}", fontSize = 40.sp, color = Color.Black
        )
        Button(onClick = {
            viewModel.incrementCount()
        }) {
            Text(text = "Count")
        }

        val isShowDialogInput = remember { mutableStateOf(false) }
        val isShowDialogProgress = remember { mutableStateOf(false) }

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
                Log.i("HomePage", "HomePage : $it")
            })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
            ShowConfirmDialog()
        }
    }
}