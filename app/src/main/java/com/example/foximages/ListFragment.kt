package com.example.foximages

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.foximages.pojo.DataFromAPI
import kotlinx.coroutines.flow.Flow


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
                val isLoading by viewModel.isLoading.collectAsState()
                val gifs by viewModel.gifsState.collectAsState()
                when{
                    isLoading -> LoadingUi()
                    else -> GifListUi(gifs)
                }

            }
        }
    }

    @Composable
    fun LoadingUi(){
        CircularProgressIndicator()
    }

    @Composable
    private fun GifContent(
       gifts: Flow<PagingData<DataFromAPI>>,
    ){

    }

    @Composable
    fun GifListUi(gifsList: Flow<PagingData<DataFromAPI>>) {
        val items:LazyPagingItems<DataFromAPI> = gifsList.collectAsLazyPagingItems()
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            this.items<DataFromAPI>(items){
                GifGridItem(data = it?:DataFromAPI())
            }
        })
    }

    @Composable
    private fun GifGridItem(
        data:DataFromAPI
    ){
        val imageLoader = ImageLoader.Builder(requireContext())
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        AsyncImage(
            model = data.media?.get(0)?.gif?.url,
            imageLoader = imageLoader,
            contentDescription = null
        )
    }

    private fun <T: Any> LazyGridScope.items(
        lazyPagingItems: LazyPagingItems<DataFromAPI>,
        itemContent: @Composable LazyItemScope.(value: T?) -> Unit
    ) {
        items(lazyPagingItems.itemCount) { item ->
            GifGridItem(data = lazyPagingItems[item] ?: DataFromAPI())
        }
    }

    companion object {
        fun newInstance() =
            ListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}