import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import static java.lang.Math.min;

class TweetCounts {
    
    private final Map<String, List<Integer>> tweetTimes = new HashMap<>();

    public TweetCounts() {}

    public void recordTweet(String tweetName, int time) {
        if (tweetTimes.containsKey(tweetName)) {
            tweetTimes.get(tweetName).add(time);
        } else {
            List<Integer> list = new ArrayList<>();
            list.add(time);
            tweetTimes.put(tweetName, list);
        }
    }

    public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
        int delta = seconds(freq);
        List<Integer> tweets = new ArrayList<>();
        for (int time = startTime; time <= endTime; time += delta) {
            int t = time;
            tweets.add((int) tweetTimes.get(tweetName).stream()
                .filter(tweetTime -> tweetTime >= t)
                .filter(tweetTime -> tweetTime <= min(t + delta - 1, endTime))
                .count());
        }
        return tweets;
    }

    private int seconds(String freq) {
        int time = 1;
        switch (freq) {
            case "day": time *= 24;
            case "hour": time *= 60;
            case "minute": time *= 60;
        }
        return time;
    }

    public static void main(String... args) {
        TweetCounts t = new TweetCounts();
        t.recordTweet("tweet3", 0);
        t.recordTweet("tweet3", 60);
        t.recordTweet("tweet3", 10);
        System.out.println(String.format("Output: %s | Expected: %s",
            t.getTweetCountsPerFrequency("minute", "tweet3", 0, 59),
            "[2]"));
        System.out.println(String.format("Output: %s | Expected: %s",
            t.getTweetCountsPerFrequency("minute", "tweet3", 0, 60),
            "[2,1]"));
        t.recordTweet("tweet3", 120);
        System.out.println(String.format("Output: %s | Expected: %s",
            t.getTweetCountsPerFrequency("hour", "tweet3", 0, 210),
            "[4]"));
    }
}
