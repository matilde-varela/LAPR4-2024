package jobs4u.base.workmodemanagement.dto;

import eapli.framework.validations.Preconditions;

import java.util.Objects;

public class WorkModeDTO {

    private final String workModeName;

    public WorkModeDTO(String denomination) {
        Preconditions.nonEmpty(denomination);
        Preconditions.noneNull(denomination);
        this.workModeName = denomination;
    }

    public String workModeName(){
        return workModeName;
    }

    @Override
    public String toString() {
        return workModeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkModeDTO)) return false;
        WorkModeDTO that = (WorkModeDTO) o;
        return Objects.equals(workModeName, that.workModeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workModeName);
    }
}
