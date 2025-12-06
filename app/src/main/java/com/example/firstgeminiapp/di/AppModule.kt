package com.example.firstgeminiapp.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstgeminiapp.data.repository.SummaryRepositoryImpl
import com.example.firstgeminiapp.domain.repository.SummaryRepository
import com.example.firstgeminiapp.domain.usecase.GetSummaryUseCase
import com.example.firstgeminiapp.ui.viewModel.GetSummaryViewModel
import org.koin.dsl.module

object Utils {
    const val DEBUG_LOG_TAG = "GeminiApp"
}
val appModule = module {

    single<SummaryRepository> { SummaryRepositoryImpl(get()) }
    factory{ GetSummaryUseCase(get()) }
    viewModel { GetSummaryViewModel(get()) }
}