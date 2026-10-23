package com.example.step4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.step4.navigation.TridoshaApp
import com.example.step4.ui.theme.TridoshaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TridoshaTheme {
                TridoshaApp()
            }
        }
    }
}
