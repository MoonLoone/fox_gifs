package com.example.foximages

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.bumptech.glide.Glide
import com.example.foximages.database.AppDatabase
import com.example.foximages.database.DatabaseEntity
import com.example.foximages.utils.RetrofitBuilder
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class ListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val job = getDataFromApi()
        job.start()
        val db = AppDatabase.getInstance(requireContext().applicationContext)
        val resultsFromDB = db.dao().getAll()
        return ComposeView(requireContext()).apply {
            setContent {
                var count by remember { mutableStateOf(listOf<DatabaseEntity>())}
                LaunchedEffect(Unit){
                    resultsFromDB.collect{
                        count = it
                    }
                }
                LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                    items(count){
                        val imageLoader = ImageLoader.Builder(context)
                            .components {
                                if (SDK_INT >= 28) {
                                    add(ImageDecoderDecoder.Factory())
                                } else {
                                    add(GifDecoder.Factory())
                                }
                            }
                            .build()
                        AsyncImage(
                            model = it.url,
                            imageLoader = imageLoader,
                            contentDescription = null
                        )
                    }
                })
            }
        }
    }

    private fun getDataFromApi(): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val data = RetrofitBuilder.apiService.getData().results
            val saveInDb = mutableListOf<DatabaseEntity>()
            val db = AppDatabase.getInstance(requireContext().applicationContext)
            data?.forEach {
                saveInDb.add(
                    DatabaseEntity(
                        id_from_api = it.id,
                        contentDescription = it.contentDescription,
                        url = it.media?.get(0)?.gif?.url,
                        width = it.media?.get(0)?.gif?.dims?.get(0),
                        height = it.media?.get(0)?.gif?.dims?.get(1),
                    )
                )
            }
            db.dao().insertAll(saveInDb.toList())
        }

    companion object {
        fun newInstance() =
            ListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}