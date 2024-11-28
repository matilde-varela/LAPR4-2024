package jobs4u.base.jobopeningmanagement.domain;

import eapli.framework.actions.Actions;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Address implements ValueObject {
    private String streetName;
    private String city;
    private String district;
    private String streetNumber;
    private String zipcode;

    public Address(String streetName, String city, String district, String streetNumber, String zipcode) {
        Preconditions.noneNull(streetName, city, district, streetNumber, zipcode);
        Preconditions.nonEmpty(streetName);
        Preconditions.nonEmpty(city);
        Preconditions.nonEmpty(district);
        Preconditions.nonEmpty(streetNumber);
        Preconditions.nonEmpty(zipcode);
        validateZipcode(zipcode);

        this.streetName = streetName;
        this.city = city;
        this.district = district;
        this.streetNumber = streetNumber;
        this.zipcode = zipcode;
    }

    protected Address() {
        //for ORM
    }

    public Address(String address) {
        String[] parts = address.split(",\\s*");
        new Address(parts[0], parts[2], parts[3], parts[1], parts[4]);
    }

    public static Address valueOf(String address) {
        return new Address(address);
    }


    private void validateZipcode(String zipcode) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{3}");
        Matcher matcher = pattern.matcher(zipcode);
        Actions.doIf(Actions.THROW_ARGUMENT, !matcher.matches());
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", streetName, streetNumber, city, district, zipcode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(streetName, address.streetName) && Objects.equals(city, address.city) && Objects.equals(district, address.district) && Objects.equals(streetNumber, address.streetNumber) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, city, district, streetNumber, zipcode);
    }
}
