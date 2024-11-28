//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package jobs4u.base.usermanagement.domain;

import eapli.framework.strings.RandomString;
import eapli.framework.strings.util.Strings;

public final class RandomPassword {
    private static final int DEFAULT_LENGTH = 12;
    private static final String LOWER_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CHARS = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL = "_-+*/@=><#?!.%():;[]|{}&$€";
    private final String rawPassword;

    public RandomPassword() {
        this(DEFAULT_LENGTH);
    }

    public RandomPassword(final int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Lenght must be at least 8");
        } else {
            String part1 = RandomString.of(length - 4, LOWER_CHARS).toString();
            String part2 = RandomString.of(2, UPPER_CHARS).toString();
            String part3 = RandomString.of(1, NUMBERS).toString();
            String part4 = RandomString.of(1, SPECIAL).toString();
            this.rawPassword = Strings.shuffle(part1 + part2 + part3 + part4);
        }
    }

    public String toString() {
        return this.rawPassword;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RandomPassword)) {
            return false;
        } else {
            RandomPassword other = (RandomPassword) o;
            Object this$rawPassword = this.rawPassword;
            Object other$rawPassword = other.rawPassword;
            if (this$rawPassword == null) {
                if (other$rawPassword != null) {
                    return false;
                }
            } else if (!this$rawPassword.equals(other$rawPassword)) {
                return false;
            }

            return true;
        }
    }

    public int hashCode() {
        int result = 1;
        Object $rawPassword = this.rawPassword;
        result = result * 59 + ($rawPassword == null ? 43 : $rawPassword.hashCode());
        return result;
    }

}
