package com.example.firstgeminiapp.di

import com.example.firstgeminiapp.data.remote.GeminiDataSource
import com.example.firstgeminiapp.data.repository.SummaryRepositoryImpl
import com.example.firstgeminiapp.domain.repository.SummaryRepository
import com.example.firstgeminiapp.domain.usecase.GetSummaryUseCase
import com.example.firstgeminiapp.ui.viewModel.GetSummaryViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

object Utils {
    const val DEBUG_LOG_TAG = "GeminiApp"
}
val appModule = module {
    single{ GeminiDataSource() }
    single<SummaryRepository> { SummaryRepositoryImpl(get()) }
    single { GetSummaryUseCase(get()) }
    viewModel { GetSummaryViewModel(get<GetSummaryUseCase>()) }
}