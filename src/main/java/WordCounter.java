import java.io.*;

public class WordCounter {

    private final String outpFilePath;
    private final String uniqFilePath;

    public WordCounter(String outpFilePath, String uniqFilePath) {
        this.outpFilePath = outpFilePath;
        this.uniqFilePath = uniqFilePath;
    }

    public void count() {
        getUniqWords();
        countCopies();
    }

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

    private void getUniqWords() {
        try (BufferedReader allWordsReader = new BufferedReader(new FileReader(outpFilePath))) {

            String word = "";
            while ((word = allWordsReader.readLine()) != null) {
                BufferedReader uniqWordsReader = new BufferedReader(new FileReader(uniqFilePath));
                if (!isCopy(word, uniqWordsReader)) {
                    uniqWordsReader.close();
                    try (FileWriter uniqWordsWriter = new FileWriter(uniqFilePath,true)) {
                        uniqWordsWriter.write(word + '\n');
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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