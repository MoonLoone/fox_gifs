package com.example.foximages.ui

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.foximages.R
import com.example.foximages.pojo.DataFromApi

@Composable
fun UploadingDataPlug(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier.background(Color.Yellow))
}

@Composable
fun SearchForm(callback: (text: String) -> Unit) {
    Row(
        Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
            .border(2.dp, Color.Black, RectangleShape)
            .background(Color.Yellow)
            .width(200.dp)
    ) {
        val maxLength = 20
        var text by remember {
            mutableStateOf(TextFieldValue(""))
        }
        BasicTextField(
            value = text,
            onValueChange = {
               if(it.text.length <=maxLength) text = it
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    callback.invoke(text.text)
                }
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (text.text.isEmpty()) {
                    Text(stringResource(id = R.string.search_fish_bone))
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
fun GifListUi(items: LazyPagingItems<DataFromApi>) {

    val context = LocalContext.current
    val resources = context.resources
    val orientation = LocalConfiguration.current.orientation

    val spanCount =
        if (orientation == Configuration.ORIENTATION_PORTRAIT) resources.getInteger(
            R.integer.vertical_column_count
        )
        else resources.getInteger(R.integer.horizontal_column_count)
    LazyVerticalGrid(columns = GridCells.Fixed(spanCount),
        content = {
            this.items<DataFromApi>(items) {
                GifGridItem(data = it ?: DataFromApi())
            }
        })
}

@Composable
private fun GifGridItem(
    data: DataFromApi,
) {

    val context = LocalContext.current

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
    lazyPagingItems: LazyPagingItems<DataFromApi>,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { item ->
        GifGridItem(data = lazyPagingItems[item] ?: DataFromApi())
    }
}