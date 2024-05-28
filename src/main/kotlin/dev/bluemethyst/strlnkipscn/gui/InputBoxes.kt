package dev.bluemethyst.strlnkipscn.gui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun InputBoxes(subnetMin: TextFieldValue, subnetMax: TextFieldValue, ipRangeMax: TextFieldValue, ipRangeMin: TextFieldValue) {
    val subnetMinState = remember { mutableStateOf(subnetMin) }
    val subnetMaxState = remember { mutableStateOf(subnetMax) }
    val ipRangeMaxState = remember { mutableStateOf(ipRangeMax) }
    val ipRangeMinState = remember { mutableStateOf(ipRangeMin) }

    Column {
        TextField(value = subnetMinState.value, onValueChange = {
            if (it.text.all { char -> char.isDigit() }) {
                subnetMinState.value = it
            }
        }, placeholder = { Text("Subnet Min") }, singleLine = true
        )
    }
    Column {
        TextField(value = subnetMaxState.value, onValueChange = {
            if (it.text.all { char -> char.isDigit() }) {
                subnetMaxState.value = it
            }
        }, placeholder = { Text("Subnet Max") }, singleLine = true
        )
    }
    Column {
        TextField(value = ipRangeMaxState.value, onValueChange = {
            if (it.text.all { char -> char.isDigit() }) {
                ipRangeMaxState.value = it
            }
        }, placeholder = { Text("IP Range Max") }, singleLine = true
        )
    }
    Column {
        TextField(value = ipRangeMinState.value, onValueChange = {
            if (it.text.all { char -> char.isDigit() }) {
                ipRangeMinState.value = it
            }
        }, placeholder = { Text("IP Range Min") }, singleLine = true
        )
    }
}