import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String SPACE_DELIMITER = "\\s+";
    public static final String NEWLINE_DELIMITER = "\n";
    public static final String SPACE_CHARACTER = " ";
    public static final String CALCULATE_ERROR = "Calculate Error";

    public String getResult(String inputStr) {
        if (inputStr.split(SPACE_DELIMITER).length == 1) {
            return inputStr + " 1";
        } else {
            try {
                List<WordFrequencyInfo> wordFrequencyInfoList = getWordFrequencyInfosList(inputStr);

                return generatePrintLines(wordFrequencyInfoList);
            } catch (Exception e) {
                return CALCULATE_ERROR;
            }
        }
    }

    private List<WordFrequencyInfo> getWordFrequencyInfosList(String inputStr) {
        //split the input string with 1 to n pieces of spaces
        String[] words = inputStr.split(SPACE_DELIMITER);

        List<WordFrequencyInfo> wordFrequencyInfoList = new ArrayList<>();
        for (String word : words) {
            WordFrequencyInfo wordFrequencyInfo = new WordFrequencyInfo(word, 1);
            wordFrequencyInfoList.add(wordFrequencyInfo);
        }

        //get the wordFrequencyMap for the next step of sizing the same word
        Map<String, List<WordFrequencyInfo>> wordFrequencyMap = getListMap(wordFrequencyInfoList);

        List<WordFrequencyInfo> frequencyInfos = new ArrayList<>();
        for (Map.Entry<String, List<WordFrequencyInfo>> entry : wordFrequencyMap.entrySet()) {
            WordFrequencyInfo wordFrequencyInfo = new WordFrequencyInfo(entry.getKey(), entry.getValue().size());
            frequencyInfos.add(wordFrequencyInfo);
        }
        wordFrequencyInfoList = frequencyInfos;

        wordFrequencyInfoList.sort((firstWord, secondWord) -> secondWord.getWordCount() - firstWord.getWordCount());
        return wordFrequencyInfoList;
    }

    private static String generatePrintLines(List<WordFrequencyInfo> wordFrequencyInfoList) {
        return wordFrequencyInfoList.stream()
                .map(wordFrequencyInfo -> wordFrequencyInfo.getWord() + SPACE_CHARACTER + wordFrequencyInfo.getWordCount())
                .collect(Collectors.joining(NEWLINE_DELIMITER));
    }

    private Map<String, List<WordFrequencyInfo>> getListMap(List<WordFrequencyInfo> wordFrequencyInfoList) {
        Map<String, List<WordFrequencyInfo>> wordAndFrequencyMap = new HashMap<>();
        for (WordFrequencyInfo wordFrequencyInfo : wordFrequencyInfoList) {
            if (!wordAndFrequencyMap.containsKey(wordFrequencyInfo.getWord())) {
                ArrayList wordFrequency = new ArrayList<>();
                wordFrequency.add(wordFrequencyInfo);
                wordAndFrequencyMap.put(wordFrequencyInfo.getWord(), wordFrequency);
            } else {

                wordAndFrequencyMap.get(wordFrequencyInfo.getWord()).add(wordFrequencyInfo);
            }
        }
        return wordAndFrequencyMap;
    }
}
