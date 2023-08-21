import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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

                StringJoiner joiner = new StringJoiner(NEWLINE_DELIMITER);
                for (WordFrequencyInfo word : wordFrequencyInfoList) {
                    String wordWithCount = word.getValue() + SPACE_CHARACTER + word.getWordCount();
                    joiner.add(wordWithCount);
                }
                return joiner.toString();
            } catch (Exception e) {
                return CALCULATE_ERROR;
            }
        }
    }

    private Map<String, List<WordFrequencyInfo>> getListMap(List<WordFrequencyInfo> wordFrequencyInfoList) {
        Map<String, List<WordFrequencyInfo>> map = new HashMap<>();
        for (WordFrequencyInfo input : wordFrequencyInfoList) {
            if (!map.containsKey(input.getValue())) {
                ArrayList wordFrequencyInfo = new ArrayList<>();
                wordFrequencyInfo.add(input);
                map.put(input.getValue(), wordFrequencyInfo);
            } else {
                map.get(input.getValue()).add(input);
            }
        }
        return map;
    }
}
