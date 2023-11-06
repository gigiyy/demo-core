package com.example.core.ebl;

import com.example.core.model.Qualifiers;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@Import(EblDocumentService.class)
@Tag("performance")
class EblDocumentPerformanceTests {

	@Autowired
	EblRepository repository;

	@Autowired
	EblDocumentService subject;

	@Autowired
	ReactiveMongoOperations operations;

	private static final long multiplier = 10000L;

	@BeforeEach
	public void setup() {
		operations.collectionExists(ElectronicBillOfLading.class)
			.flatMap(exists -> exists ? operations.dropCollection(ElectronicBillOfLading.class) : Mono.empty())
			.then(operations.createCollection(ElectronicBillOfLading.class))
			.as(StepVerifier::create).expectNextCount(1).verifyComplete();

		operations.insertAll(startupData()).as(StepVerifier::create).expectNextCount(3 * multiplier).verifyComplete();
	}

	@Test
	public void should_return_all_records_when_search_by_county_city() {
		// given
		Qualifiers criteria = Qualifiers.builder().
			loadCountries(Set.of("usa")).loadCities(Set.of("shanghai")).build();
		// when
		Flux<ElectronicBillOfLading> found = subject.findBy(criteria);

		// then
		StepVerifier.create(found).expectNextCount(2 * multiplier).verifyComplete();
	}

	public List<ElectronicBillOfLading> startupData() {
		List<ElectronicBillOfLading> multiplied = new ArrayList<>();
		List<ElectronicBillOfLading> seed = List.of(
			ElectronicBillOfLading.builder()
				.blNumber("bl0001").carrier("oocl").shipper("shipper001")
				.consignee("consignee0002").loadingCountry("usa").loadingCity("la")
				.destinationCountry("singapore").destinationCity("singapore").build(),
			ElectronicBillOfLading.builder()
				.blNumber("bl0002").carrier("oolu").shipper("shipper003")
				.consignee("consignee0002").loadingCountry("china").loadingCity("shenzhen")
				.destinationCountry("singapore").destinationCity("singapore").build(),
			ElectronicBillOfLading.builder()
				.blNumber("bl0003").carrier("oocl").shipper("shipper003")
				.consignee("consignee0001").loadingCountry("china").loadingCity("shanghai")
				.destinationCountry("usa").destinationCity("la").build()
		);
		for (int i = 0; i < multiplier; i++) {
			multiplied.addAll(seed);
		}
		return multiplied;
	}
}
