package com.example.foximages.ui

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.foximages.R
import com.example.foximages.pojo.DataFromAPI
import kotlinx.coroutines.flow.Flow

@Composable
fun LoadingUi() {
    CircularProgressIndicator(modifier = Modifier.height(50.dp).width(50.dp))
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
    var imageState by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .clickable {
                imageState = !imageState
            }
            .width(data.media?.get(0)?.gif?.dims?.get(0)?.dp ?: 20.dp)
            .height(data.media?.get(0)?.gif?.dims?.get(1)?.dp ?: 20.dp), content = {
            if (imageState) {
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
                    placeholder = painterResource(R.drawable.placeholder)
                )
            } else {
                AsyncImage(
                    model = data.media?.get(0)?.gif?.url,
                    contentDescription = data.contentDescription,
                    imageLoader = ImageLoader(context),
                    placeholder = painterResource(R.drawable.placeholder)
                )
            }
        })
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