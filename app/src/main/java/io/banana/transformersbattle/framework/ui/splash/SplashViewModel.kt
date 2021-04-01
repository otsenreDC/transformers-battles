package io.banana.transformersbattle.framework.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.use_cases.GetAccessTokenUseCase
import io.banana.transformersbattle.domain.view_models.ISplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel(getTokenUseCaseDI: GetAccessTokenUseCase) : ViewModel(), ISplashViewModel {

    override val getTokenUseCase: GetAccessTokenUseCase = getTokenUseCaseDI

    val onLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val onErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val onNavigateMainScreen: MutableLiveData<Boolean> = MutableLiveData()

    fun start() {
        viewModelScope.launch {
            hasToken()
        }
    }

    override fun onLoading(isLoading: Boolean) {
        onLoadingLiveData.postValue(isLoading)
    }

    override fun onDone(data: String) {
        viewModelScope.launch {
            delay(500)
            onNavigateMainScreen.postValue(true)
        }
    }

    override fun onError(flaw: Flaw?) {
        onErrorLiveData.postValue(flaw?.message ?: "Unexpected error")
    }
}