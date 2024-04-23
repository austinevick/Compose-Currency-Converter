package com.example.currencyconverter.repository

import com.example.currencyconverter.API_KEY
import com.example.currencyconverter.model.ConvertCurrencyModel
import com.example.currencyconverter.model.CurrencyResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("/v6/$API_KEY/latest/USD")
    suspend fun getCurrencies(): Response<CurrencyResponseModel>

    @GET("/v6/$API_KEY/pair/{baseCode}/{targetCode}/{amount}")
    suspend fun convertCurrency(
        @Path("baseCode") baseCode: String,
        @Path("targetCode") targetCode: String,
        @Path("amount") amount: String
    ): Response<ConvertCurrencyModel>

}