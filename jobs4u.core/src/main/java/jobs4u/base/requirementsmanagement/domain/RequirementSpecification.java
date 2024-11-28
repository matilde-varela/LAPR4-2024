package jobs4u.base.requirementsmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;

import jobs4u.base.pluginmanagement.domain.ConfigFileName;
import jobs4u.base.pluginmanagement.domain.FullClassName;
import jobs4u.base.requirementsmanagement.dto.RequirementSpecificationDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Entity
@Table(name = "T_REQUIREMENTSPECIFICATION")
public class RequirementSpecification implements DTOable<RequirementSpecificationDTO>, AggregateRoot<RequirementName> {

    private static final Logger LOGGER = LogManager.getLogger(RequirementSpecification.class);

    @EmbeddedId
    private RequirementName requirementName;

    private RequirementDescription description;
    private FullClassName reqSpec;
    private ConfigFileName configFile;

    public RequirementSpecification(String requirementName, String description, String reqSpec, String configFile, String dataImporter) {
        Preconditions.noneNull(requirementName, description, reqSpec);
        Preconditions.nonEmpty(requirementName);
        Preconditions.nonEmpty(description);
        Preconditions.nonEmpty(reqSpec);
        Preconditions.nonEmpty(dataImporter);
        this.requirementName = new RequirementName(requirementName);
        this.description = new RequirementDescription(description);
        this.reqSpec = FullClassName.valueOf(reqSpec,dataImporter);
        this.configFile = ConfigFileName.valueOf(configFile);
    }

    protected RequirementSpecification() {
        //for ORM
    }

    @Override
    public RequirementSpecificationDTO toDTO() {
        return new RequirementSpecificationDTO(requirementName.name(), description.description());
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public RequirementName identity() {
        return this.requirementName;
    }

    public RequirementName requirementName() {
        return identity();
    }

    public RequirementDescription requirementDescription() {
        return description;
    }

    public String className() {
        return this.reqSpec.mainClass();
    }

    public String dataImporter() {
        return this.reqSpec.dataImporter();
    }

    public ConfigFileName configurationFile() {
        return this.configFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequirementSpecification)) return false;
        RequirementSpecification that = (RequirementSpecification) o;
        return Objects.equals(requirementName, that.requirementName) && Objects.equals(description, that.description) && Objects.equals(reqSpec, that.reqSpec) && Objects.equals(configFile, that.configFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirementName, description, reqSpec, configFile);
    }
}
