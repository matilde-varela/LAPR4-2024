package jobs4u.base.applicationmanagement.domain;

import jobs4u.base.applicationmanagement.application.ApplicationFilesThreadService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileWordCountThread implements Runnable {
    //----------------------------------------------------------
    //---------------------- ATTRIBUTES ------------------------
    private final File file;
    private final List<String> unwantedCharacters = new ArrayList<>();


    //----------------------------------------------------------
    //--------------------- CONSTRUCTOR ------------------------
    public FileWordCountThread(ApplicationFile applicationFile) {
        this.file = applicationFile.getApplicationFile();
    }

    //----------------------------------------------------------
    //------------------------- RUN ----------------------------
    @Override
    public void run() {
        try {
            //------------------------------------------------------
            //---------------- HELPFUL VARIABLES -------------------

            //   FILE        WORD    NUMBER OF TIMES IT APPEARED
            Map<String, Map<String, Integer>> map = new TreeMap<>();

            //  WORD     NUMBER OF TIMES IT APPEARED
            Map<String, Integer> map1 = new HashMap<>();

            //USED TO MANIPULATE THE FILE (READ)
            Scanner Reader = new Scanner(file);

            //ALL UNWANTED CHARACTERS THAT WILL BE TAKEN OUT TO GET ONLY WORDS
            defineUnwantedCharacters();

            //------------------------------------------------------
            //---------------- READING THE FILE --------------------
            while (Reader.hasNextLine()) {
                String data = Reader.nextLine();

                //IF THE LINE IS EMPTY, SKIP IT
                if (data.isBlank()) {
                    continue;
                }

                //TAKE ALL UNWANTED CHARACTERS AND LEAVE ONLY THE ACTUAL WORDS
                for (String unwantedCharacter : unwantedCharacters) {
                    if (data.contains(unwantedCharacter)) {
                        data = data.replace(unwantedCharacter, "");
                    }
                }

                //SPLIT THE STRING INTO ONLY WORDS
                String[] data1 = data.split(" ");

                //FOR EVERY WORD IN DATA CHECKS IF IT ALREADY EXISTS IN THE MAP
                //  IF IT EXISTS, UPS THE NUMBER OF TIMES IT APPEARS
                //  IF IT DOESN'T, ADDS THE ENTRY TO THE MAP
                for (String word : data1) {
                    if (!word.isEmpty()) {
                        if (map1.containsKey(word.toLowerCase())) {
                            map1.replace(word.toLowerCase(), map1.get(word.toLowerCase()), map1.get(word.toLowerCase()) + 1);
                        } else {
                            map1.put(word.toLowerCase(), 1);
                        }
                    }
                }

            }

            //PUTS THE ENTRY IN THE MAP RELATED TO THE FILE BEING WORKED ON BY THIS THREAD
            map.put(file.getName(), map1);

            //ADDS THE ENTRY TO THE SHARED MAP
            ApplicationFilesThreadService.addEntry(map);

            //CLOSES THE FILE
            Reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }


    }

    private void defineUnwantedCharacters() {
        String unwantedCharacter1 = "\n";
        unwantedCharacters.add(unwantedCharacter1);
        String unwantedCharacter2 = ",";
        unwantedCharacters.add(unwantedCharacter2);
        String unwantedCharacter3 = "!";
        unwantedCharacters.add(unwantedCharacter3);
        String unwantedCharacter4 = ".";
        unwantedCharacters.add(unwantedCharacter4);
        String unwantedCharacter5 = "\"";
        unwantedCharacters.add(unwantedCharacter5);
        String unwantedCharacter6 = "*";
        unwantedCharacters.add(unwantedCharacter6);
        String unwantedCharacter7 = "-";
        unwantedCharacters.add(unwantedCharacter7);
        String unwantedCharacter8 = "\r";
        unwantedCharacters.add(unwantedCharacter8);
        String unwantedCharacter9 = "\t";
        unwantedCharacters.add(unwantedCharacter9);
        String unwantedCharacter10 = ")";
        unwantedCharacters.add(unwantedCharacter10);
        String unwantedCharacter11 = "(";
        unwantedCharacters.add(unwantedCharacter11);
        String unwantedCharacter12 = "]";
        unwantedCharacters.add(unwantedCharacter12);
        String unwantedCharacter13 = "[";
        unwantedCharacters.add(unwantedCharacter13);
        String unwantedCharacter14 = "{";
        unwantedCharacters.add(unwantedCharacter14);
        String unwantedCharacter15 = "}";
        unwantedCharacters.add(unwantedCharacter15);
        String unwantedCharacter16 = ";";
        unwantedCharacters.add(unwantedCharacter16);
    }
}
