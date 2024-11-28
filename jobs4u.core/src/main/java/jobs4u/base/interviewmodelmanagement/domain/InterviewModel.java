package jobs4u.base.interviewmodelmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jobs4u.base.interviewmodelmanagement.dto.InterviewModelDTO;
import jobs4u.base.pluginmanagement.domain.ConfigFileName;
import jobs4u.base.pluginmanagement.domain.FullClassName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Entity
@Table(name = "T_INTERVIEWMODEL")
public class InterviewModel implements AggregateRoot<InterviewModelName> {

    private static final Logger LOGGER = LogManager.getLogger(InterviewModel.class);

    @EmbeddedId
    private InterviewModelName interviewModelName;

    private InterviewModelDescription description;

    private FullClassName plugin;
    private ConfigFileName configFile;

    public InterviewModel(String interviewModelName, String description, String fullClassName, String configFile, String dataImporter) {
        Preconditions.noneNull(interviewModelName, description, fullClassName);
        Preconditions.nonEmpty(interviewModelName);
        Preconditions.nonEmpty(description);
        Preconditions.nonEmpty(fullClassName);
        this.interviewModelName = new InterviewModelName(interviewModelName);
        this.description = new InterviewModelDescription(description);
        this.plugin = FullClassName.valueOf(fullClassName,dataImporter);
        this.configFile = ConfigFileName.valueOf(configFile);
    }

    public InterviewModel() {
        //for ORM
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public InterviewModelName identity() {
        return this.interviewModelName;
    }

    public InterviewModelName interviewModelName() {
        return identity();
    }

    public InterviewModelDescription interviewModelDescription() {
        return description;
    }

    public String className() {
        return plugin.mainClass();
    }

    public String dataImporter(){
        return this.plugin.dataImporter();
    }

    public ConfigFileName configurationFile(){
        return this.configFile;
    }

    public String nameString() {
        return this.interviewModelName.name();
    }

    public String descriptionString() {
        return this.interviewModelName.name();
    }

    public InterviewModelDTO toDto() {
        return new InterviewModelDTO(nameString(), descriptionString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterviewModel)) return false;
        InterviewModel that = (InterviewModel) o;
        return Objects.equals(interviewModelName, that.interviewModelName) && Objects.equals(description, that.description) && Objects.equals(plugin, that.plugin) && Objects.equals(configFile, that.configFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interviewModelName, description, plugin, configFile);
    }
}
