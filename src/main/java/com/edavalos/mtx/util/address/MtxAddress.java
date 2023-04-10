package com.edavalos.mtx.util.address;

import java.util.HashMap;
import java.util.Map;
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

    private static final Map<String, String> STATES_TO_CODES = new HashMap<>();
    private static final Map<String, String> CODES_TO_STATES = new HashMap<>();
    static {
        STATES_TO_CODES.put("Alabama","AL");
        STATES_TO_CODES.put("Alaska","AK");
        STATES_TO_CODES.put("Alberta","AB");
        STATES_TO_CODES.put("American Samoa","AS");
        STATES_TO_CODES.put("Arizona","AZ");
        STATES_TO_CODES.put("Arkansas","AR");
        STATES_TO_CODES.put("Armed Forces (AE)","AE");
        STATES_TO_CODES.put("Armed Forces Americas","AA");
        STATES_TO_CODES.put("Armed Forces Pacific","AP");
        STATES_TO_CODES.put("British Columbia","BC");
        STATES_TO_CODES.put("California","CA");
        STATES_TO_CODES.put("Colorado","CO");
        STATES_TO_CODES.put("Connecticut","CT");
        STATES_TO_CODES.put("Delaware","DE");
        STATES_TO_CODES.put("District Of Columbia","DC");
        STATES_TO_CODES.put("Florida","FL");
        STATES_TO_CODES.put("Georgia","GA");
        STATES_TO_CODES.put("Guam","GU");
        STATES_TO_CODES.put("Hawaii","HI");
        STATES_TO_CODES.put("Idaho","ID");
        STATES_TO_CODES.put("Illinois","IL");
        STATES_TO_CODES.put("Indiana","IN");
        STATES_TO_CODES.put("Iowa","IA");
        STATES_TO_CODES.put("Kansas","KS");
        STATES_TO_CODES.put("Kentucky","KY");
        STATES_TO_CODES.put("Louisiana","LA");
        STATES_TO_CODES.put("Maine","ME");
        STATES_TO_CODES.put("Manitoba","MB");
        STATES_TO_CODES.put("Maryland","MD");
        STATES_TO_CODES.put("Massachusetts","MA");
        STATES_TO_CODES.put("Michigan","MI");
        STATES_TO_CODES.put("Minnesota","MN");
        STATES_TO_CODES.put("Mississippi","MS");
        STATES_TO_CODES.put("Missouri","MO");
        STATES_TO_CODES.put("Montana","MT");
        STATES_TO_CODES.put("Nebraska","NE");
        STATES_TO_CODES.put("Nevada","NV");
        STATES_TO_CODES.put("New Brunswick","NB");
        STATES_TO_CODES.put("New Hampshire","NH");
        STATES_TO_CODES.put("New Jersey","NJ");
        STATES_TO_CODES.put("New Mexico","NM");
        STATES_TO_CODES.put("New York","NY");
        STATES_TO_CODES.put("Newfoundland","NF");
        STATES_TO_CODES.put("North Carolina","NC");
        STATES_TO_CODES.put("North Dakota","ND");
        STATES_TO_CODES.put("Northwest Territories","NT");
        STATES_TO_CODES.put("Nova Scotia","NS");
        STATES_TO_CODES.put("Nunavut","NU");
        STATES_TO_CODES.put("Ohio","OH");
        STATES_TO_CODES.put("Oklahoma","OK");
        STATES_TO_CODES.put("Ontario","ON");
        STATES_TO_CODES.put("Oregon","OR");
        STATES_TO_CODES.put("Pennsylvania","PA");
        STATES_TO_CODES.put("Prince Edward Island","PE");
        STATES_TO_CODES.put("Puerto Rico","PR");
        STATES_TO_CODES.put("Quebec","QC");
        STATES_TO_CODES.put("Rhode Island","RI");
        STATES_TO_CODES.put("Saskatchewan","SK");
        STATES_TO_CODES.put("South Carolina","SC");
        STATES_TO_CODES.put("South Dakota","SD");
        STATES_TO_CODES.put("Tennessee","TN");
        STATES_TO_CODES.put("Texas","TX");
        STATES_TO_CODES.put("Utah","UT");
        STATES_TO_CODES.put("Vermont","VT");
        STATES_TO_CODES.put("Virgin Islands","VI");
        STATES_TO_CODES.put("Virginia","VA");
        STATES_TO_CODES.put("Washington","WA");
        STATES_TO_CODES.put("West Virginia","WV");
        STATES_TO_CODES.put("Wisconsin","WI");
        STATES_TO_CODES.put("Wyoming","WY");
        STATES_TO_CODES.put("Yukon Territory","YT");

        CODES_TO_STATES.put("AL", "Alabama");
        CODES_TO_STATES.put("AK", "Alaska");
        CODES_TO_STATES.put("AB", "Alberta");
        CODES_TO_STATES.put("AZ", "Arizona");
        CODES_TO_STATES.put("AR", "Arkansas");
        CODES_TO_STATES.put("BC", "British Columbia");
        CODES_TO_STATES.put("CA", "California");
        CODES_TO_STATES.put("CO", "Colorado");
        CODES_TO_STATES.put("CT", "Connecticut");
        CODES_TO_STATES.put("DE", "Delaware");
        CODES_TO_STATES.put("DC", "District Of Columbia");
        CODES_TO_STATES.put("FL", "Florida");
        CODES_TO_STATES.put("GA", "Georgia");
        CODES_TO_STATES.put("GU", "Guam");
        CODES_TO_STATES.put("HI", "Hawaii");
        CODES_TO_STATES.put("ID", "Idaho");
        CODES_TO_STATES.put("IL", "Illinois");
        CODES_TO_STATES.put("IN", "Indiana");
        CODES_TO_STATES.put("IA", "Iowa");
        CODES_TO_STATES.put("KS", "Kansas");
        CODES_TO_STATES.put("KY", "Kentucky");
        CODES_TO_STATES.put("LA", "Louisiana");
        CODES_TO_STATES.put("ME", "Maine");
        CODES_TO_STATES.put("MB", "Manitoba");
        CODES_TO_STATES.put("MD", "Maryland");
        CODES_TO_STATES.put("MA", "Massachusetts");
        CODES_TO_STATES.put("MI", "Michigan");
        CODES_TO_STATES.put("MN", "Minnesota");
        CODES_TO_STATES.put("MS", "Mississippi");
        CODES_TO_STATES.put("MO", "Missouri");
        CODES_TO_STATES.put("MT", "Montana");
        CODES_TO_STATES.put("NE", "Nebraska");
        CODES_TO_STATES.put("NV", "Nevada");
        CODES_TO_STATES.put("NB", "New Brunswick");
        CODES_TO_STATES.put("NH", "New Hampshire");
        CODES_TO_STATES.put("NJ", "New Jersey");
        CODES_TO_STATES.put("NM", "New Mexico");
        CODES_TO_STATES.put("NY", "New York");
        CODES_TO_STATES.put("NF", "Newfoundland");
        CODES_TO_STATES.put("NC", "North Carolina");
        CODES_TO_STATES.put("ND", "North Dakota");
        CODES_TO_STATES.put("NT", "Northwest Territories");
        CODES_TO_STATES.put("NS", "Nova Scotia");
        CODES_TO_STATES.put("NU", "Nunavut");
        CODES_TO_STATES.put("OH", "Ohio");
        CODES_TO_STATES.put("OK", "Oklahoma");
        CODES_TO_STATES.put("ON", "Ontario");
        CODES_TO_STATES.put("OR", "Oregon");
        CODES_TO_STATES.put("PA", "Pennsylvania");
        CODES_TO_STATES.put("PE", "Prince Edward Island");
        CODES_TO_STATES.put("PR", "Puerto Rico");
        CODES_TO_STATES.put("QC", "Quebec");
        CODES_TO_STATES.put("RI", "Rhode Island");
        CODES_TO_STATES.put("SK", "Saskatchewan");
        CODES_TO_STATES.put("SC", "South Carolina");
        CODES_TO_STATES.put("SD", "South Dakota");
        CODES_TO_STATES.put("TN", "Tennessee");
        CODES_TO_STATES.put("TX", "Texas");
        CODES_TO_STATES.put("UT", "Utah");
        CODES_TO_STATES.put("VT", "Vermont");
        CODES_TO_STATES.put("VI", "Virgin Islands");
        CODES_TO_STATES.put("VA", "Virginia");
        CODES_TO_STATES.put("WA", "Washington");
        CODES_TO_STATES.put("WV", "West Virginia");
        CODES_TO_STATES.put("WI", "Wisconsin");
        CODES_TO_STATES.put("WY", "Wyoming");
        CODES_TO_STATES.put("YT", "Yukon Territory");
    }

    public MtxAddress(
            int buildingNumber,
            String streetName,
            String optionalUnitAbbrevAndNumber,
            String cityName,
            String state,
            int zipCode
    ) {
        this(buildingNumber, streetName, optionalUnitAbbrevAndNumber, cityName, state, String.valueOf(zipCode));
    }

    public MtxAddress(
            int buildingNumber,
            String streetName,
            String optionalUnitAbbrevAndNumber,
            String cityName,
            String state,
            String zipCode
    ) {
        this.buildingNumber = buildingNumber;
        this.streetName = streetName;
        this.optionalUnitAbbrevAndNumber = optionalUnitAbbrevAndNumber;
        this.cityName = cityName;
        this.state = this.validateAndFormatState(state);
        this.zipCode = zipCode;

        Objects.requireNonNull(streetName);
        Objects.requireNonNull(cityName);
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

    private String validateAndFormatState(String state) {
        Objects.requireNonNull(state);

        String stateStr;
        if (STATES_TO_CODES.containsKey(state)) {
            stateStr = STATES_TO_CODES.get(state);
        } else if (CODES_TO_STATES.containsKey(state)) {
            stateStr = state;
        } else {
            throw new IllegalArgumentException(state);
        }

        return stateStr;
    }
}