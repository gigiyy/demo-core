package com.example.core.ebl;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EblRepository extends ReactiveMongoRepository<ElectronicBillOfLading, String> {

}
