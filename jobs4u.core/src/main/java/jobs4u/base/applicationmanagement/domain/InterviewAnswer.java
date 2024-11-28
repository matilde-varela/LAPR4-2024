package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class InterviewAnswer implements ValueObject {

    private File interviewAnswerFile;
    private String filenameInterview;
    private String filepathInterview;

    protected InterviewAnswer(String interviewAnswerFile){
        Preconditions.noneNull(interviewAnswerFile);
        Preconditions.nonEmpty(interviewAnswerFile);
        Preconditions.matches(
                Pattern.compile("([a-zA-Z]:)?(\\\\\\\\[a-zA-Z0-9_.-]+)*(\\\\\\\\[a-zA-Z0-9_.-]+)?"),
                interviewAnswerFile,
                "The provided filepath is not correct."
        );

        Path uploadedPath = Paths.get(interviewAnswerFile);

        if (!Files.isRegularFile(uploadedPath)) {
            throw new IllegalArgumentException("The provided path is not a file.");
        }

        File uploaded = new File(interviewAnswerFile);
        Path targetDirectory = Paths.get("../plugins-config-file/interview/");
        Path targetPath = targetDirectory.resolve(uploaded.getName());

        try {
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            Files.copy(uploaded.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            this.interviewAnswerFile = targetPath.toFile();
            this.filenameInterview = targetPath.getFileName().toString();
            this.filepathInterview = targetPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy the file to the interviewAnswers directory.", e);
        }
    }

    protected InterviewAnswer(){
    }

    public File interviewAnswerFile(){
        return interviewAnswerFile;
    }

    public String name(){
        return filenameInterview;
    }

    public String filepath(){
        return this.filepathInterview;
    }

    public static InterviewAnswer valueOf(final String answer){
        return new InterviewAnswer(answer);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof  InterviewAnswer)) return false;
        InterviewAnswer that = (InterviewAnswer) o;
        return Objects.equals(interviewAnswerFile, that.interviewAnswerFile);
    }

    @Override
    public int hashCode(){
        return Objects.hash(interviewAnswerFile);
    }
}
