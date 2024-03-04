package com.eugenebutovka.test.api_with_metrics_and_iaac.controller

import com.eugenebutovka.test.api_with_metrics_and_iaac.exception.UserFlowInternalException
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/flow")
class UserFlowController {
    private val logger = KotlinLogging.logger {}

    @GetMapping(
        value = ["/{userInput}"],
        headers = ["Accept=*/*"],
        consumes = [MediaType.ALL_VALUE],
        produces = [MediaType.ALL_VALUE]
    )
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
    fun startUserFlow(@PathVariable(required = true, value = "userInput") @Valid userInput: Int):ResponseEntity<String> {
        logger.info { "user flow controller - get" }

        if (userInput == 1) return ResponseEntity<String>("user flow success", HttpStatus.OK)
        if (userInput == 2) throw UserFlowInternalException("User Flow input is causing user flow exception")
        return ResponseEntity<String>("Input not correct, values 1 or 2 required", HttpStatus.BAD_REQUEST)
    }
}
