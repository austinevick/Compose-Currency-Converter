package com.example.currencyconverter.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyButton(onClick:()->Unit, @DrawableRes flag: Int,currency: String){
    OutlinedButton(onClick = onClick) {
        Image(painter = painterResource(id =flag),
            contentDescription = null,modifier = Modifier.size(26.dp)
                .padding(end = 5.dp))
        Text(text = currency)
        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null)
    }

}