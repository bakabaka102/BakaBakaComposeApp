package com.baka.composeapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        TopAppBarMain(scrollBehavior)
                    },
                    bottomBar = {
                        BottomAppBar(
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
                                FloatingActionButton(
                                    /*modifier = Modifier.padding(12.dp),*/
                                    onClick = {
                                        Toast.makeText(this, "Floating button", Toast.LENGTH_SHORT)
                                            .show()
                                    },
                                    /*containerColor = BottomAppBarDefaults.bottomAppBarFabColor,*/
                                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                                ) {
                                    Icon(Icons.Filled.Add, "Localized description")
                                }
                            }
                        )
                    }) { innerPadding ->
                    /*Greeting(
                        name = "Baka",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    /*val gradientColors = listOf(Color.Cyan, Color.LightGray, Color.Magenta *//*...*//*)*/

                    val listState = rememberLazyListState()
                    val productList = listOf(
                        Product(1, "Smartphone", 599.99),
                        Product(2, "Laptop", 1299.99),
                        Product(3, "Headphones", 99.99),
                        Product(4, "Headphones", 99.99),
                        Product(5, "Headphones", 99.99),
                        Product(6, "Headphones", 99.99),
                        Product(7, "Headphones", 99.99),
                        Product(8, "Headphones", 99.99),
                        Product(9, "Headphones", 99.99),
                        Product(10, "Headphones", 99.99),
                        Product(11, "Headphones", 99.99),
                        Product(12, "Headphones", 99.99),
                        Product(13, "Headphones", 99.99),
                        Product(14, "Headphones", 99.99),
                        Product(15, "Headphones", 99.99),
                        Product(16, "Headphones", 99.99),
                        Product(17, "Headphones", 99.99),
                        Product(18, "Headphones", 99.99),
                    )
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding),
                        state = listState,
                        contentPadding = PaddingValues(horizontal = 6.dp),
                    ) {
                        items(productList) { product ->
                            ItemRow(product) {
                                Log.d("Product", "Item click === $it")
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ItemRow(product: Product, onItemClick: (Product) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp), /* https://stackoverflow.com/questions/62939473/how-to-add-margin-in-jetpack-compose */
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Gray,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(product) }
                    .padding(24.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Icon of product",
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 24.sp,
                        /*brush = Brush.linearGradient(colors = gradientColors),*/
                    ),
                    text = product.name,
                    color = Color.Black,
                )

                Text(
                    textAlign = TextAlign.End,
                    style = TextStyle(
                        fontSize = 16.sp,
                    ),
                    text = product.price.toString(),
                )
            }

        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopAppBarMain(scrollBehavior: TopAppBarScrollBehavior) {
        TopAppBar(
            title = {
                Text(text = "Top App Bar")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigation Icon",
                    )
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu Icons",
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )
    }
}


data class Product(val id: Int, val name: String, val price: Double)
