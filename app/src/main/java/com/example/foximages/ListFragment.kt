package com.example.foximages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foximages.ui.GifListUi
import com.example.foximages.ui.LoadingUi
import com.example.foximages.ui.SearchForm


class ListFragment : Fragment() {

    private val viewModel: ListFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load()
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
                    when {
                        isLoading -> LoadingUi()
                        else -> GifListUi(gifs, context)
                    }
                }
            }
        }
    }

}