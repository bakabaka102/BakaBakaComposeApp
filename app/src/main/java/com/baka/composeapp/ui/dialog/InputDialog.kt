package com.baka.composeapp.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.baka.composeapp.R
import kotlinx.coroutines.delay

@Composable
fun DialogInput(
    title: String,
    setShowDialog: (Boolean) -> Unit,
    setValue: (String) -> Unit,
    timeOut: Long = 3000L,
) {

    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        delay(timeOut)
        setShowDialog(false)
    }
    Dialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = "",
                    tint = colorResource(android.R.color.darker_gray),
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clickable { setShowDialog(false) }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = title,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                width = 2.dp,
                                color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                            ),
                            shape = RoundedCornerShape(50)
                        ),
                    /*colors = TextFieldDefaults.colors(),*/
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        /*  focusedTextColor = md_black_1000,
                          unfocusedTextColor = md_black_1000,*/
                        focusedIndicatorColor = Color.Transparent,
                        /*unfocusedContainerColor = md_grey_200,*/
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Money,
                            contentDescription = "",
                            tint = colorResource(android.R.color.holo_green_light),
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    },
                    label = { Text("Number") },
                    placeholder = { Text(text = "Enter value") },
                    value = txtField.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        txtField.value = it.ifEmpty { "" }
                    })

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.padding(40.dp)) {
                    Button(
                        onClick = {
                            if (txtField.value.isEmpty()) {
                                txtFieldError.value = "Field can not be empty"
                                return@Button
                            }
                            setValue(txtField.value)
                            setShowDialog(false)
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Done")
                    }
                }
            }
        }
    }
}
