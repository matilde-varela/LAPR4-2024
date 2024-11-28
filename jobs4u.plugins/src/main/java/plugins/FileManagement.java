package plugins;

public interface FileManagement {

    Boolean checkFileFormat(String fileName);

    void importData(String filepath);

}
