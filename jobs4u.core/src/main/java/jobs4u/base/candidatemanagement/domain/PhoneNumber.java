package jobs4u.base.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;


@Embeddable
public class PhoneNumber implements ValueObject, Comparable<PhoneNumber> {
    private String extension;
    private String number;

    public PhoneNumber(String extension, String number) {
        if (!isValidPhoneNumber(extension, number)) {
            throw new IllegalArgumentException(
                    "The inserted phone number is not in the right format.");
        } else if (extension == null || number == null) {
            throw new NullPointerException(
                    "The extension or the phone number can't be null.");
        }

        this.extension = extension;
        this.number = number;
    }

    protected PhoneNumber() {

    }

    public String extension() {
        return this.extension;
    }

    public String number() {
        return this.number;
    }

    public static PhoneNumber valueOf(final String extension, final String number) {
        return new PhoneNumber(extension, number);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneNumber)) {
            return false;
        }

        final PhoneNumber that = (PhoneNumber) o;
        return this.number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return this.number.hashCode();
    }

    @Override
    public String toString() {
        return extension + " " + number;
    }

    @Override
    public int compareTo(PhoneNumber o) {
        return number.compareTo(o.number);
    }

    public static boolean isValidPhoneNumber(String extension, String number) {

        boolean extensionContaisPlus = extension.contains("+");

        return extensionContaisPlus && (number.length() >= 8 && number.length() <= 15);
    }
}
