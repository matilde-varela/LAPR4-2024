package jobs4u.base.pluginmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import eapli.framework.strings.StringMixin;

import java.util.regex.Pattern;

@Embeddable
public class FullClassName implements ValueObject, Comparable<FullClassName>, StringMixin {

    private static final long serialVersionUID = 1L;

    private final String mainClass;
    private final String dataImporter;

    protected FullClassName(final String name, final String data) {
        Preconditions.nonEmpty(name);
        Preconditions.matches(Pattern.compile("([a-z]+\\.)+[A-Z][a-zA-Z0-9]*"), name, "The provided name is not a fully qualified class name.");
        this.mainClass = name;
        this.dataImporter = data;
    }

    protected FullClassName() {
        mainClass = null;
        dataImporter = null;
    }

    public static FullClassName valueOf(final String fqClassName, final String dataImporter) {
        return new FullClassName(fqClassName, dataImporter);
    }

    public String mainClass(){
        return this.mainClass;
    }

    public String dataImporter(){
        return this.dataImporter;
    }

    @Override
    public int compareTo(final FullClassName o) {
        return mainClass.compareTo(o.mainClass);
    }
}
