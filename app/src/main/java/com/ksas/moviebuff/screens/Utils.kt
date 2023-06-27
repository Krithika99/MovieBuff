package com.ksas.moviebuff.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.ksas.moviebuff.R
import javax.inject.Inject

const val TAG = "Screens"

@Composable
fun circularProgressIndicator() {
    val strokeWidth = 7.dp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp), color = Color.Gray, strokeWidth = strokeWidth
        )
    }
}

val customFontMainHeading = FontFamily(
    Font(R.font.robotomedium)
)

val customFontSubHeading = FontFamily(
    Font(R.font.robotoregular)
)

val customFontText = FontFamily(
    Font(R.font.robotolightitalic)
)


class FavoriteScreenStateHolder @Inject constructor() {
    private val sharedPreferences = SharedPreferenceManager.getSharedPreferences()

    fun isFavorite(movieId: String): Boolean {
        return sharedPreferences.getBoolean(movieId, false)
    }

    fun toggleFavorite(movieId: String) {
        val currentState = isFavorite(movieId)
        sharedPreferences.edit {
            putBoolean(movieId, !currentState)
            apply()
        }
    }
}

object SharedPreferenceManager {
    private const val PREFS_NAME = "Favorites"
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getSharedPreferences(): SharedPreferences {
        if (!::sharedPreferences.isInitialized) {
            throw IllegalStateException("SharedPreferenceManager is not initialized. Call initialize() first.")
        }
        return sharedPreferences
    }
}


