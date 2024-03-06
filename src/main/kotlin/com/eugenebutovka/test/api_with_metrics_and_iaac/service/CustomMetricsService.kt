package com.eugenebutovka.test.api_with_metrics_and_iaac.service

import com.eugenebutovka.test.api_with_metrics_and_iaac.config.PrometheusCustomMetricsConfig
import org.springframework.stereotype.Service

@Service
class CustomMetricsService(
    internal val metricConfig: PrometheusCustomMetricsConfig? = null
) {
    fun registerUserFlowCriticalProblem(exception: RuntimeException) {
        metricConfig?.incrementErrorCounter(exception.javaClass.simpleName)
    }
}
