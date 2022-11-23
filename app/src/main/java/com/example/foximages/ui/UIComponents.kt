package com.example.foximages.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
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
fun SearchForm(callback: (text: String) -> Unit) {
    Row(
        Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
            .border(2.dp, Color.Black, RectangleShape)
            .background(Color.Yellow)
            .width(200.dp)
    ) {
        var text by remember {
            mutableStateOf(TextFieldValue(""))
        }
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    callback.invoke(text.text)
                }
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (text.text.isEmpty()) {
                    Text("Enter your search parameter")
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun LoadingUi() {
    CircularProgressIndicator(
        modifier = Modifier
            .height(50.dp)
            .width(50.dp)
    )
}

@Composable
fun GifListUi(gifsList: Flow<PagingData<DataFromAPI>>, context: Context) {
    val items: LazyPagingItems<DataFromAPI> = gifsList.collectAsLazyPagingItems()
    val spanCount =
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) context.resources.getInteger(
            R.integer.vertical_column_count
        )
        else context.resources.getInteger(R.integer.horizontal_column_count)
    LazyVerticalGrid(columns = GridCells.Fixed(spanCount),
        content = {
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
            .background(Color.White)
            .padding(bottom = 10.dp, start = 2.dp, end = 2.dp, top = 5.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true,
        content = {
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
                    placeholder = painterResource(R.drawable.placeholder),
                )
            } else {
                AsyncImage(
                    model = data.media?.get(0)?.gif?.url,
                    contentDescription = data.contentDescription,
                    imageLoader = ImageLoader(context),
                    placeholder = painterResource(R.drawable.placeholder),
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