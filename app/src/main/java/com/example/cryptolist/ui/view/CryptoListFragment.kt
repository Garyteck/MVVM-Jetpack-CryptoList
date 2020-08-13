package com.example.cryptolist.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptolist.R
import com.example.cryptolist.api.NetworkInterface
import com.example.cryptolist.data.local.CryptoDatabase
import com.example.cryptolist.data.repository.Repository
import com.example.cryptolist.ui.adapter.CryptoAdapter
import com.example.cryptolist.ui.adapter.ProgressAdapter
import com.example.cryptolist.ui.viewmodel.CryptoViewModel
import kotlinx.android.synthetic.main.fragment_crypto_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class CryptoListFragment : Fragment() {

    private val cryptoAdapter: CryptoAdapter = CryptoAdapter()
    @ExperimentalPagingApi
    private val cryptoViewModel  by viewModels<CryptoViewModel>(
        factoryProducer = { object:  AbstractSavedStateViewModelFactory(this, null) {
                    override fun <T : ViewModel?> create(
                        key: String,
                        modelClass: Class<T>,
                        handle: SavedStateHandle
                    ): T {
                        val db = CryptoDatabase.getInstance(activity?.applicationContext as Context) as CryptoDatabase
                        val api = Retrofit.Builder()
                            .baseUrl(NetworkInterface.ENDPOINT)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(
                                OkHttpClient().newBuilder().connectTimeout(60L, TimeUnit.SECONDS)
                                    .readTimeout(60L, TimeUnit.SECONDS)
                                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                                    .build()).build()
                            .create(NetworkInterface::class.java)
                        val repository = Repository(db,api)

                        @Suppress("UNCHECKED_CAST")
                        return CryptoViewModel(repository) as T
                    }
                }
        }
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crypto_list, container, false)
    }


    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(recyclerView) {
            adapter = cryptoAdapter.withLoadStateFooter(ProgressAdapter())
            layoutManager = LinearLayoutManager(context)
        }


        lifecycleScope.launch {
            cryptoAdapter.loadStateFlow.collectLatest { loadStates ->
                swipeLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }


        lifecycleScope.launch {
            cryptoViewModel.cryptos.collectLatest {
                cryptoAdapter.submitData(it)
            }
        }

    }

}