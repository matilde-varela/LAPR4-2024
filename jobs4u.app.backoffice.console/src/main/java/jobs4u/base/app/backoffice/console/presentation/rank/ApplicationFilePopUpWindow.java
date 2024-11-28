package jobs4u.base.app.backoffice.console.presentation.rank;

import javax.swing.*;
import java.awt.*;

public class ApplicationFilePopUpWindow implements Runnable {

    private final String fileContent;

    public ApplicationFilePopUpWindow(String fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public void run() {
        JTextArea edit = new JTextArea(20, 40);
        if (fileContent.isEmpty()){
            edit.setText("The file is empty");
        }else if (fileContent.equals("!")){
            edit.setText("An error occurred while reading the file");
        }else {
            edit.setText(fileContent);
        }

        edit.setFont(new Font("Serif", Font.PLAIN, 18));

        edit.setLineWrap(true);
        edit.setWrapStyleWord(true);

        JFrame frame = new JFrame("[Jobs4U] Application File Content");
        frame.getContentPane().add(new JScrollPane(edit), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
