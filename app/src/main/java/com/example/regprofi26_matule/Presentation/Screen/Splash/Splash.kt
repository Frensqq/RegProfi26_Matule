package com.example.regprofi26_matule.Presentation.Screen.Splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.ViewModels.SplashViewModel

@Composable
fun SplashScreen(navHostController: NavHostController,splashViewModel: SplashViewModel) {
    splashViewModel.launch(navHostController = navHostController)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {

                drawRect(
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0f to Color(0xFF00C07F).copy(alpha = 0.80f),
                            0.3f to Color(0xFFA6E6D3),
                            0.7f to Color(0xFFA6E6D3),
                            1f to Color(0xFF00C07F).copy(alpha = 0.70f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height)
                    )
                )

                drawRect(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color(0xFFA6E6D3).copy(alpha = 0.05f),
                            0.29f to Color(0xFF63D4B0).copy(alpha = 0.50f),
                            0.50f to Color(0xFF00C07F),
                            0.71f to Color(0xFF63D4B0).copy(alpha = 0.50f),
                            1f to Color(0xFFA6E6D3).copy(alpha = 0.05f)
                        )
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Matule",
            color = Color.White,
            fontSize = 40.sp
        )
    }
}

