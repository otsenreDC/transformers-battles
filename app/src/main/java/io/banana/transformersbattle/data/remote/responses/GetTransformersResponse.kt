package io.banana.transformersbattle.data.remote.responses

import io.banana.transformersbattle.data.remote.dtos.TransformerDTO

class GetTransformersResponse {
    var transformers: List<TransformerDTO> = emptyList()
}