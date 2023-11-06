package com.example.core.model;

import com.example.core.ebl.ElectronicBillOfLading;
import java.util.Set;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Qualifiers {

	private Set<String> carriers;
	private Set<String> loadCountries;
	private Set<String> loadCities;
	private Set<String> destCountries;
	private Set<String> destCities;

	private Boolean contains(Set<String> qualifier, String value) {
		return !notEmpty(qualifier) || qualifier.contains(value);
	}

	private Boolean notEmpty(Set<String> set) {
		return set != null && !set.isEmpty();
	}


	public Predicate<ElectronicBillOfLading> match() {
		return ebl -> contains(carriers, ebl.getCarrier()) &&
			matchCountryCity(loadCountries, loadCities, ebl.getLoadingCountry(), ebl.getLoadingCity()) &&
			matchCountryCity(destCountries, destCities, ebl.getDestinationCountry(), ebl.getDestinationCity());

	}

	private boolean matchCountryCity(Set<String> countries, Set<String> cities, String country, String city) {
		if (notEmpty(countries) && notEmpty(cities)) {
			return contains(countries, country) || contains(cities, city);
		}
		if (notEmpty(countries)) {
			return contains(countries, country);
		}
		if (notEmpty(cities)) {
			return contains(cities, city);
		}
		return true;
	}
}
