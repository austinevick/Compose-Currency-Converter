package com.example.currencyconverter.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.currencyconverter.model.ConvertCurrencyModel
import com.example.currencyconverter.model.CurrencyResponseModel
import com.example.currencyconverter.repository.CurrencyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyApi: CurrencyApi,
    application: Application
) : AndroidViewModel(application) {

    private val _responseData = MutableStateFlow<UIState>(UIState.Loading)
    val responseData: StateFlow<UIState> = _responseData.asStateFlow()

    private val _currency = MutableStateFlow(ConvertCurrencyModel())
    val currency: StateFlow<ConvertCurrencyModel> = _currency.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading:StateFlow<Boolean> = _isLoading.asStateFlow()

    suspend fun getCurrencyRate() {
        try {
            val response = currencyApi.getCurrencies()
            _responseData.value = UIState.Success(data = response.body()!!)
            Log.d("API data", response.body().toString())
            Log.d("API data", response.code().toString())
        } catch (e: Exception) {
            _responseData.value = UIState.Error(e.message.toString())
            Log.d("API data",e.message.toString())

        }
    }

    suspend fun convertCurrency(baseCode:String,targetCode:String,amount:String){
        try {
            _isLoading.value = true
            val response = currencyApi.convertCurrency(baseCode, targetCode, amount)
            _currency.value = response.body() as ConvertCurrencyModel
            Log.d("API data",response.body().toString())
            _isLoading.value = false
        }catch (e:Exception){
            _isLoading.value = false
            Log.d("API data",e.message.toString())
        }
    }

}



sealed class UIState {
    data object Loading : UIState()
    data class Error(val message: String) : UIState()
    data class Success<T>(val data: T) : UIState()
}

