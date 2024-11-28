package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Date;
import java.util.Objects;

@Embeddable
public class InterviewResult  implements ValueObject {

    private Integer interviewGrade;
    private String justification;

    public InterviewResult(Integer interviewGrade, String justification){
        Preconditions.noneNull(interviewGrade, justification);
        Preconditions.nonEmpty(justification);

        this.interviewGrade = interviewGrade;
        this.justification = justification;
    }
    protected InterviewResult(Integer grade) {
        Preconditions.noneNull(grade);

        this.interviewGrade = grade;
        this.justification = "";
    }

    protected InterviewResult(){
        //for ORM
    }

    public Integer interviewGrade(){
        return interviewGrade;
    }

    public String justification(){
        return justification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewResult that = (InterviewResult) o;
        return  Objects.equals(interviewGrade, that.interviewGrade) && Objects.equals(justification, that.justification);
    }

    @Override
    public int hashCode() {
        return Objects.hash( interviewGrade, justification);
    }
    public static InterviewResult valueOf(final Integer grade, final String justification) {
        return new InterviewResult(grade, justification);
    }
    public static InterviewResult valueOf(final Integer grade) {
        return new InterviewResult(grade);
    }

    public int compareTo(InterviewResult result1) {
        return this.interviewGrade.compareTo(result1.interviewGrade);
    }
}
