package com.eugenebutovka.test.api_with_metrics_and_iaac.controller

import com.eugenebutovka.test.api_with_metrics_and_iaac.service.UserFlowService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/flow")
class UserFlowController(
    val userFlowService: UserFlowService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping(
        value = ["/{userInput}"],
        headers = ["Accept=*/*"],
        consumes = [MediaType.ALL_VALUE],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(
        summary = "Starts the user flow",
        description = "Starts the user flow in case of correct input. URL parameter 1 for correct input, 2 for user flow exception, else for bad request"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User flow input successful"),
        ApiResponse(responseCode = "400", description = "Input not correct"),
        ApiResponse(responseCode = "417", description = "Input is causing user flow exception"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    fun startUserFlow(@PathVariable(required = true, value = "userInput") @Valid userInput: Int): String {
        logger.info { "user flow controller - get with input param $userInput" }

        return userFlowService.parseUserInput(userInput)
    }
}
