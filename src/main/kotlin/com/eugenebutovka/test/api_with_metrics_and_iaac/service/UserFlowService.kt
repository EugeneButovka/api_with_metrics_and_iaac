package com.eugenebutovka.test.api_with_metrics_and_iaac.service

import com.eugenebutovka.test.api_with_metrics_and_iaac.exception.UserFlowInternalException
import com.eugenebutovka.test.api_with_metrics_and_iaac.exception.WrongUserFlowInputException
import org.springframework.stereotype.Service

@Service
class UserFlowService(
    val customMetricsService: CustomMetricsService
) {
    fun parseUserInput(userInput: Int): String {
        if (userInput == 1) return "user flow success"
        if (userInput == 2) {
            val e = UserFlowInternalException("User Flow input is causing user flow exception")
            customMetricsService.registerUserFlowCriticalProblem(e)
            throw e
        }
        throw WrongUserFlowInputException("Input not correct, values 1 or 2 required")
    }
}
