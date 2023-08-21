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

                List<Input> inputList = new ArrayList<>();
                for (String word : words) {
                    Input input = new Input(word, 1);
                    inputList.add(input);
                }

                //get the wordFrequencyMap for the next step of sizing the same word
                Map<String, List<Input>> wordFrequencyMap = getListMap(inputList);

                List<Input> frequencyInfos = new ArrayList<>();
                for (Map.Entry<String, List<Input>> entry : wordFrequencyMap.entrySet()) {
                    Input input = new Input(entry.getKey(), entry.getValue().size());
                    frequencyInfos.add(input);
                }
                inputList = frequencyInfos;

                inputList.sort((firstWord, secondWord) -> secondWord.getWordCount() - firstWord.getWordCount());

                StringJoiner joiner = new StringJoiner(NEWLINE_DELIMITER);
                for (Input word : inputList) {
                    String wordWithCount = word.getValue() + SPACE_CHARACTER + word.getWordCount();
                    joiner.add(wordWithCount);
                }
                return joiner.toString();
            } catch (Exception e) {
                return CALCULATE_ERROR;
            }
        }
    }

    private Map<String, List<Input>> getListMap(List<Input> inputList) {
        Map<String, List<Input>> map = new HashMap<>();
        for (Input input : inputList) {
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
