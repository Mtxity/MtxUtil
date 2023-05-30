package com.edavalos.mtx.util.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Pattern;

public final class MtxStringUtil {
    public static final String EMPTY = "";
    public static final String NEWLINE = String.valueOf('\n');
    public static final char SEPARATOR_CHAR = 'ҁ';

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

        if (v == Math.floor(v) && v == Math.ceil(v)) {
            return Integer.parseInt(v.toString().substring(0, v.toString().indexOf('.')));
        } else {
            return null;
        }
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

    public static String joinObjectsAsStrings(Objects... objects) {
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
}
