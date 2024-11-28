package jobs4u.base.recruitmentprocessmanagement.dto;

import java.util.List;
import java.util.Objects;

public class AllPhasesDTO {

    private List<PhaseDTO> listOfPhases;

    public AllPhasesDTO(List<PhaseDTO> listOfPhases){
        this.listOfPhases = listOfPhases;
    }

    public List<PhaseDTO> getListOfPhases() {
        return listOfPhases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllPhasesDTO that = (AllPhasesDTO) o;
        return Objects.equals(listOfPhases, that.listOfPhases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfPhases);
    }
}
