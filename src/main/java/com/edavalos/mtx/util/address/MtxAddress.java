package com.edavalos.mtx.util.address;

import java.util.Objects;

import com.edavalos.mtx.util.map.MtxBiMap;
import com.edavalos.mtx.util.map.MtxHashBiMap;
import com.edavalos.mtx.util.string.MtxStringUtil;

public record MtxAddress(
        int buildingNumber,
        String streetName,
        String optionalUnitAbbrevAndNumber,
        String cityName,
        String state,
        String zipCode
    ) {

    // Source: https://www.yourdictionary.com/articles/usps-street-building-abbreviations
    private static final String[] STREET_ABBREVIATIONS = {
            "Alley", "ALY",
            "Avenue", "AVE",
            "Boulevard", "BLV",
            "Causeway", "CSWY",
            "Center", "CTR",
            "Circle", "CIR",
            "Court", "CT",
            "Cove", "CV",
            "Crossing", "XING",
            "Drive", "DR",
            "Expressway", "EXPY",
            "Extension", "EXT",
            "Freeway", "FWY",
            "Grove", "GRV",
            "Highway", "HWY",
            "Hollow", "HOLW",
            "Junction", "JCT",
            "Lane", "LN",
            "Motorway", "MTWY",
            "Overpass", "OPAS",
            "Park", "PARK",
            "Parkway", "PKWY",
            "Place", "PL",
            "Plaza", "PLZ",
            "Point", "PT",
            "Road", "RD",
            "Route", "RTE",
            "Skyway", "SKWY",
            "Square", "SQ",
            "Street", "ST",
            "Terrace", "TERR",
            "Trail", "TRL",
            "Way", "WAY",
            // Lesser used street suffixes and abbreviations:
            "Annex", "ANX",
            "Arcade", "ARC",
            "Bayou", "BYU",
            "Beach", "BCH",
            "Bend", "BND",
            "Bluff", "BLF",
            "Bottom", "BTM",
            "Branch", "BR",
            "Bridge", "BRG",
            "Brook", "BRK",
            "Burg", "BG",
            "Bypass", "BYP",
            "Camp", "CP",
            "Cliff", "CLF",
            "Club", "CLB",
            "Commons", "CMNS",
            "Corner", "CNR",
            "Corners", "CORS",
            "Course", "CRSE",
            "Creek", "CRK",
            "Crescent", "CRES",
            "Dale", "DL",
            "Estate", "EST",
            "Falls", "FLS",
            "Field", "FLD",
            "Flats", "FLTS",
            "Ford", "FRD",
            "Forge", "FRG",
            "Gateway", "GTWY",
            "Glen", "GLN",
            "Haven", "HVN",
            "Heights", "HTS",
            "Ridge", "RDG",
            "Station", "STA"
    };

    // Source: https://www.yourdictionary.com/articles/usps-street-building-abbreviations
    private static final String[] BUILDING_ABBREVIATIONS = {
            "Apartment", "APT",
            "Basement", "BSMT",
            "Building", "BLDG",
            "Department", "DEPT",
            "Floor", "FL",
            "Hanger", "HNGR",
            "Lobby", "LBBY",
            "Lower", "LOWR",
            "Number", "#",
            "Office", "OFC",
            "Penthouse", "PH",
            "Room", "RM",
            "Suite", "STE",
            "Trailer", "TRLR",
            "Unit", "UNIT",
            "Upper", "UPPR"
    };

    private static final MtxBiMap<String, String> STATES_TO_CODES = new MtxHashBiMap<>();
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
        } else if (STATES_TO_CODES.inverse().containsKey(state)) {
            stateStr = state;
        } else {
            throw new IllegalArgumentException(state);
        }

        return stateStr;
    }

    // @TODO: Add parseAddress(): MtxAddress - converts a string into an MtxAddress
    public static MtxAddress parseAddress(String address) {
        //
    }
}