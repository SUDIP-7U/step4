package com.tridosha.balance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tridosha.balance.navigation.TridoshaApp
import com.tridosha.balance.ui.theme.TridoshaTheme

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
