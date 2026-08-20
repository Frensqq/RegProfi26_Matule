package com.example.regprofi26_matule.Presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.createMatuleTypography
import io.ktor.websocket.Frame

@Composable
fun NoInternetScreen(){
    Box(
        Modifier.fillMaxSize()
            .background(MatuleTheme.colors.accent),
        contentAlignment = Alignment.Center
    ) {


        Text("No Internet",
            style = createMatuleTypography().title1Heavy,
            color = MatuleTheme.colors.white
        )

    }
}