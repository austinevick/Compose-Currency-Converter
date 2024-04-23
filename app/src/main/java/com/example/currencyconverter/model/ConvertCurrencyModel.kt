package com.example.currencyconverter.model


import com.google.gson.annotations.SerializedName

data class ConvertCurrencyModel(
    @SerializedName("base_code")
    val baseCode: String = "",
    @SerializedName("conversion_rate")
    val conversionRate: Double = 0.0,
    @SerializedName("conversion_result")
    val conversionResult: Double = 0.0,
    @SerializedName("result")
    val result: String = "",
    @SerializedName("target_code")
    val targetCode: String = ""
)