package com.edavalos.mtx.util.string;

import com.edavalos.mtx.util.math.MtxMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MtxStringUtil {
    public static final String EMPTY = "";
    public static final String NEWLINE = String.valueOf('\n');
    public static final String COMMA = ",";
    public static final char SEPARATOR_CHAR = 'ҁ';
    public static final String SPACE_QUOTED_PATTERN = Pattern.quote(" ");
    public static final String DEFAULT_TESTLOG_VARIABLE_REPLACEMENT = "{}";

    protected static Map<String, String> leetSpeakMap = null;

    private MtxStringUtil() { }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }

    public static String[] splitAtChars(String string, char... chars) {
        if (chars.length == 0) {
            return new String[]{string};
        }

        if (chars.length == 1) {
            return string.split(Pattern.quote(String.valueOf(chars[0])), 2);
        }

        for (char separator : chars) {
            string = string.replaceFirst(Pattern.quote(String.valueOf(separator)), String.valueOf(SEPARATOR_CHAR));
        }
        return string.split(Pattern.quote(String.valueOf(SEPARATOR_CHAR)));
    }

    public static String repeat(String s, int times) {
        return new String(new char[times]).replace("\0", s);
    }

    public static String leftPad(String s, int length) {
        if (length <= 0) {
            return "";
        }
        if (length < s.length()) {
            return s.substring(s.length() - length);
        }
        if (length == s.length()) {
            return s;
        }
        return new String(new char[length - s.length()]).replace('\0', ' ') + s;
    }

    public static String rightPad(String s, int length) {
        if (length <= 0) {
            return "";
        }
        if (length < s.length()) {
            return s.substring(0, length);
        }
        if (length == s.length()) {
            return s;
        }
        return s + new String(new char[length - s.length()]).replace('\0', ' ');
    }

    public static String centerPad(String s, int length) {
        if (length <= 0) {
            return "";
        }
        if (length == s.length()) {
            return s;
        }

        if (length < s.length()) {
            int lengthDifference = s.length() - length;
            boolean front = true;
            while (lengthDifference > 0) {
                if (front) {
                    s = s.substring(1);
                    front = false;
                } else {
                    s = s.substring(0, s.length() - 1);
                    front = true;
                }
                lengthDifference --;
            }
            return s;
        }

        int extraLength = length - s.length();
        int leftPad = ((int) Math.ceil(((double) extraLength) / 2));
        int rightPad = ((int) Math.floor(((double) extraLength) / 2));

        return repeat(" ", leftPad) + s + repeat(" ", rightPad);
    }

    public static Integer toInt(String s) {
        Double v = toDouble(s);
        if (v == null) {
            return null;
        }

        if (v == Math.floor(v)) {
            return Integer.parseInt(v.toString().substring(0, v.toString().indexOf('.')));
        }
        return null;
    }

    public static Double toDouble(String s) {
        if (isEmpty(s)) {
            return null;
        }

        double i;
        try {
            i = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
        return i;
    }

    public static String[] mergeStringArrays(String[] arr1, String[] arr2, boolean removeDuplicates) {
        if (arr1 == null || arr1.length == 0) {
            return arr2;
        }
        if (arr2 == null || arr2.length == 0) {
            return arr1;
        }

        if (removeDuplicates) {
            List<String> arr1List = Arrays.asList(arr1);
            List<String> arr2EditableList = new ArrayList<>(Arrays.asList(arr2));
            for (String arr2Elem : arr2) {
                if (arr1List.contains(arr2Elem)) {
                    arr2EditableList.remove(arr2Elem);
                }
            }
            arr2 = arr2EditableList.toArray(new String[0]);
        }

        String[] arr1plus2 = new String[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, arr1plus2, 0, arr1.length);
        System.arraycopy(arr2, 0, arr1plus2, arr1.length, arr2.length);
        return arr1plus2;
    }

    public static int countOccurrencesOf(String string, String sub) {
        if (isEmpty(string) || isEmpty(sub)) {
            return 0;
        }
        if (sub.length() > string.length()) {
            return 0;
        }
        if (sub.length() == string.length()) {
            return sub.equals(string) ? 1 : 0;
        }

        int count = 0;
        int startingIdx = 0;
        int endingIdx = sub.length();
        while (endingIdx <= string.length()) {
            if (sub.equals(string.substring(startingIdx, endingIdx))) {
                count ++;
            }
            startingIdx ++;
            endingIdx ++;
        }
        return count;
    }

    public static boolean areParenthesisValid(String string) {
        Stack<Character> parenthesis = new Stack<>();

        for (char c : string.toCharArray()) {
            switch (c) {
                case '(', '[', '{' -> parenthesis.push(c);

                case ')' -> {
                    if (parenthesis.size() == 0) {
                        return false;
                    }
                    if (parenthesis.pop() != '(') {
                        return false;
                    }
                }
                case ']' -> {
                    if (parenthesis.size() == 0) {
                        return false;
                    }
                    if (parenthesis.pop() != '[') {
                        return false;
                    }
                }
                case '}' -> {
                    if (parenthesis.size() == 0) {
                        return false;
                    }
                    if (parenthesis.pop() != '{') {
                        return false;
                    }
                }
            }
        }
        return parenthesis.isEmpty();
    }

    public static String reverse(String string) {
        StringBuilder reversedString = new StringBuilder();
        for (int i = string.length() - 1; i >= 0; i--) {
            reversedString.append(string.charAt(i));
        }
        return reversedString.toString();
    }

    public static boolean isValidIpAddress(String string) {
        String[] address = string.split(Pattern.quote("."));
        if (address.length != 4) {
            return false;
        }

        for (String addressPart : address) {
            Integer numericalValue = toInt(addressPart);
            if (numericalValue == null) {
                return false;
            }
            if (numericalValue > 255 || numericalValue < 0) {
                return false;
            }
        }
        return true;
    }

    // taken from https://howtodoinjava.com/java/regex/us-postal-zip-code-validation/
    public static boolean isValidZipcode(String string) {
        return Pattern.matches("^[0-9]{5}(?:-[0-9]{4})?$", string);
    }

    // taken from https://stackoverflow.com/a/1757107
    public static String[] splitAtCommas(String string) {
        return string.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    public static String convertToMockingCase(String string) {
        StringBuilder mockingString = new StringBuilder();

        boolean nextCaseIsUpper = true;
        for (char c : string.toCharArray()) {
            if (Character.isLetter(c)) {
                mockingString.append(nextCaseIsUpper ? Character.toUpperCase(c) : Character.toLowerCase(c));
                nextCaseIsUpper = !nextCaseIsUpper;

            } else {
                mockingString.append(c);
            }
        }
        return mockingString.toString();
    }

    public static boolean isPalindrome(String string) {
        int start = 0;
        int end = string.length() - 1;
        while (start < end) {
            if (string.charAt(start) != string.charAt(end)) {
                return false;
            }
            start ++;
            end --;
        }
        return true;
    }

    public static boolean isPalindrome(String string, String delimiter) {
        if (isEmpty(string) || isEmpty(delimiter)) {
            return true;
        }

        String[] splitString = string.split(Pattern.quote(delimiter));

        if (splitString.length <= 1) {
            return true;
        }

        int left = 0;
        int right = splitString.length - 1;
        while (left < right) {
            if (!splitString[left].equals(splitString[right])) {
                return false;
            }

            left ++;
            right --;
        }
        return true;
    }

    public static char mostCommonChar(String string) {
        if (string == null || string.length() == 0) {
            return '\u0000';
        }

        HashMap<Character, Integer> tallyMap = new HashMap<>();
        for (char c : string.toCharArray()) {
            if (!tallyMap.containsKey(c)) {
                tallyMap.put(c, 1);
            } else {
                int occurrences = tallyMap.get(c);
                tallyMap.put(c, occurrences + 1);
            }
        }
        char mostCommon = string.charAt(0);
        int largest = 0;
        for (char d : tallyMap.keySet()) {
            if (tallyMap.get(d) > largest) {
                largest = tallyMap.get(d);
                mostCommon = d;
            }
        }
        return mostCommon;
    }

    public static String replaceFromMap(String input, Map<String, String> replacements) {
        for (Map.Entry<String, String> replacement : replacements.entrySet()) {
            if (!MtxStringUtil.isEmpty(replacement.getKey())) {
                String replacementValue = (replacement.getValue() == null)
                                          ? ""
                                          : replacement.getValue();
                input = input.replaceAll(replacement.getKey(), replacementValue);
            }
        }
        return input;
    }

    public static String leetSpeak(String input) {
        if (leetSpeakMap == null) {
            leetSpeakMap = new HashMap<>();
            leetSpeakMap.put("a", "4");
            leetSpeakMap.put("A", "4");
            leetSpeakMap.put("e", "3");
            leetSpeakMap.put("E", "3");
            leetSpeakMap.put("l", "1");
            leetSpeakMap.put("L", "1");
            leetSpeakMap.put("o", "0");
            leetSpeakMap.put("O", "0");
            leetSpeakMap.put("s", "5");
            leetSpeakMap.put("S", "5");
            leetSpeakMap.put("t", "7");
            leetSpeakMap.put("T", "7");
            leetSpeakMap.put("i", "!");
            leetSpeakMap.put("I", "!");
        }

        return MtxStringUtil.replaceFromMap(input, leetSpeakMap);
    }

    // Source: https://stackoverflow.com/a/1285588
    public static String concatenateWithOverlap(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new NullPointerException();
        }

        int len1 = s1.length() - 1;
        char last1 = s1.charAt(len1);
        char first2 = s2.charAt(0);

        int indexOfLast2 = s2.lastIndexOf(last1, Math.min(last1, Math.min(len1, s2.length() - 1)));
        while (indexOfLast2 != -1) {
            if (s1.charAt(len1 - indexOfLast2) == first2) {
                int ix = indexOfLast2;
                while ((ix != -1) && (s1.charAt(len1 - indexOfLast2 + ix)) == s2.charAt(ix)) {
                    ix--;
                }
                if (ix == -1) {
                    return s1 + s2.substring(indexOfLast2 + 1);
                }
            }

            indexOfLast2 = s2.lastIndexOf(last1, indexOfLast2 - 1);
        }

        return s1 + s2;
    }

    public static String joinObjectsAsStrings(Object... objects) {
        StringBuilder str = new StringBuilder();
        for (Object thing : objects) {
            String objectString = null;
            try {
                objectString = thing.toString();
            } catch (Exception e) {
                objectString = EMPTY;
            } finally {
                str.append(objectString);
            }
        }
        return str.toString();
    }

    public static <T> String joinArrayAsString(T[] array) {
        StringBuilder sb = new StringBuilder("[");
        if (array.length == 0) {
            return sb.append("]").toString();
        }

        for (int i = 0; i < array.length - 1; i++) {
            sb.append(array[i].toString()).append(", ");
        }
        sb.append(array[array.length - 1].toString()).append("]");
        return sb.toString();
    }

    public static String padZeroToDateRelatedInt(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Negative values cannot be padded: " + value);
        }
        if (value > 99) {
            throw new IllegalArgumentException("Value is too big to be padded (and fit in two digits): " + value);
        }

        if (value > 9) {
            return String.valueOf(value);
        } else {
            return "0" + value;
        }
    }

    // Inspired by: https://stackoverflow.com/a/2282765
    // @TODO: Make this more efficient
    public static String replaceLast(String fullStr, String toReplace, String replaceWith) {
        if (fullStr == null || toReplace == null || replaceWith == null) {
            return fullStr;
        }

        Pattern pattern = Pattern.compile(toReplace);
        Matcher matcher = pattern.matcher(fullStr);
        if (!matcher.find()) {
            return fullStr;
        }
        int lastMatchStart=0;
        do {
            lastMatchStart=matcher.start();
        } while (matcher.find());
        matcher.find(lastMatchStart);
        StringBuffer sb = new StringBuffer(fullStr.length());
        matcher.appendReplacement(sb, replaceWith);
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Split a String into two at any of the delimiters in the given array
     * @param s original String to split
     * @param possibleDelimiters array of Strings to use as delimiters
     * @param putDelimiterInFirst if true, will split the string after the delimiter, else will split it before
     * @param caseSensitive should check case of possibleDelimiters
     * @return String array of length two if delimiter is found or one if no delimiter is found
     */
    public static String[] splitAtMultiple(String s, String[] possibleDelimiters, boolean putDelimiterInFirst, boolean caseSensitive) {
        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int splitIdx = i + (putDelimiterInFirst ? 1 : 0);
            for (String delim : possibleDelimiters) {
                if ((caseSensitive && word.equals(delim)) || (!caseSensitive && word.equalsIgnoreCase(delim))) {
                    String part1 = String.join(" ", Arrays.copyOf(words, splitIdx));
                    String part2 = String.join(" ", Arrays.copyOfRange(words, splitIdx, words.length));
                    return new String[] {part1, part2};
                }
            }
        }
        return new String[] {s};
    }

    /**
     * Calculates the levenshtein distance between two strings.
     * Source: <a href="https://chatgpt.com/share/213d7e68-c706-4a81-9a85-0158758f91cf">ChatGPT</a>
     * @param a first string to compare
     * @param b second string to compare
     * @return distance score between strings a and b
     */
    public static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    /**
     * Calculates the Jaro-Winkler distance between two strings.
     * Source: <a href="https://chatgpt.com/share/b9eb9a6d-dbb1-4315-b3f4-81fee8df4eef">ChatGPT</a>
     * @param a first string to compare
     * @param b second string to compare
     * @return distance score between strings a and b
     */
    public static double jaroWinklerDistance(String a, String b) {
        if (a == null || b == null) {
            return 0.0;
        }

        int s1Len = a.length();
        int s2Len = b.length();

        if (s1Len == 0 && s2Len == 0) {
            return 1.0;
        }

        if (s1Len == 0 || s2Len == 0) {
            return 0.0;
        }

        int matchDistance = Math.max(s1Len, s2Len) / 2 - 1;

        boolean[] s1Matches = new boolean[s1Len];
        boolean[] s2Matches = new boolean[s2Len];

        int matches = 0;
        int transpositions = 0;

        for (int i = 0; i < s1Len; i++) {
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(i + matchDistance + 1, s2Len);

            for (int j = start; j < end; j++) {
                if (s2Matches[j]) {
                    continue;
                }
                if (a.charAt(i) != b.charAt(j)) {
                    continue;
                }
                s1Matches[i] = true;
                s2Matches[j] = true;
                matches++;
                break;
            }
        }

        if (matches == 0) {
            return 0.0;
        }

        int k = 0;
        for (int i = 0; i < s1Len; i++) {
            if (!s1Matches[i]) {
                continue;
            }
            while (!s2Matches[k]) {
                k++;
            }
            // This is not needed (until it is)
//            if (a.charAt(i) != b.charAt(k)) {
//                transpositions++;
//            }
            k++;
        }
        transpositions /= 2;

        double jaroDistance = ((double) matches / s1Len +
                (double) matches / s2Len +
                (double) (matches - transpositions) / matches) / 3.0;

        int prefixLength = 0;
        int maxPrefixLength = 4;

        for (int i = 0; i < MtxMath.min(maxPrefixLength, s1Len, s2Len); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                prefixLength++;
            } else {
                break;
            }
        }

        double winklerBoost = 0.1 * prefixLength * (1 - jaroDistance);
        return jaroDistance + winklerBoost;
    }

    /**
     * Calculates an abstract distance between two strings based on their characters' char values.
     * @param a first string to compare
     * @param b second string to compare
     * @return distance score between strings a and b
     */
    public static int genericHashDistance(String a, String b) {
        if (a == null || a.isEmpty() || b == null || b.isEmpty()) {
            return -1;
        }

        if (a.equals(b)) {
            return 0;
        }

        int size = Math.max(a.length(), b.length());
        int totalDiff = 0;
        for (int i = 0; i < size; i++) {
            int aChar;
            try {
                aChar = a.charAt(i);
            } catch (IndexOutOfBoundsException e) {
                aChar = 0;
            }

            int bChar;
            try {
                bChar = b.charAt(i);
            } catch (IndexOutOfBoundsException e) {
                bChar = 0;
            }

            totalDiff += Math.abs(aChar - bChar);
        }
        return totalDiff;
    }

    public static int countOccurrences(String fullString, String substring) {
        int occurrences = 0;
        while (fullString.contains(substring)) {
            fullString = fullString.replaceFirst(Pattern.quote(substring), "");
            occurrences++;
        }
        return occurrences;
    }

    public static String logFill(MtxStringWrapper variableReplacement, String template, String... args) {
        if (countOccurrences(template, variableReplacement.toString()) != args.length) {
            throw new IllegalArgumentException("Argument count mismatch");
        }

        String pattern = Pattern.quote(variableReplacement.toString());
        for (String arg : args) {
            template = template.replaceFirst(pattern, arg);
        }
        return template;
    }

    public static String logFill(String template, String... args) {
        return logFill(new MtxStringWrapper(DEFAULT_TESTLOG_VARIABLE_REPLACEMENT), template, args);
    }

    // ("k1=v1, k2=v2, k3={}", "v3") -> Map.of("k1", "v1", "k2", "v2", "k3", "v3")
    public static Map<String, String> logMapFill(String template, String... args) {
        Map<String, String> kvMap = new HashMap<>();

        String log = logFill(template, args);
        String[] kvPairs = log.split(",");
        for (String kvPair : kvPairs) {
            if (countOccurrences(kvPair, "=") == 1) {
                String[] kv = kvPair.split("=");
                kvMap.put(kv[0].trim(), kv[1].trim());
            }
        }
        return Collections.unmodifiableMap(kvMap);
    }
}
