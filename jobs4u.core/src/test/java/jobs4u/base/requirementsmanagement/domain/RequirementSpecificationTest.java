package jobs4u.base.requirementsmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class RequirementSpecificationTest {

    String name = "Prime Minister";
    String description = "Prime Ministers with good experience in running a country.";
    String fullClassName = "jobs4u.plugin.core.adapter.RequirementPluginAdapter";
    String dataImporter = "jobs4u.plugin.core.adapter.FileManagementAdapter";

    @Test
    void ensureSameAsWorksAsExpected() {
        String description1 = "Back-End Developer With Experience in Java";
        String name1 = "Back-End Developer";
        String fullClassName1 = "jobs4u.plugin.core.adapter.RequirementPluginAdapter1";
        RequirementSpecification requirementSpecification1 = new RequirementSpecification(name1, description1, fullClassName1, "plugins-config-file/requirement/r-config-1.txt",dataImporter);

        String description2 = "Front-End Developer With Experience in HTML";
        String name2 = "Front-End Developer";
        String fullClassName2 = "jobs4u.plugin.core.adapter.RequirementPluginAdapter2";
        RequirementSpecification requirementSpecification2 = new RequirementSpecification(name2, description2, fullClassName2, "plugins-config-file/requirement/r-config-1.txt",dataImporter);

        RequirementSpecification requirementSpecification3 = new RequirementSpecification(name2, description2, fullClassName2, "plugins-config-file/requirement/r-config-1.txt",dataImporter);

        // Assert that the sameAs() method checks that two different interview models are different
        assertFalse(requirementSpecification1.sameAs(requirementSpecification2));

        // Assert that the sameAs() method checks that the same interview model is checked as being the same
        assertTrue(requirementSpecification1.sameAs(requirementSpecification1));

        // Assert that the sameAs() method checks that two different interview objects with the same value objects are considered the same
        assertTrue(requirementSpecification2.sameAs(requirementSpecification3));
    }

    @Test
    void ensureItHasAnRequirementName() {
        assertThrows(IllegalArgumentException.class, () -> new RequirementSpecification(null, description, fullClassName, "plugins-config-file/requirement/r-config-1.txt",dataImporter));
    }

    @Test
    void ensureItHasAnRequirementDescription() {
        assertThrows(IllegalArgumentException.class, () -> new RequirementSpecification(name, null, fullClassName, "plugins-config-file/requirement/r-config-1.txt",dataImporter));
    }

    @Test
    void ensureItHasAClassName() {
        assertThrows(IllegalArgumentException.class, () -> new RequirementSpecification(name, description, null, "plugins-config-file/requirement/r-config-1.txt",dataImporter));
    }
}