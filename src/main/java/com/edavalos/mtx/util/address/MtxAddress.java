package com.edavalos.mtx.util.address;

import java.util.Objects;

public record MtxAddress(
    int buildingNumber,
    String streetName,
    String optionalUnitAbbrevAndNumber,
    String cityName,
    String state,
    int zipCode
  ) {

    public MtxAddress {
      Objects.requireNonNull(buildingNumber);
      Objects.requireNonNull(streetName);
      Objects.requireNonNull(cityName);
      Objects.requireNonNull(state);
      Objects.requireNonNull(zipCode);

      // Validate zipcode
      // Validate state abbrev
    }
}