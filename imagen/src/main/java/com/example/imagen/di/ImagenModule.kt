package com.example.imagen.di

import com.example.imagen.data.remote.GeminiImageSource
import com.example.imagen.data.repository.GetImageFromGeminiRepositoryImpl
import com.example.imagen.domain.repository.GetImageFromGeminiRepository
import com.example.imagen.domain.usecase.GetImageFromPromptUseCase
import org.koin.dsl.module

val imagenModule = module {
    single { GeminiImageSource() }
    single<GetImageFromGeminiRepository> { GetImageFromGeminiRepositoryImpl(get()) }
    single{ GetImageFromPromptUseCase(get()) }
}