import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String SPACE_DELIMITER = "\\s+";
    public static final String NEWLINE_DELIMITER = "\n";
    public static final String SPACE_CHARACTER = " ";
    public static final String CALCULATE_ERROR = "Calculate Error";

    public String getResult(String inputStr) {
        String[] words = inputStr.split(SPACE_DELIMITER);
        try {
            List<WordFrequencyInfo> wordFrequencyInfo = getWordFrequencyInfo(words);
            Map<String, List<WordFrequencyInfo>> wordFrequencyMap = getWordFrequencyInfoMap(wordFrequencyInfo);
            List<WordFrequencyInfo> wordFrequencyInfoList = getWordFrequencyInfoList(wordFrequencyMap);
            return generatePrintLines(wordFrequencyInfoList);
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }

    private List<WordFrequencyInfo> getWordFrequencyInfoList(Map<String, List<WordFrequencyInfo>> wordFrequencyMap) {
        return wordFrequencyMap.entrySet().stream()
                .map(entry -> new WordFrequencyInfo(entry.getKey(), entry.getValue().size()))
                .sorted((firstWord, secondWord) -> secondWord.getWordCount() - firstWord.getWordCount())
                .collect(Collectors.toList());
    }

    private static List<WordFrequencyInfo> getWordFrequencyInfo(String[] words) {
        return Arrays.stream(words)
                .map(word -> new WordFrequencyInfo(word, 1))
                .collect(Collectors.toList());
    }

    private static String generatePrintLines(List<WordFrequencyInfo> wordFrequencyInfoList) {
        return wordFrequencyInfoList.stream()
                .map(wordFrequencyInfo -> wordFrequencyInfo.getWord() + SPACE_CHARACTER + wordFrequencyInfo.getWordCount())
                .collect(Collectors.joining(NEWLINE_DELIMITER));
    }

    private Map<String, List<WordFrequencyInfo>> getWordFrequencyInfoMap(List<WordFrequencyInfo> wordFrequencyInfoList) {
        return wordFrequencyInfoList.stream()
                .collect(Collectors.groupingBy(WordFrequencyInfo::getWord, Collectors.toList()));
    }
}
