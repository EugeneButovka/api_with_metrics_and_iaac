package com.eugenebutovka.test.api_with_metrics_and_iaac.config

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "management.metrics")
class PrometheusCustomMetricsConfig(internal val meterRegistry: MeterRegistry) {
    private var disableCustomMetrics = false

    fun setDisableCustomMetrics(disableCustomMetrics: Boolean) {
        this.disableCustomMetrics = disableCustomMetrics
    }

    fun getCounter(exceptionName: String): Counter {
        val counter: Counter.Builder = Counter.builder("user_flow_exceptions")
            .description("Counts user flow exceptions exception occurrences")
            .tag("exception", exceptionName) // tag and exception name to differentiate from prometheus basic metrics
        return counter.register(meterRegistry)
    }

    fun incrementErrorCounter(exceptionName: String) {
        if (disableCustomMetrics) return
        return this.getCounter(exceptionName).increment(1.0)
    }
}
