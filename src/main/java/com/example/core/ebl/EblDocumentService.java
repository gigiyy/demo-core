package com.example.core.ebl;

import com.example.core.model.Qualifiers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EblDocumentService {

	private final EblRepository repository;

	public Flux<ElectronicBillOfLading> findAll() {
		return repository.findAll();
	}

	public Flux<ElectronicBillOfLading> findBy(Qualifiers qualifiers) {
		return repository.findAll().filter(qualifiers.match());
	}
}
