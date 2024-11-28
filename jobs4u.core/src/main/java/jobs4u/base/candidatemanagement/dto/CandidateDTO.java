package jobs4u.base.candidatemanagement.dto;

public class CandidateDTO {

    private final String candidateName;
    private final String candidateEmail;

    private final String candidatePhoneNumber;

    public CandidateDTO(String candidateName, String candidateEmail, String candidatePhoneNumber) {
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.candidatePhoneNumber=candidatePhoneNumber;
    }

    public String getCandidateName(){
        return this.candidateName;
    }

    public String getCandidateEmail(){
        return this.candidateEmail;
    }
    public String getCandidatePhoneNumber(){return this.candidatePhoneNumber;}

    @Override
    public String toString() {
        return String.format("%s | %s | %s", candidateName, candidateEmail,candidatePhoneNumber);
    }
}
