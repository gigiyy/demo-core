package com.example.core.ebl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ElectronicBillOfLading {

	@Id
	String id;
	String blNumber;
	String loadingCountry;
	String loadingCity;
	String destinationCountry;
	String destinationCity;
	String shipper;
	String consignee;
	String carrier;
	String holder;
	String owner;
}
