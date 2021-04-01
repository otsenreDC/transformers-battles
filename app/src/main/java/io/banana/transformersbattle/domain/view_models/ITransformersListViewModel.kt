package io.banana.transformersbattle.domain.view_models

import io.banana.transformersbattle.domain.models.Failure
import io.banana.transformersbattle.domain.models.Success
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.domain.use_cases.DeleteTransformerUseCase
import io.banana.transformersbattle.domain.use_cases.GetTransformersUseCase

interface ITransformersListViewModel : IViewModel<List<Transformer>> {
    val getTransformers: GetTransformersUseCase
    val deleteTransformer: DeleteTransformerUseCase

    fun onDeleted(deleted: Boolean)

    suspend fun load() {
        onLoading(true)
        when (val result = getTransformers.execute()) {
            is Success -> onDone(result.data)
            is Failure -> onError(result.flaw)
        }
        onLoading(false)

    }

    suspend fun delete(id: String) {
        onLoading(true)
        when (val result = deleteTransformer.execute(id)) {
            is Success -> onDeleted(result.data)
            is Failure -> onError(result.flaw)
        }
        onLoading(false)
    }
}