package com.example.core.model;

import com.example.core.ebl.ElectronicBillOfLading;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class QualifiersTests {

	Flux<ElectronicBillOfLading> ebls;

	@BeforeEach
	public void setup() {
		ebls = Flux.just(
			ElectronicBillOfLading.builder()
				.carrier("oolu")
				.loadingCountry("usa").loadingCity("la")
				.destinationCountry("china").destinationCity("shanghai")
				.build(),
			ElectronicBillOfLading.builder()
				.carrier("cosco")
				.loadingCountry("usa").loadingCity("newYork")
				.destinationCountry("singapore").destinationCity("xinGang")
				.build(),
			ElectronicBillOfLading.builder()
				.carrier("oolu")
				.loadingCountry("canada").loadingCity("vancouver")
				.destinationCountry("singapore").destinationCity("xinGang")
				.build(),
			ElectronicBillOfLading.builder()
				.carrier("oolu")
				.loadingCountry("canada").loadingCity("toronto")
				.destinationCountry("china").destinationCity("shanghai")
				.build(),
			ElectronicBillOfLading.builder()
				.carrier("cosco")
				.loadingCountry("china").loadingCity("shanghai")
				.destinationCountry("singapore").destinationCity("xinGang")
				.build(),
			ElectronicBillOfLading.builder()
				.carrier("cosco")
				.loadingCountry("china").loadingCity("shenzhen")
				.destinationCountry("usa").destinationCity("newYork")
				.build()
		);
	}

	@Test
	public void should_return_ebl_list_when_called_with_criteria() {
		// given
		Qualifiers qualifiers = Qualifiers.builder().loadCountries(Set.of("usa")).loadCities(Set.of("shanghai"))
			.build();
		// when
		Flux<ElectronicBillOfLading> filtered = ebls.filter(qualifiers.match());

		// then
		StepVerifier.create(filtered).expectNextCount(3).verifyComplete();
	}

	@Test
	public void should_return_expected_ebl_when_called_with_full_criteria() {
		// given
		Qualifiers qualifiers = Qualifiers.builder()
			.carriers(Set.of("cosco")).loadCities(Set.of("shanghai")).destCountries(Set.of("singapore")).build();
		// when
		Flux<ElectronicBillOfLading> filtered = ebls.filter(qualifiers.match());

		// then
		StepVerifier.create(filtered).expectNextCount(1).verifyComplete();
	}

}