package plugins;

import com.ibm.icu.impl.Pair;

public interface RequirementsSpecificationPlugin {

    boolean generateTextFile(String filePath);

    Pair<Boolean, String> evaluateRequirementSpecificationFile(String filePath);
}
