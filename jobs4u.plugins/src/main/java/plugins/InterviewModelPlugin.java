package plugins;

import com.ibm.icu.impl.Pair;

public interface InterviewModelPlugin {

    boolean generateTextFile(String filePath);

    Pair<Integer, String> evaluateInterviewModelFile(String filePath);
}
