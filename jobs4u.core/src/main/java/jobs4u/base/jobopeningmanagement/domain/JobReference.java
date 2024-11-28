package jobs4u.base.jobopeningmanagement.domain;


import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class JobReference implements Comparable<JobReference>, ValueObject {

    @AttributeOverride(name = "value", column = @Column(name = "Company"))
    private String companyCode;
    @Column(nullable = false)
    private Integer sequentialCode;

    protected JobReference() {
        // FOR ORM
    }

    public JobReference(String custumerCode, Integer sequentialCode) {
        Preconditions.noneNull(custumerCode, sequentialCode);
        Preconditions.noneNull(custumerCode);
        Preconditions.nonEmpty(custumerCode);
        Preconditions.nonNegative(sequentialCode);
        this.companyCode = custumerCode;
        this.sequentialCode = sequentialCode;
    }

    public JobReference(String jobReference) {
        Preconditions.noneNull(jobReference);
        Preconditions.nonEmpty(jobReference);
        this.companyCode = jobReference.split("-")[0];
        this.sequentialCode = Integer.valueOf(jobReference.split("-")[1]);
    }

    @Override
    public int compareTo(JobReference other) {
        int customerCodeComparison = CharSequence.compare(this.companyCode, other.companyCode);
        if (customerCodeComparison != 0) {
            return customerCodeComparison;
        }
        return Integer.compare(this.sequentialCode, other.sequentialCode);
    }

    @Override
    public String toString() {
        return String.format("%s-%d", companyCode, sequentialCode);
    }

    public String getcustomerCode() {
        return companyCode;
    }

    public Integer getSequentialCode() {
        return sequentialCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobReference)) return false;
        JobReference that = (JobReference) o;
        return Objects.equals(companyCode, that.companyCode) && Objects.equals(getSequentialCode(), that.getSequentialCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCode, getSequentialCode());
    }

    public String getCompanyCode() {
        return companyCode;
    }
}
