package com.baka.composeapp.home

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun BottomAppBarHome() {
    androidx.compose.material3.BottomAppBar(
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.Call,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.Share,
                    contentDescription = "Localized description",
                )
            }
        },
        floatingActionButton = {
            val context = LocalContext.current.applicationContext
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "Floating button", Toast.LENGTH_SHORT).show()
                },
                /*containerColor = BottomAppBarDefaults.bottomAppBarFabColor,*/
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )
}
