import java.util.*;

public class StringJoinSplitAdvanced {

    // Parse CSV row (handles quoted fields)
    static String[] parseCsvRow(String row) {
        List<String> fields = new ArrayList<>();
        boolean inQuote = false;
        StringBuilder current = new StringBuilder();
        for (char c : row.toCharArray()) {
            if (c == '"') { inQuote = !inQuote; }
            else if (c == ',' && !inQuote) { fields.add(current.toString().trim()); current.setLength(0); }
            else { current.append(c); }
        }
        fields.add(current.toString().trim());
        return fields.toArray(new String[0]);
    }

    // Word frequency from text
    static Map<String,Integer> wordFrequency(String text) {
        Map<String,Integer> freq = new TreeMap<>();
        for (String w : text.toLowerCase().split("[^a-zA-Z]+"))
            if (!w.isBlank()) freq.merge(w, 1, Integer::sum);
        return freq;
    }

    // Build query string
    static String buildQueryString(Map<String,String> params) {
        List<String> parts = new ArrayList<>();
        params.forEach((k,v) -> parts.add(k + "=" + v));
        return "?" + String.join("&", parts);
    }

    public static void main(String[] args) {
        System.out.println("=== CSV Parser ===");
        String[] csvLines = {
            "Alice,30,"New York, NY",Engineer",
            "Bob,25,London,Designer",
            "Carol,35,"Los Angeles, CA",Manager"
        };
        for (String line : csvLines) {
            String[] fields = parseCsvRow(line);
            System.out.printf("Name:%-8s Age:%-4s City:%-18s Job:%s%n",
                fields[0], fields[1], fields[2], fields[3]);
        }

        System.out.println("\n=== Word Frequency ===");
        String text = "to be or not to be that is the question to be";
        wordFrequency(text).forEach((w,c) -> System.out.printf("  %-12s %d%n", w, c));

        System.out.println("\n=== URL Builder ===");
        Map<String,String> params = new LinkedHashMap<>();
        params.put("query", "java+programming");
        params.put("page", "1");
        params.put("sort", "relevance");
        System.out.println("https://example.com/search" + buildQueryString(params));
    }
}
