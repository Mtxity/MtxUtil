package com.edavalos.mtx.util.address;

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
        Objects.requireNonNull(streetName);
        Objects.requireNonNull(cityName);
        Objects.requireNonNull(state);
        Objects.requireNonNull(zipCode);

        if (!MtxStringUtil.isValidZipcode(zipCode)) {
            throw new IllegalArgumentException(zipCode);
        }
    }

    @Override
    public String toString() {
        String unitNumber = this.optionalUnitAbbrevAndNumber == null ? "" : " " + this.optionalUnitAbbrevAndNumber;
        return this.buildingNumber + " " + this.streetName + unitNumber + "\n" +
               this.cityName + ", " + this.state + " " + this.zipCode;
    }
}