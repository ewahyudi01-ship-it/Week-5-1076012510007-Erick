package com.eric.week5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.eric.week5.expenses.ui.screen.viewexpenses
import com.eric.week5.expenses.ui.theme.Week5Theme
import com.eric.week5.quiz.ui.screen.viewquiz

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    viewquiz()

                }
            }
        }
    }
}
