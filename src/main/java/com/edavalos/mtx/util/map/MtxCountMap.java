package com.edavalos.mtx.util.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MtxCountMap  extends HashMap<String, Integer> {

    public LinkedHashMap<String, Integer> getMapSortedByCount() {
        return this.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public String getMostCommonKey() {
        String mostCommonKey = null;
        int mostCommonCount = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : this.entrySet()) {
            if (entry.getValue() > mostCommonCount) {
                mostCommonKey = entry.getKey();
                mostCommonCount = entry.getValue();
            }
        }
        return mostCommonKey;
    }
}
