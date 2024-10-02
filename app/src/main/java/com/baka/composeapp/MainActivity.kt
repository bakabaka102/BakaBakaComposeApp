package com.baka.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.rememberNavController
import com.baka.composeapp.home.BottomAppBarHome
import com.baka.composeapp.home.ProductListPage
import com.baka.composeapp.home.TopAppBarMain
import com.baka.composeapp.ui.theme.BakaBakaComposeAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BakaBakaComposeAppTheme {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                    rememberTopAppBarState()
                )
                val navigation = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        TopAppBarMain(scrollBehavior)
                    },
                    bottomBar = {
                        BottomAppBarHome()
                    }) { innerPadding ->
                    ProductListPage(innerPadding)
                }
            }
        }
    }
}
