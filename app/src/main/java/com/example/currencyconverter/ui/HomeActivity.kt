package com.example.currencyconverter.ui

import Currency
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R
import com.example.currencyconverter.common.DecimalFormatter
import com.example.currencyconverter.common.DecimalInputVisualTransformation
import kotlinx.coroutines.launch
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeActivity(viewModel: MainViewModel) {

    val currencyState = viewModel.currency.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val showDropdown = remember { mutableStateOf(false) }
    val amount = remember { mutableStateOf("") }
    val baseCurrency = remember { mutableStateOf(Currency(R.drawable.usa, "USD")) }
    val targetCurrency = remember { mutableStateOf(Currency(R.drawable.nigeria, "NGN")) }
    val selectedIndex = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()



    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "Currency Converter") })
    }) { padding ->
        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(value = amount.value,
                onValueChange = { value ->
                amount.value = DecimalFormatter().cleanup(value)
            },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                singleLine = true,
                visualTransformation = DecimalInputVisualTransformation(DecimalFormatter()),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal),
                label = { Text(text = "Enter amount to convert") })
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CurrencyButton(onClick = {
                    showDropdown.value = true
                    selectedIndex.intValue = 0
                }, flag = baseCurrency.value.flag,
                    currency = baseCurrency.value.code)

                Icon(
                    painter = painterResource(id = R.drawable.compare_arrows),
                    contentDescription = null, modifier = Modifier.size(40.dp)
                )
                CurrencyButton(
                    onClick = {
                        showDropdown.value = true
                        selectedIndex.intValue = 1
                    },
                    flag = targetCurrency.value.flag,
                    currency = targetCurrency.value.code)

            }
            if (showDropdown.value) CurrencyBottomSheet(
                onDismiss = { showDropdown.value = false },
                onItemSelection = {
                    showDropdown.value = false
                    if (selectedIndex.intValue == 0) {
                        baseCurrency.value = it
                    } else {
                        targetCurrency.value = it
                    }
                },
                selectedValue = if (selectedIndex.intValue == 0)
                    baseCurrency.value.code else targetCurrency.value.code
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedButton(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                enabled = amount.value.isNotEmpty(),
                onClick = {
                    scope.launch {
                        viewModel.convertCurrency(
                            baseCode = baseCurrency.value.code,
                            targetCode = targetCurrency.value.code,
                            amount = amount.value
                        )
                    }
                }) {
                if (isLoading.value) Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(28.dp),
                    content = { CircularProgressIndicator() })
                else Text(text = "CONVERT")
            }

            Spacer(modifier = Modifier.height(40.dp))

            if (currencyState.value.conversionResult != 0.0) OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                val format: NumberFormat = NumberFormat.getCurrencyInstance()
                format.maximumFractionDigits = 0;
                format.currency = java.util.Currency.getInstance(targetCurrency.value.code);
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Converting from ${baseCurrency.value.code} to ${targetCurrency.value.code}",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        text = format.format(currencyState.value.conversionResult),
                        fontSize = 25.sp, fontWeight = FontWeight.SemiBold
                    )
                    Text(text = "Conversion Rate: ${format.format(currencyState.value.conversionRate)}")
                }
            }
        }
    }
}