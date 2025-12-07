package com.example.imagen.di

import com.example.imagen.data.remote.ImageSource
import com.example.imagen.data.repository.GetImageFromGeminiRepositoryImpl
import com.example.imagen.domain.repository.GetImageFromGeminiRepository
import com.example.imagen.domain.usecase.GetImageFromPromptUseCase
import com.example.imagen.ui.viewModel.GetImageFromGeminiViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val imagenModule = module {
    single { ImageSource() }
    single<GetImageFromGeminiRepository> { GetImageFromGeminiRepositoryImpl(get()) }
    single{ GetImageFromPromptUseCase(get()) }
    viewModel{ GetImageFromGeminiViewModel(get()) }
}