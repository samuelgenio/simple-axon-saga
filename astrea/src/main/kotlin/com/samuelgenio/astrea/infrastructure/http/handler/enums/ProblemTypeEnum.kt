package com.samuelgenio.astrea.infrastructure.http.handler.enums

enum class ProblemTypeEnum(val path: String, val title: String) {

    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),

}