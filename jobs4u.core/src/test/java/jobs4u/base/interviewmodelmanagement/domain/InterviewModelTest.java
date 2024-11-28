package jobs4u.base.interviewmodelmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class InterviewModelTest {

    String name = "Prime Minister";
    String description = "Prime Ministers with experience in running a country.";
    String fullClassName = "jobs4u.plugin.core.adapter.InterviewPluginAdapter";
    String dataImporter = "jobs4u.plugin.core.adapter.FileManagementAdapter";
    @Test
    void ensureSameAsWorksAsExpected() {
        String description1 = "Back-End Developer With Experience in Java";
        String name1 = "Back-End Developer";
        String fullClassName1 = "jobs4u.plugin.core.adapter.InterviewPluginAdapter1";
        InterviewModel interviewModel1 = new InterviewModel(name1, description1, fullClassName1,"plugins-config-file/requirement/r-config-1.txt",dataImporter);

        String description2 = "Front-End Developer With Experience in HTML";
        String name2 = "Front-End Developer";
        String fullClassName2 = "jobs4u.plugin.core.adapter.InterviewPluginAdapter2";
        InterviewModel interviewModel2 = new InterviewModel(name2, description2, fullClassName2,"plugins-config-file/requirement/r-config-1.txt",dataImporter);

        InterviewModel interviewModel3 = new InterviewModel(name2, description2, fullClassName2,"plugins-config-file/requirement/r-config-1.txt",dataImporter);

        // Assert that the sameAs() method checks that two different interview models are different
        assertFalse(interviewModel1.sameAs(interviewModel2));

        // Assert that the sameAs() method checks that the same interview model is checked as being the same
        assertTrue(interviewModel1.sameAs(interviewModel1));

        // Assert that the sameAs() method checks that two different interview objects with the same value objects are considered the same
        assertTrue(interviewModel2.sameAs(interviewModel3));
    }

    @Test
    void ensureItHasAnInterviewModelName() {
        assertThrows(IllegalArgumentException.class, () -> new InterviewModel(null, description, fullClassName,"plugins-config-file/requirement/r-config-1.txt",dataImporter));
    }

    @Test
    void ensureItHasAnInterviewModelDescription() {
        assertThrows(IllegalArgumentException.class, () -> new InterviewModel(name, null, fullClassName,"plugins-config-file/requirement/r-config-1.txt",dataImporter));
    }

    @Test
    void ensureItHasAPluginJarFile() {
        assertThrows(IllegalArgumentException.class, () -> new InterviewModel(name, description, null,"plugins-config-file/requirement/r-config-1.txt",dataImporter));
    }

}