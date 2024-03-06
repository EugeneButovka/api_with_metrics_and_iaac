package com.eugenebutovka.test.api_with_metrics_and_iaac.exceptionHandler

import com.eugenebutovka.test.api_with_metrics_and_iaac.exception.UserFlowInternalException
import com.eugenebutovka.test.api_with_metrics_and_iaac.exception.WrongUserFlowInputException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.apache.catalina.connector.ClientAbortException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.lang.reflect.UndeclaredThrowableException

@ControllerAdvice
class ControllerExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ResponseBody
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(UserFlowInternalException::class)
    fun handleUserFlowInternalException(e: UserFlowInternalException): String {
        logger.debug { "handleUserFlowInternalException: $e" }

        return e.message ?: "Unknown User Flow Internal Exception"
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongUserFlowInputException::class)
    fun handleWrongUserFlowInputException(e: WrongUserFlowInputException): String {
        logger.debug { "handleWrongUserFlowInputException: $e" }
        return e.message ?: "Unknown Wrong User Flow Input Exception"
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): String {
        logger.debug { "handleMethodArgumentTypeMismatchException: $e" }
        logger.debug { e.stackTraceToString() }
        return e.message ?: "Unknown Method Argument Type Mismatch Exception"
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleControllerArgumentValidationException(e: MethodArgumentNotValidException): String {
        logger.debug { "handleControllerArgumentValidationExceptions: $e" }
        return e.message
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.SEE_OTHER)
    @ExceptionHandler(NoResourceFoundException::class)
    internal fun handleNoResourceFoundException(e: NoResourceFoundException): String {
        logger.debug { "NoResourceFoundException: $e" }
        return "Please use API docs at /swagger-ui.html for the correct endpoint"
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(ClientAbortException::class)
    internal fun handleClientAbortException(e: ClientAbortException) {
        // Connection aborted by client, can't return anything
        logger.debug { "ClientAbortException: $e" }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleGenericExceptions(
        e: Exception,
        request: HttpServletRequest
    ): Any {
        val exception = if (e is UndeclaredThrowableException) e.cause else e
        logger.error(exception) { "Wrapping exception in custom response" }

        return object {
            val message = exception?.message ?: "Unknown error"
            val trace = exception?.stackTraceToString()
        }
    }
}
