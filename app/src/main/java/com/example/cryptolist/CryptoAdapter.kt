package com.example.cryptolist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.text.DecimalFormat
import java.util.*

class CryptoAdapter : PagingDataAdapter<CryptoCurrency, CryptoViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<CryptoCurrency>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldCrypto: CryptoCurrency,
                                         newCrypto: CryptoCurrency
            ) = oldCrypto.id == newCrypto.id

            override fun areContentsTheSame(oldCrypto: CryptoCurrency,
                                            newCrypto: CryptoCurrency
            ) = oldCrypto == newCrypto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cryptocurrency_item, parent, false)
        return CryptoViewHolder(itemView as ViewGroup)
    }


    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

val DECIMAL_FORMATTER = DecimalFormat("#.##")
class CryptoViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView) {

    private  var cryptoImage: ImageView = itemView.findViewById(R.id.cryptoImageIcon)
    private  var cryptoName: TextView = itemView.findViewById(R.id.cryptoName)
    private  var cryptoSymbol: TextView = itemView.findViewById(R.id.cryptoSymbol)
    private  var cryptoPrice: TextView = itemView.findViewById(R.id.cryptoPrice)
    private  var cryptoChangeIcon: ImageView = itemView.findViewById(R.id.cryptoChangeIcon)
    private  var cryptoChange: TextView = itemView.findViewById(R.id.cryptoChange)


    fun bind(cryptoCurrency : CryptoCurrency?) {

        cryptoCurrency?.let {


            cryptoImage.setImageResource(itemView.context.resIdByName(it.symbol?.toLowerCase(Locale.US),"mipmap"))
            cryptoName.text = it.name
            cryptoSymbol.text = it.symbol
            cryptoPrice.text = "$ ${DECIMAL_FORMATTER.format(it.priceUsd?.toFloat())}"
            cryptoPrice.setTextColor( if (it.changePercent24Hr?.toFloat() != null && it.changePercent24Hr.toFloat() > 0) ContextCompat.getColor(itemView.context,R.color.colorUp) else ContextCompat.getColor(itemView.context, R.color.colorDown) )
            cryptoChangeIcon.setImageResource( if (it.changePercent24Hr?.toFloat() != null && it.changePercent24Hr.toFloat() > 0) R.drawable.arrow_up else R.drawable.arrow_down )
            cryptoChange.setTextColor( if (it.changePercent24Hr?.toFloat() != null && it.changePercent24Hr.toFloat() > 0) ContextCompat.getColor(itemView.context,R.color.colorUp) else ContextCompat.getColor(itemView.context, R.color.colorDown) )
            cryptoChange.text = "${DECIMAL_FORMATTER.format(if (it.changePercent24Hr != null)  it.changePercent24Hr.toFloat() else 0 )}%"
        }

    }

}