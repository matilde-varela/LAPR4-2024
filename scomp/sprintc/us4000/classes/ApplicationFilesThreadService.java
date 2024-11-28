package jobs4u.base.applicationmanagement.application;

import jobs4u.base.applicationmanagement.domain.ApplicationFile;
import jobs4u.base.applicationmanagement.domain.FileWordCountThread;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ApplicationFilesThreadService {

    static Map<String, Map<String, Integer>> map = new TreeMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);


    public Map<String, Pair<Integer, List<String>>> getTop20Words(Set<ApplicationFile> applicationFiles) {
        map.clear();
        List<Thread> threads = new ArrayList<>();

        for (ApplicationFile applicationFile : applicationFiles) {
            threads.add(new Thread(new FileWordCountThread(applicationFile)));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Thread interrupted", e);
        }

        Map<String, Map<String, Integer>> newMap = map;
        return sortDescendingTop20Words(newMap);
    }

    private static Map<String, Pair<Integer, List<String>>> sortDescendingTop20Words(Map<String, Map<String, Integer>> top20Words) {
        Map<String, Pair<Integer, List<String>>> processedWords = processResult(top20Words);

        List<Map.Entry<String, Pair<Integer, List<String>>>> entryList = new ArrayList<>(processedWords.entrySet());
        entryList.sort((e1, e2) -> e2.getValue().getLeft().compareTo(e1.getValue().getLeft()));

        Map<String, Pair<Integer, List<String>>> sortedTop20Words = new LinkedHashMap<>();

        //if there's less than 20 entries that it only shows that
        int limit = Math.min(entryList.size(), 20);
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Pair<Integer, List<String>>> entry = entryList.get(i);
            sortedTop20Words.put(entry.getKey(), entry.getValue());
        }

        return sortedTop20Words;
    }

    private static Map<String, Pair<Integer, List<String>>> processResult(Map<String, Map<String, Integer>> top20Words) {
        Map<String, Pair<Integer, List<String>>> processedWords = new HashMap<>();
        int wordCount;

        for (Map.Entry<String, Map<String, Integer>> entry : top20Words.entrySet()) {
            wordCount = 0;
            Map<String, Integer> entryMap = entry.getValue();
            List<String> filenames = new ArrayList<>();
            for (Map.Entry<String, Integer> word : entryMap.entrySet()) {
                wordCount += word.getValue();
                filenames.add(word.getKey());
            }
            processedWords.put(entry.getKey(), Pair.of(wordCount, filenames));


        }
        return processedWords;
    }

    public synchronized static void addEntry(Map<String, Map<String, Integer>> map1) {
        for (String file : map1.keySet()) {
            for (String word : map1.get(file).keySet()) {
                if(!map.containsKey(word)) {
                    Map<String, Integer> map2 = new HashMap<>();
                    map2.put(file, map1.get(file).get(word));
                    map.put(word, map2);
                }else{
                    map.get(word).put(file, map1.get(file).get(word));
                }
            }
        }

    }
}
