import java.util.*;

import static java.util.stream.Collectors.*;

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        var map1 = occurrences(s1);        
        var map2 = occurrences(s2.substring(0, s1.length()));
        var balance = map1.entrySet().stream()
            .collect(toMap(Map.Entry::getKey, e -> map2.getOrDefault(e.getKey(), 0) - e.getValue()));
        if (balance.values().stream().allMatch(n -> n >= 0)) {
            return true;
        }
        for (var i = 0; i + s1.length() < s2.length(); i++) {
            balance.computeIfPresent(s2.charAt(i), (k, v) -> v - 1);
            balance.computeIfPresent(s2.charAt(i + s1.length()), (k, v) -> v + 1);
            if (balance.values().stream().allMatch(n -> n >= 0)) {
                return true;
            }
        }
        return false;
    }

    private Map<Character, Integer> occurrences(String s) {
        return s.chars()
            .mapToObj(c -> (char) c)
            .collect(groupingBy(c -> c, summingInt(c -> 1)));
    }

    // java Solution.java "ab" "eidbaooo" "true" "ab" "eidboaoo" "false"
    public static void main(String... args) {
        for (int i = 0; i < args.length; i += 3) {
            String s1 = args[i], s2 = args[i + 1], expected = args[i + 2];
            System.out.println(String.format(
                "Output: %s | Expected: %s | Input: s1 = %s, s2 = %s",
                new Solution().checkInclusion(s1, s2), expected, s1, s2));
        }
    }
}
