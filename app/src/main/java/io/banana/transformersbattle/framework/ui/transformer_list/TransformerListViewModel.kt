package io.banana.transformersbattle.framework.ui.transformer_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.banana.transformersbattle.domain.flaws.Flaw
import io.banana.transformersbattle.domain.flaws.NoTransformerRegisterFlaw
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.domain.use_cases.DeleteTransformerUseCase
import io.banana.transformersbattle.domain.use_cases.GetTransformersUseCase
import io.banana.transformersbattle.domain.view_models.ITransformersListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransformerListViewModel(
    getUseCase: GetTransformersUseCase,
    deleteUseCase: DeleteTransformerUseCase
) : ViewModel(), ITransformersListViewModel {

    override val getTransformers: GetTransformersUseCase = getUseCase
    override val deleteTransformer: DeleteTransformerUseCase = deleteUseCase

    var transformers: List<Transformer> = emptyList()
        private set

    val onListLoadedLiveData: MutableLiveData<List<Transformer>> = MutableLiveData()
    val onLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val onErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onLoading(isLoading: Boolean) {
        onLoadingLiveData.postValue(isLoading)
    }

    override fun onDone(data: List<Transformer>) {
        transformers = data
        onListLoadedLiveData.postValue(data)
    }

    override fun onDeleted(deleted: Boolean) {
        viewModelScope.launch {
            if (deleted) {
                load()
            } else {
                onErrorLiveData.postValue("Error deleting the transformer.")
            }
        }
    }

    override fun onError(flaw: Flaw?) {
        val message = when (flaw) {
            is NoTransformerRegisterFlaw -> {
                onListLoadedLiveData.postValue(emptyList())
            }
            else -> onErrorLiveData.postValue("Error loading list")
        }
    }

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            load()
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            load()
        }
    }

    fun remove(id: String) {
        viewModelScope.launch {
            delete(id)
        }
    }
}

