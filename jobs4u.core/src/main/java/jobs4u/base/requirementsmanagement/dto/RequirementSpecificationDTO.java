package jobs4u.base.requirementsmanagement.dto;

import lombok.Getter;

public class RequirementSpecificationDTO {

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlugin() {
        return plugin;
    }

    private final String name;
    private final String description;
    private String plugin;

    public RequirementSpecificationDTO(String requirementName, String description) {
        this.name = requirementName;
        this.description = description;
    }

    public String filename() {
        return this.name;
    }

    @Override
    public String toString() {
        if (plugin == null) {
            return String.format("Name: %s | Description: %s", name, description);
        }
        return String.format("Name: %s | Plugin : %s | Description: %s", name, plugin, description);
    }
}
