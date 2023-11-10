package com.example.core.ebl;

import io.micrometer.observation.Observation;
import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EblObservationHandler implements ObservationHandler<Observation.Context> {

	@Override
	public void onStart(Context context) {
		log.info("before running context [{}]", context.getName());
	}

	@Override
	public void onStop(Context context) {
		log.info("after running context [{}]", context.getName());
	}

	@Override
	public boolean supportsContext(Context context) {
		return true;
	}
}
