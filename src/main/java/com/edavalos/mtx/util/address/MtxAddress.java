package com.edavalos.mtx.util.address;

import java.text.ParseException;
import java.util.Objects;

import com.edavalos.mtx.util.string.MtxStringUtil;

public record MtxAddress(
    int buildingNumber,
    String streetName,
    String optionalUnitAbbrevAndNumber,
    String cityName,
    String state,
    String zipCode
  ) {

    public MtxAddress {
      Objects.requireNonNull(buildingNumber);
      Objects.requireNonNull(streetName);
      Objects.requireNonNull(cityName);
      Objects.requireNonNull(state);
      Objects.requireNonNull(zipCode);

      if (!MtxStringUtil.isValidZipcode(zipCode)) {
        throw new ParseException(zipCode, 0);
      }
    }

    @Override
    public String toString() {
      return this.buildingNumber + this.streetName + this.optionalUnitAbbrevAndNumber + "\n" +
             this.cityName + ", " + this.state + this.zipCode;
    }
}