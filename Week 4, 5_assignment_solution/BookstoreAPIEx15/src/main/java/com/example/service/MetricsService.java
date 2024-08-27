package com.example.service;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class MetricsService {

	private final Counter customCounter;

	public MetricsService(MeterRegistry meterRegistry) {
		this.customCounter = meterRegistry.counter("custom_metric_count", "type", "example");
	}

	public void incrementCustomCounter() {
		this.customCounter.increment();
	}
}
