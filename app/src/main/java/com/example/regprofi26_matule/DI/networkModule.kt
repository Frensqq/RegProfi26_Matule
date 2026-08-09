package com.example.regprofi26_matule.DI

import com.example.netlibrary.data.remote.PBApi
import com.example.netlibrary.data.remote.PBApiServis
import com.example.netlibrary.data.repository.PBRepository
import com.example.netlibrary.domain.repository.Repository
import com.example.netlibrary.network.IsConnect
import com.example.netlibrary.network.NetworkMonitor
import com.example.regprofi26_matule.Domain.UseCase
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.regprofi26_matule.Presentation.ViewModels.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val networkModule = module{

    single<PBApi> { PBApiServis.instance }
    single<IsConnect> { NetworkMonitor (androidContext())}

    single<Repository> { PBRepository(
        get<PBApi>(),
        get<IsConnect>(),
        androidContext()
    ) }

    factory { UseCase(get()) }

    viewModel{ SplashViewModel() }
    viewModel{ AuthViewModel(get()) }

}