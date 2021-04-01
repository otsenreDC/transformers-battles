package io.banana.transformersbattle.domain.view_models

import io.banana.transformersbattle.domain.flaws.Flaw

interface IViewModel<T : Any> {
    fun onLoading(isLoading: Boolean)
    fun onDone(data: T)
    fun onError(flaw: Flaw?)
}