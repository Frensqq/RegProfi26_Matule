package com.example.regprofi26_matule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.netlibrary.network.NetworkMonitor
import com.example.regprofi26_matule.DI.networkModule
import com.example.regprofi26_matule.Domain.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.Navigation
import com.example.regprofi26_matule.ui.theme.RegProfi26_MatuleTheme
import com.example.uikit.UI.MatuleTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    val isOnline = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkModuleMonitor = NetworkMonitor(this)
        isOnline.value = networkModuleMonitor.isConnected()

        startKoin {
            androidContext(this@MainActivity)
            modules(networkModule)
        }

        UserRepository.init(this)

        enableEdgeToEdge()
        setContent {
            MatuleTheme {
                Navigation(isOnline.value)
            }
        }
    }
}

