package com.example.foximages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foximages.ui.GifListUi
import com.example.foximages.ui.LoadingUi
import com.example.foximages.ui.SearchForm
import com.example.foximages.ui.UploadingDataPlug
import com.example.foximages.utils.InternetConnectionManager


class ListFragment : Fragment() {

    private val viewModel: ListFragmentViewModel by viewModels{ ListFragmentViewModel.factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (InternetConnectionManager.checkInternetConnection(requireContext())) {
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
                        val isLoading by viewModel.isLoading.collectAsState()
                        val gifs by viewModel.gifsState.collectAsState()
                        var isInternetConnected by remember {
                            mutableStateOf(InternetConnectionManager.checkInternetConnection(requireContext()))
                        }
                        SearchForm {
                            isInternetConnected = InternetConnectionManager.checkInternetConnection(requireContext())
                            if (isInternetConnected) {
                                viewModel.load(it)
                            }
                            else{
                                Toast.makeText(context, resources.getText(R.string.internet_warning), Toast.LENGTH_SHORT).show()
                            }
                        }
                        when {
                            !isInternetConnected -> UploadingDataPlug(Modifier.clickable {
                                if (InternetConnectionManager.checkInternetConnection(requireContext())){
                                    isInternetConnected = !isInternetConnected
                                    viewModel.load()
                                }
                            })
                            isLoading -> LoadingUi()
                            else -> GifListUi(gifs.collectAsLazyPagingItems())
                        }
                    }
                }
            }
        }
}