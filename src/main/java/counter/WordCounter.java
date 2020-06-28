package counter;

import java.io.*;

/**
 * Class which counting all words and output to console frequency dictionary
 */
public class WordCounter {

    /**
     * path to splitted words file
     */
    private final String outpFilePath;
    /**
     * path to uniq file
     */
    private final String uniqFilePath;

    public WordCounter(String outpFilePath, String uniqFilePath) {
        this.outpFilePath = outpFilePath;
        this.uniqFilePath = uniqFilePath;
    }


    /**
     * Method which runs getUniqWords() and countCopies() methods
     */
    public void count() {
        getUniqWords();
        countCopies();
    }


    /**
     * Count how many times every uniq words occur in the splitted words file
     */
    private void countCopies() {
        try (BufferedReader uniqWordsReader = new BufferedReader(new FileReader(uniqFilePath))) {
            String uniqWord;
            String word;
            int wordCount = 0;
            System.out.println("Частотный словарь HTMl контента введённой страницы:");
            while ((uniqWord = uniqWordsReader.readLine()) != null) {
                try (BufferedReader allWordsReader = new BufferedReader(new FileReader(outpFilePath))) {
                    while ((word = allWordsReader.readLine()) != null) {
                        if (word.equals(uniqWord)) {
                            wordCount++;
                        }
                    }
                }
                System.out.println(uniqWord + " - " + wordCount);
                wordCount = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Select all uniq words from splitted words file.
     * Write these uniq words to another file.
     */
    private void getUniqWords() {
        try (BufferedReader allWordsReader = new BufferedReader(new FileReader(outpFilePath))) {

            String word = "";
            while ((word = allWordsReader.readLine()) != null) {
                BufferedReader uniqWordsReader = new BufferedReader(new FileReader(uniqFilePath));
                if (!isCopy(word, uniqWordsReader)) {
                    uniqWordsReader.close();
                    try (FileWriter uniqWordsWriter = new FileWriter(uniqFilePath, true)) {
                        uniqWordsWriter.write(word + '\n');
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the current word in the uniq words file already
     * Return true if current word in uniq file.
     * Return false if current word not yet in the uniq file.
     *
     * @param currentWord the current word for check
     * @param br          reader for read words from uniq words file
     * @return true or false
     * @throws IOException the io exception
     */
    private boolean isCopy(String currentWord, BufferedReader br) throws IOException {
        String word;

        while ((word = br.readLine()) != null) {
            if (currentWord.equals(word)) {
                return true;
            }
        }

        return false;
    }
}