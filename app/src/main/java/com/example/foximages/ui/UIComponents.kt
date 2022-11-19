package com.example.foximages.ui

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.foximages.pojo.DataFromAPI
import kotlinx.coroutines.flow.Flow

@Composable
fun LoadingUi() {
    CircularProgressIndicator()
}

@Composable
fun GifListUi(gifsList: Flow<PagingData<DataFromAPI>>, context: Context) {
    val items: LazyPagingItems<DataFromAPI> = gifsList.collectAsLazyPagingItems()
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        this.items<DataFromAPI>(items, context) {
            GifGridItem(data = it ?: DataFromAPI(), context)
        }
    })
}

@Composable
private fun GifGridItem(
    data: DataFromAPI,
    context: Context,
) {
    val isAnimate = false
    val imageLoaderState = remember {
        mutableStateOf(
            isAnimate
        )
    }
    if (isAnimate){
        AsyncImage(
            model = data.media?.get(0)?.gif?.url,
            contentDescription = data.contentDescription,
            imageLoader = ImageLoader.Builder(context)
                .components {
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build(),
            modifier = Modifier.clickable(onClick = {
                imageLoaderState.value = !imageLoaderState.value
                Toast.makeText(context,imageLoaderState.value.toString(),Toast.LENGTH_SHORT).show()
            })
        )
    }
    else{
        AsyncImage(model = data.media?.get(0)?.gif?.url,
            contentDescription = data.contentDescription,
            modifier = Modifier.clickable(onClick = {
                imageLoaderState.value = !imageLoaderState.value
                Toast.makeText(context,imageLoaderState.value.toString(),Toast.LENGTH_SHORT).show()
            })
        )
    }
}

private fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<DataFromAPI>,
    context: Context,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { item ->
        GifGridItem(data = lazyPagingItems[item] ?: DataFromAPI(), context)
    }
}