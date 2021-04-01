package io.banana.transformersbattle.domain.models

import io.banana.transformersbattle.domain.flaws.Flaw

sealed class Result<T>;

class Success<T>(val data: T) : Result<T>()

class Failure<T>(val flaw: Flaw) : Result<T>()