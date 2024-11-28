package jobs4u.base.applicationmanagement.domain;

import jobs4u.base.applicationmanagement.application.ApplicationFilesThreadService;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationFilesThreadServiceTest {

    ApplicationFilesThreadService service = new ApplicationFilesThreadService();


    @Test
    public void ensureItCountsCorrectly1() {

        Map<String, Pair<Integer, List<String>>> map = new TreeMap<>();

        org.apache.commons.lang3.tuple.Pair<Integer, List<String>> pair;

        List<String> list = new ArrayList<>();

        list.add("test_file_1.txt");

        pair = org.apache.commons.lang3.tuple.Pair.of(1,list);

        map.put("a", pair);
        map.put("b", pair);
        map.put("c", pair);
        map.put("d", pair);
        map.put("e", pair);
        map.put("f", pair);
        map.put("g", pair);
        map.put("h", pair);
        map.put("i", pair);
        map.put("j", pair);
        map.put("k", pair);
        map.put("l", pair);
        map.put("m", pair);
        map.put("n", pair);
        map.put("o", pair);
        map.put("p", pair);
        map.put("q", pair);
        map.put("r", pair);
        map.put("s", pair);
        map.put("t", pair);

        ApplicationFile file = new ApplicationFile(new File("../scomp/sprintc/us4000/US4000_Test_Files/test_file_1.txt"));

        Set<ApplicationFile> set = new HashSet<>();

        set.add(file);

        assertEquals(map, service.getTop20Words(set));
    }

    @Test
    public void ensureItCountsCorrectly2() {

        Map<String, Pair<Integer, List<String>>> map = new TreeMap<>();

        org.apache.commons.lang3.tuple.Pair<Integer, List<String>> pair;

        List<String> list = new ArrayList<>();

        list.add("test_file_2.txt");

        pair = org.apache.commons.lang3.tuple.Pair.of(1,list);

        map.put("a", pair);
        map.put("b", pair);
        map.put("c", pair);
        map.put("d", pair);
        map.put("e", pair);
        map.put("f", pair);
        map.put("g", pair);
        map.put("h", pair);
        map.put("i", pair);
        map.put("j", pair);
        map.put("k", pair);
        map.put("l", pair);
        map.put("m", pair);
        map.put("n", pair);
        map.put("o", pair);
        map.put("p", pair);
        map.put("q", pair);
        map.put("r", pair);
        map.put("s", pair);
        map.put("t", pair);

        ApplicationFile file = new ApplicationFile(new File("../scomp/sprintc/us4000/US4000_Test_Files/test_file_2.txt"));

        Set<ApplicationFile> set = new HashSet<>();

        set.add(file);

        assertEquals(map, service.getTop20Words(set));
    }

    @Test
    public void ensureItCountsCorrectly3() {

        Map<String, Pair<Integer, List<String>>> map = new TreeMap<>();

        org.apache.commons.lang3.tuple.Pair<Integer, List<String>> pair, pair1;

        List<String> list = new ArrayList<>();

        list.add("test_file_3.txt");

        pair = org.apache.commons.lang3.tuple.Pair.of(4,list);

        map.put("test", pair);
        map.put("file", pair);
        map.put("made", pair);
        map.put("with", pair);
        map.put("intent", pair);
        map.put("to", pair);
        map.put("check", pair);
        map.put("if", pair);
        map.put("thread", pair);
        map.put("can", pair);
        map.put("count", pair);
        map.put("words", pair);
        map.put("correctly", pair);

        pair1 = org.apache.commons.lang3.tuple.Pair.of(12,list);

        map.put("the", pair1);

        ApplicationFile file = new ApplicationFile(new File("../scomp/sprintc/us4000/US4000_Test_Files/test_file_3.txt"));

        Set<ApplicationFile> set = new HashSet<>();

        set.add(file);

        assertEquals(map, service.getTop20Words(set));
    }

}