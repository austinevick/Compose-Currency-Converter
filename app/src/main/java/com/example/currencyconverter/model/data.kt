import androidx.annotation.DrawableRes
import com.example.currencyconverter.R

val currencies = listOf(
    Currency( R.drawable.usa,"USD","United State Dollar"),
    Currency( R.drawable.british,"GBP","British Pounds"),
    Currency( R.drawable.nigeria,"NGN","Nigeria Naira"),
    Currency( R.drawable.europe,"EUR","European Euro"),
)
data class Currency(@DrawableRes val flag:Int, val code:String, val title:String="")

