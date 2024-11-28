package jobs4u.base.interviewmodelmanagement.dto;

public class InterviewModelDTO {

    private final String name;
    private final String description;
    private String plugin;

    public InterviewModelDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public InterviewModelDTO(String name, String description, String plugin) {
        this.name = name;
        this.description = description;
        this.plugin = plugin;
    }

    public String name() {
        return this.name;
    }
    public String description(){return this.description;}
    public String plugin(){ return this.plugin;}



}
