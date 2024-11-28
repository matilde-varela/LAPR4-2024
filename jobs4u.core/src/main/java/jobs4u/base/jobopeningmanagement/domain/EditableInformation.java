package jobs4u.base.jobopeningmanagement.domain;

import eapli.framework.validations.Preconditions;

import java.util.*;

public class EditableInformation {

    public static final EditableInformation ADDRESS = new EditableInformation("Address");
    public static final EditableInformation NUM_VACANCIES = new EditableInformation("Number Of Vacancies");
    public static final EditableInformation FUNCTION = new EditableInformation("Function");
    public static final EditableInformation DESCRIPTION = new EditableInformation("Description");
    public static final EditableInformation CONTRACT_TYPE = new EditableInformation("Contract Type");
    public static final EditableInformation WORK_MODE = new EditableInformation("Work Mode");
    public static final EditableInformation REQ_SPECI = new EditableInformation("Requirement Specification");
    public static final EditableInformation INT_MODEL = new EditableInformation("Interview Model");

    private final String name;

    public EditableInformation(String name) {
        Preconditions.nonEmpty(name);
        this.name = name;
    }

    private static final EditableInformation[] VALUES = {
            ADDRESS, NUM_VACANCIES, FUNCTION, DESCRIPTION, CONTRACT_TYPE, WORK_MODE
    };

    public static boolean isEditable(String text) {
        for (EditableInformation info : VALUES) {
            if (info.toString().equalsIgnoreCase(text)) {
                return true;
            }
        }
        throw new IllegalArgumentException("Not possible to edit " + text);
    }

    public static List<EditableInformation> notStartedEditableInformation() {
        return Arrays.asList(ADDRESS, NUM_VACANCIES, FUNCTION, DESCRIPTION, CONTRACT_TYPE, WORK_MODE, REQ_SPECI, INT_MODEL);
    }

    public static List<EditableInformation> startedEditableInformation() {
        return Arrays.asList(REQ_SPECI, INT_MODEL);
    }

    public static List<EditableInformation> screeningEditableInformation() {
        List<EditableInformation> e = new ArrayList<>();
        e.add(INT_MODEL);
        return e;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditableInformation)) return false;
        EditableInformation that = (EditableInformation) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
