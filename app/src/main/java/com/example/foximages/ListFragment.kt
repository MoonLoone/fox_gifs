package com.example.foximages

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foximages.ui.GifListUi
import com.example.foximages.ui.LoadingUi
import com.example.foximages.ui.SearchForm
import com.example.foximages.ui.UploadingDataPlug


class ListFragment : Fragment() {

    private val viewModel: ListFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkInternetConnection()) {
            viewModel.load()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            return ComposeView(requireContext()).apply {
                setContent {
                    Column {
                        SearchForm {
                            viewModel.loadByName(it)
                        }
                        val isLoading by viewModel.isLoading.collectAsState()
                        val gifs by viewModel.gifsState.collectAsState()
                        var isInternetConnected by remember {
                            mutableStateOf(checkInternetConnection())
                        }
                        when {
                            !isInternetConnected -> UploadingDataPlug(Modifier.clickable {
                                if (checkInternetConnection()){
                                    isInternetConnected = !isInternetConnected
                                    viewModel.load()
                                }
                            })
                            isLoading -> LoadingUi()
                            else -> GifListUi(gifs, context)
                        }
                    }
                }
            }
        }


    private fun checkInternetConnection():Boolean{
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}