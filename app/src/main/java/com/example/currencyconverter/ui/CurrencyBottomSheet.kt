package com.example.currencyconverter.ui

import Currency
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import currencies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyBottomSheet(onDismiss: () -> Unit,
                        onItemSelection:(Currency)->Unit,
                        selectedValue:String
                        ) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Select Currency", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(20.dp))

           currencies.map {
            OutlinedCard(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                .clickable(onClick = {onItemSelection(it)})) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = it.flag),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .clip(CircleShape)
                            .size(40.dp)
                    )
                    Text(text = it.title)
                    Spacer(modifier = Modifier.weight(3f))
                 if (selectedValue==it.code) Icon(Icons.Filled.CheckCircle,
                     contentDescription = null, tint = Color.Blue)


                }
            }

           }

        }
    }
}

















