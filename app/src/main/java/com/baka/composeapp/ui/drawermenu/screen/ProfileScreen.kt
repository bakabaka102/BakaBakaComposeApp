package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val languages = listOf(
            "C++", "C", "C#", "Java", "Kotlin", "Dart", "Python", "Javascript", "SpringBoot",
            "XML", "Dart", "Node JS", "Typescript", "Dot Net", "GoLang", "MongoDb",
        )

        Column(
            // inside this column we are specifying modifier to specify max width and max height
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Simple ListView Example",
                // in modifier we are specifying padding for our text from all sides.
                modifier = Modifier.padding(10.dp),
                // on below line we are specifying style for our text
                style = TextStyle(
                    color = Color.Black,
                    fontSize = TextUnit(value = 20.0F, type = TextUnitType.Sp)
                ), fontWeight = FontWeight.Black
            )
            // on below line we are calling lazy column for displaying listview.
            LazyColumn {
                // on below line we are populating items for listview.
                items(languages) { language ->
                    Text(language, modifier = Modifier.padding(15.dp))
                    HorizontalDivider()
                }
            }
        }
    }
}