package com.example.foximages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.foximages.database.AppDatabase
import com.example.foximages.models.Gifs
import com.example.foximages.models.RawData
import com.example.foximages.utils.RetrofitBuilder
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, ListFragment())
                .addToBackStack("mainStack")
                .commit()
        }
}