package com.baka.composeapp.ui.bottomsheetlayout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetLayoutMain() {
    ModalBottomSheet(onDismissRequest = { /*TODO*/ }) {
        val sheetState = rememberModalBottomSheetState()
        val coroutineScope = rememberCoroutineScope()

        val showBottomSheetLayout: () -> Unit = {
            coroutineScope.launch {
                sheetState.show()
            }
        }
        val hideBottomSheetLayout: () -> Unit = {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
        var showBottomSheet by remember { mutableStateOf(false) }
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show bottom sheet") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        showBottomSheet = true
                    }
                )
            }
        ) { contentPadding ->
            // Screen content

            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.padding(contentPadding),
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    // Sheet content
                    Button(onClick = {
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Hide bottom sheet")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewView() {
    BottomSheetLayoutMain()
}