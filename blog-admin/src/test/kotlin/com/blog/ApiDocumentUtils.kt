package com.blog

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors

object ApiDocumentUtils {
    val documentRequest: OperationRequestPreprocessor
        get() = Preprocessors.preprocessRequest(
            Preprocessors.modifyUris()
                .scheme("http")
                .removePort(),
            Preprocessors.prettyPrint())
    val documentResponse: OperationResponsePreprocessor
        get() {
            return Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        }
}
