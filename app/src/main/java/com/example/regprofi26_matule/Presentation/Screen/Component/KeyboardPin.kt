package com.example.regprofi26_matule.Presentation.Screen.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.regprofi26_matule.R
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH

@Composable
fun PinKeyboard(
    onComplete: (String) -> Unit
) {
    var code by remember { mutableStateOf("") }

    val accent = MatuleTheme.colors.accent
    val keyColor = Color(0xFFF4F4F8)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .border(
                            width = 1.dp,
                            color = accent,
                            shape = CircleShape
                        )
                        .background(
                            color = if (index < code.length)
                                accent
                            else
                                Color.Transparent,
                            shape = CircleShape
                        )
                )
            }
        }

        SpacerH(60)

        // 1 - 9
        listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9")
        ).forEach { row ->

            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                row.forEach { number ->

                    NumberKey(
                        number = number,
                     {
                        if (code.length < 4) {
                            code += number

                            if (code.length == 4) {
                                onComplete(code)
                            }
                        }
                    })
                }
            }
        }

        // Последний ряд: пусто / 0 / удалить
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(Modifier.size(80.dp))

            NumberKey(
                number = "0",
             {
                if (code.length < 4) {
                    code += "0"

                    if (code.length == 4) {
                        onComplete(code)
                    }
                }
            }
            )

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        if (code.isNotEmpty()) {
                            code = code.dropLast(1)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.del),
                    contentDescription = null,
                    tint = MatuleTheme.colors.black
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 375,
    heightDp = 812
)
@Composable
private fun PinKeyboardPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PinKeyboard { }
    }
}