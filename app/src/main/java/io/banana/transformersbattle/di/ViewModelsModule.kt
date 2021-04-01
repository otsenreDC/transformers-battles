package io.banana.transformersbattle.di

import io.banana.transformersbattle.framework.ui.battle.BattleViewModel
import io.banana.transformersbattle.framework.ui.create_transformer.TransformersWorkshopViewModel
import io.banana.transformersbattle.framework.ui.splash.SplashViewModel
import io.banana.transformersbattle.framework.ui.transformer_list.TransformerListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SplashViewModel(get())
    }
    viewModel {
        TransformerListViewModel(get(), get())
    }
    viewModel {
        TransformersWorkshopViewModel(get(), get())
    }
    viewModel {
        BattleViewModel()
    }
}