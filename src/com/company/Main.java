package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String[] username = {"h","eiy","cq","h","cq","txldsscx","cq","txldsscx","h","cq","cq"};
        int[] timestamp = {527896567,334462937,517687281,134127993,859112386,159548699,51100299,444082139,926837079,317455832,411747930};
        String[] website = {"hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","hibympufi","yljmntrclw","hibympufi","yljmntrclw"};
        new Solution().mostVisitedPattern(username, timestamp, website);
    }
}

class Solution {
    TreeMap<Integer, TreeSet<String>> freqMap;
    Map<String, Integer> freqs;

    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        List<Data> data = new ArrayList<>();
        Map<String, List<String>> users = new HashMap<>();
        freqMap = new TreeMap<>(Collections.reverseOrder());
        freqs = new HashMap<>();
        for (int i = 0; i < username.length; i++) {
            data.add(new Data(username[i], timestamp[i], website[i]));
        }

        data.sort((d1, d2) -> d1.timestamp - d2.timestamp);

        for (int i = 0; i < data.size(); i++) {
            Data d = data.get(i);
            users.putIfAbsent(d.username, new ArrayList<>());
            users.get(d.username).add(d.website);
        }

        for (List<String> list: users.values()) {
            patterns(list, 0, new ArrayList<>(), new HashSet<>());
        }

        Iterator<String> itr = freqMap.get(freqMap.firstKey()).iterator();
        String[] resArr = ( (String)itr.next()).split("->");
        return Arrays.asList(resArr);
    }

    private void patterns(List<String> list, int st, List<String> temp, Set<String> set) {
        if (temp.size() == 3) {
            String pattern = temp.get(0) + "->" + temp.get(1) + "->" + temp.get(2);
            if (set.contains(pattern))
                return;
            set.add(pattern);
            if (!freqs.containsKey(pattern)) {
                freqs.put(pattern, 1);
                freqMap.putIfAbsent(1, new TreeSet<>());
                freqMap.get(1).add(pattern);
            } else {
                int oldFreq = freqs.get(pattern);
                freqs.put(pattern, oldFreq + 1);
                freqMap.get(oldFreq).remove(pattern);
                freqMap.putIfAbsent(oldFreq + 1, new TreeSet<>());
                freqMap.get(oldFreq + 1).add(pattern);
            }
            return;
        }

        for (int i = st; i < list.size(); i++) {
            temp.add(list.get(i));
            patterns(list, i + 1, temp, set);
            temp.remove(temp.size() - 1);
        }
    }

    private class Data{
        String username;
        int timestamp;
        String website;

        Data(String _username, int _timestamp, String _website) {
            username = _username;
            timestamp = _timestamp;
            website = _website;
        }
    }
}
