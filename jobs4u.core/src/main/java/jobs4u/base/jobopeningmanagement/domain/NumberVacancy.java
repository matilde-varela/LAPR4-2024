package jobs4u.base.jobopeningmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class NumberVacancy implements ValueObject {

    private int numVacancies;

    public NumberVacancy(Integer num) {
        Preconditions.noneNull(num);
        Preconditions.isPositive(num);
        this.numVacancies = num;
    }

    protected NumberVacancy() {
        //for ORM
    }

    public static NumberVacancy valueOf(final Integer num) {
        return new NumberVacancy(num);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberVacancy)) return false;
        NumberVacancy that = (NumberVacancy) o;
        return numVacancies == that.numVacancies;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numVacancies);
    }

    @Override
    public String toString() {
        return String.valueOf(numVacancies);
    }

    public int getNumVacancies() {
        return numVacancies;
    }
}
