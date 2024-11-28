package jobs4u.base.jobopeningmanagement.dto;

import eapli.framework.validations.Preconditions;

import java.io.Serializable;
import java.util.Objects;

public class JobOpeningDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String function;
    private final String description;
    private final String status;
    private final String contractType;
    private final String workMode;
    private final Integer numVacancies;
    private final String requirementName;
    private final String interviewModelName;
    private final String customerCode;
    private final String jobReference;
    private final String address;
    private final Integer numberApplicants;
    private final String activeSinceDate;


    public JobOpeningDTO(String address, String function, String description, String status, String contractType,
                         String workMode, Integer numVacancies, String requirementName,
                         String jobReference, String customerCode, String interviewModelName,
                         Integer numberApplicants, String activeSinceDate) {

        Preconditions.noneNull(address, function, description, status, contractType, workMode,
                numVacancies, requirementName, jobReference, interviewModelName);

        this.function = function;
        this.description = description;
        this.status = status;
        this.contractType = contractType;
        this.workMode = workMode;
        this.numVacancies = numVacancies;
        this.requirementName = requirementName;
        this.customerCode = customerCode;
        this.jobReference = jobReference;
        this.address = address;
        this.interviewModelName = interviewModelName;
        this.numberApplicants = numberApplicants;
        this.activeSinceDate = activeSinceDate;
    }


    public String getFunction() {
        return function;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getContractType() {
        return contractType;
    }

    public String getWorkMode() {
        return workMode;
    }

    public Integer getNumVacancies() {
        return numVacancies;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getJobReference() {
        return jobReference;
    }

    public String getAddress() {
        return address;
    }

    public String getInterviewModelName() {
        return interviewModelName;
    }

    public Integer getNumberApplicants() {
        return numberApplicants;
    }

    public String getActiveSinceDate() {
        return activeSinceDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobOpeningDTO)) return false;
        JobOpeningDTO that = (JobOpeningDTO) o;
        return Objects.equals(getFunction(), that.getFunction()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getContractType(), that.getContractType()) && Objects.equals(getWorkMode(), that.getWorkMode()) && Objects.equals(getNumVacancies(), that.getNumVacancies()) && Objects.equals(getRequirementName(), that.getRequirementName()) && Objects.equals(getCustomerCode(), that.getCustomerCode()) && Objects.equals(getJobReference(), that.getJobReference()) && Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFunction(), getDescription(), getStatus(), getContractType(), getWorkMode(), getNumVacancies(), getRequirementName(), getCustomerCode(), getJobReference(), getAddress());
    }

    @Override
    public String toString() {
        return String.format("»» Job Reference: %s\n" +
                        " » Function: %s\n" +
                        " » Contract Type: %s\n" +
                        " » Work Mode: %s\n" +
                        " » Address: %s\n" +
                        " » Description: %s\n" +
                        " » Number of Vacancies: %d\n" +
                        " » Company: %s\n",
                jobReference, function, contractType, workMode, address,
                description, numVacancies, customerCode);
    }


}
