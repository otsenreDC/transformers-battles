package io.banana.transformersbattle.domain.flaws

open class Flaw(val message: String? = null, val cause: Throwable? = null)

class ErrorAccessingLocalStorage : Flaw()
class NoAvailableTokenFlaw : Flaw()
class NoTransformerRegisterFlaw : Flaw()