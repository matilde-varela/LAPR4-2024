package jobs4u.base.jobopeningmanagement.domain;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class AddressTest {

    String streetName = "StreetName";
    String city = "city";
    String district = "District";
    String streetNumber = "14th";
    String zipcode = "1234-234";

    @Test
    public void ensureZipcodeIsFiveDigits() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, district, streetNumber, "123"));
    }

    @Test
    public void ensureZipcodeIsFiveDigitsExact() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, district, streetNumber, "123456"));
    }

    @Test
    public void ensureZipcodeIsNumeric() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, district, streetNumber, "asdfg"));
    }

    @Test
    public void ensureStreetNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address(null, city, district, streetNumber, zipcode));
    }

    @Test
    public void ensureCityCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, null, district, streetNumber, zipcode));
    }

    @Test
    public void ensureDistrictCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, null, streetNumber, zipcode));
    }

    @Test
    public void ensureStreetNumberCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, district, null, zipcode));
    }

    @Test
    public void ensureZipcodeCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, district, streetNumber, null));
    }

    @Test
    public void ensureAddressCannotHaveEmptyStreetName() {
        assertThrows(IllegalArgumentException.class, () -> new Address("", city, district, streetNumber, zipcode));
    }

    @Test
    public void ensureAddressCannotHaveEmptyCity() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, "", district, streetNumber, zipcode));
    }

    @Test
    public void ensureAddressCannotHaveEmptyDistrict() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, "", streetNumber, zipcode));
    }

    @Test
    public void ensureAddressCannotHaveEmptyStreetNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, district, "", zipcode));
    }

    @Test
    public void ensureAddressCannotHaveEmptyZipcode() {
        assertThrows(IllegalArgumentException.class, () -> new Address(streetName, city, district, streetNumber, ""));
    }

}