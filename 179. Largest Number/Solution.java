import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

class Solution {
    public String largestNumber(int[] nums) {
        return stream(nums).allMatch(n -> n == 0) 
            ? "0" 
            : stream(nums)
                .mapToObj(String::valueOf)
                .sorted((a, b) -> - (a + b).compareTo(b + a))
                .collect(joining());
    }

    // java Solution.java "[10,2]" "210" "[3,30,34,5,9]" "9534330" "[121,12]" "12121" "[0, 0]" 0
    public static void main(String... args) {
        for (int i = 0; i < args.length; i += 2) {
            String nums = args[i], expected = args[i + 1];
            System.out.println(String.format(
                "Output: %s | Expected: %s | Input: nums = %s",
                new Solution().largestNumber(array(nums)), expected, nums));
        }
    }

    private static int[] array(String s) {
        String[] elements = s.substring(1, s.length() - 1).replaceAll(" ", "").split(",");
        int[] arr = new int[elements.length];
        for (int i = 0; i < elements.length; i++)
            arr[i] = Integer.parseInt(elements[i]);
        return arr;
    }
}
