package com.edavalos.mtx.util.address;

public record MtxAddress(
    int buildingNumber,
    String streetName,
    String optionalUnitAbbrevAndNumber,
    String cityName,
    String state,
    int zipCode
  ) { }