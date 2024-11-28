package jobs4u.base.pluginmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public class ConfigFileName implements ValueObject, Comparable<ConfigFileName> {

    private static final long serialVersionUID = 1L;

    private final String fileName;

    protected ConfigFileName(final String name) {
        Preconditions.nonEmpty(name);
        Preconditions.matches(Pattern.compile("([a-zA-Z]:)?(\\\\\\\\[a-zA-Z0-9_.-]+)*(\\\\\\\\[a-zA-Z0-9_.-]+)?"), name, "The provided filepath is not correct.");
        this.fileName = name;
    }

    protected ConfigFileName() {
        fileName = null;
    }

    public static ConfigFileName valueOf(final String filepath) {
        return new ConfigFileName(filepath);
    }

    @Override
    public String toString() {
        return fileName;
    }

    @Override
    public int compareTo(final ConfigFileName o) {
        return fileName.compareTo(o.fileName);
    }

}
