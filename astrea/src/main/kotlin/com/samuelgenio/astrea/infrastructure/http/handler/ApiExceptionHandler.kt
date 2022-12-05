package com.samuelgenio.astrea.infrastructure.http.handler

import com.fasterxml.jackson.databind.JsonMappingException.Reference
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.PropertyBindingException
import com.samuelgenio.astrea.infrastructure.http.handler.dto.Field
import com.samuelgenio.astrea.infrastructure.http.handler.dto.Problem
import com.samuelgenio.astrea.infrastructure.http.handler.enums.ProblemTypeEnum
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception
import java.time.LocalDateTime
import java.util.stream.Collectors

@ControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        val MSG_ERRO_GENERICA = "Ocorreu um erro interno inesperado no sistema."
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        val problemType = ProblemTypeEnum.PARAMETRO_INVALIDO
        val detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        val problemFields = ex.bindingResult.loadProblemFields()

        val problem = createProblemBuilder(status, problemType, detail)
            .apply {
                userMessage = detail
                fields = problemFields
            }

        return this.handleExceptionInternal(ex, problem, headers, status, request)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        var bodyResponse = body
        if (bodyResponse == null) {
            bodyResponse = Problem(
                status = status.value(),
                timestamp = LocalDateTime.now(),
                title = status.reasonPhrase,
                userMessage = MSG_ERRO_GENERICA
            )
        }
        return super.handleExceptionInternal(ex, bodyResponse, headers, status, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        if (ex is InvalidFormatException) {
            return handleInvalidFormat(ex as InvalidFormatException, headers, status, request);
        } else if (ex is PropertyBindingException) {
            return handlePropertyBinding(ex as PropertyBindingException, headers, status, request);
        }

        val problemType = ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL;
        val detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        val problem = createProblemBuilder(status, problemType, detail).apply {
            userMessage = MSG_ERRO_GENERICA
        }

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return super.handleNoHandlerFoundException(ex, headers, status, request)
    }

    fun handleInvalidFormat(ex: InvalidFormatException,
                            headers: HttpHeaders,
                            status: HttpStatus,
                            request: WebRequest): ResponseEntity<Any> {

        val path = joinPath(ex.path);

        val problemType = ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL;
        val detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
        path, ex.getValue(), ex.getTargetType().getSimpleName());

        val problem = createProblemBuilder(status, problemType, detail)
            .apply {
                userMessage = MSG_ERRO_GENERICA
            }

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    fun handlePropertyBinding(ex: PropertyBindingException,
                              headers: HttpHeaders,
                              status: HttpStatus,
                              request: WebRequest): ResponseEntity<Any> {

        val path = joinPath(ex.path);

        val problemType = ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL;
        val detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        val problem = createProblemBuilder(status, problemType, detail)
            .apply {
                userMessage = MSG_ERRO_GENERICA
            }

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    fun BindingResult.loadProblemFields(): List<Field> {
        return this.fieldErrors
            .stream()
            .map { fieldError ->  Field(name = fieldError.field, fieldError.defaultMessage) }
            .collect(Collectors.toList())
    }

    fun createProblemBuilder(status: HttpStatus,
                             problemType: ProblemTypeEnum, detail: String): Problem {
        return Problem(
            timestamp = LocalDateTime.now(),
            status = status.value(),
            type = problemType.path,
            title = problemType.title,
            detail = detail
        )
    }

    private fun joinPath(references: List<Reference>): String {
        return references.stream()
            .map{ref -> ref.fieldName}
        .collect(Collectors.joining("."));
    }

}