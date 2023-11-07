package com.example.core.ebl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EblController {

	final EblDocumentService service;

	@PostMapping("/seed")
	public Mono<ElectronicBillOfLading> seedValue(@RequestBody ElectronicBillOfLading ebl) {
		return service.save(ebl);
	}
}
