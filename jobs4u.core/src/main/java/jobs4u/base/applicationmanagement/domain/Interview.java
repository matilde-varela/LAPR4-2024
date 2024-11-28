package jobs4u.base.applicationmanagement.domain;

import com.ibm.icu.impl.Pair;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Date;
import java.util.Objects;

@Embeddable
public class Interview implements ValueObject {

    private String interviewTypeDenomination;
    private Date schedule;
    private InterviewResult interviewResult;
    private InterviewAnswer interviewAnswer;


    public Interview(String interviewTypeDenomination, Date schedule, InterviewResult interviewResult, InterviewAnswer interviewAnswer) {
        Preconditions.noneNull(interviewAnswer, schedule, interviewResult, interviewAnswer);
        Preconditions.nonEmpty(interviewTypeDenomination);
//        Preconditions.nonEmpty(interviewAnswer);

        this.interviewTypeDenomination = interviewTypeDenomination;
        this.schedule = schedule;
        this.interviewResult = interviewResult;
        this.interviewAnswer = interviewAnswer;
    }

    public Interview(String interviewTypeDenomination, Date schedule) {
        Preconditions.noneNull(schedule);
        Preconditions.nonEmpty(interviewTypeDenomination);

        this.interviewTypeDenomination = interviewTypeDenomination;
        this.schedule = schedule;
    }

    protected Interview(){
        //for ORM
    }

    public InterviewResult interviewResult(){
        return interviewResult;
    }

    public InterviewAnswer interviewAnswer(){return interviewAnswer;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interview that = (Interview) o;
        return Objects.equals(interviewTypeDenomination, that.interviewTypeDenomination) && Objects.equals(schedule, that.schedule) && Objects.equals(interviewResult, that.interviewResult) && Objects.equals(interviewAnswer, that.interviewAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interviewTypeDenomination, schedule, interviewResult, interviewAnswer);
    }
    public void updateSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public void updateInterviewAnswer(String filepath){
        Preconditions.noneNull(filepath);
        this.interviewAnswer = InterviewAnswer.valueOf(filepath);
    }
    public void updateInterviewResult(Pair<Integer, String> result){
        if (!result.second.isEmpty()) {
            this.interviewResult = InterviewResult.valueOf(result.first, result.second);
        } else {
            this.interviewResult = InterviewResult.valueOf(result.first);
        }
    }
}
