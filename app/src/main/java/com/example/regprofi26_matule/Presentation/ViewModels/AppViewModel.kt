package com.example.regprofi26_matule.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import com.example.netlibrary.network.IsConnect
import com.example.regprofi26_matule.Domain.Notification.NotificationScheduler
import com.example.regprofi26_matule.Domain.Repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel(
    private val notificationScheduler: NotificationScheduler,
    private val userRepository: UserRepository,
    private val networkMonitor: IsConnect
) : ViewModel() {

    private val _isOnline = MutableStateFlow(false)

    val isOnline: StateFlow<Boolean> =
        _isOnline.asStateFlow()

    init {
        refreshNetworkState()
    }

    fun onAppStarted() {
        notificationScheduler.cancel()
        refreshNetworkState()
    }

    fun onAppStopped() {

        if (userRepository.notification) {
            notificationScheduler.schedule()
        }
    }

    fun refreshNetworkState() {
        _isOnline.value =
            networkMonitor.isConnected()
    }
}