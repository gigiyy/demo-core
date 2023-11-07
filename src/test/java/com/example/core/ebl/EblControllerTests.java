package com.example.core.ebl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(EblController.class)
public class EblControllerTests {

	@Autowired
	WebTestClient client;

	@MockBean
	EblDocumentService service;

	Mono<ElectronicBillOfLading> response;
	ElectronicBillOfLading ebl;

	@BeforeEach
	public void setup() {
		ebl = ElectronicBillOfLading.builder()
			.blNumber("temp_blNumber")
			.carrier("oocl")
			.destinationCity("shanghai")
			.destinationCountry("china")
			.loadingCity("newYork")
			.loadingCountry("usa")
			.build();
		response = Mono.just(ebl.toBuilder().id("temp_id").build());
	}

	@Test
	public void should_return_ebl_when_received_get_request() {
		// given
		doReturn(response).when(service).save(eq(ebl));

		// when
		client.post().uri("/api/seed").body(Mono.just(ebl), ElectronicBillOfLading.class)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectAll(
				spec -> spec.expectStatus().isOk(),
				spec -> spec.expectBody().jsonPath("$.id").isEqualTo("temp_id"),
				spec -> spec.expectBody().jsonPath("$.carrier").isEqualTo("oocl")
			);

		// then

	}

}
