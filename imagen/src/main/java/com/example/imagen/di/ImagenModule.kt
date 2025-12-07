package com.example.imagen.di

import com.example.imagen.data.remote.GeminiImageSource
import org.koin.dsl.module

val imagenModule = module {
    single { GeminiImageSource() }
}