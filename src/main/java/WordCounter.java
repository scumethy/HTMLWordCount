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
        try (BufferedReader fileBufferReader1 = new BufferedReader(new FileReader(uniqFilePath))) {
            String uniqWord;
            String word;
            int wordCount = 0;
            System.out.println("Частотный словарь HTMl контента введённой страницы:");
            while ((uniqWord = fileBufferReader1.readLine()) != null) {
                try (BufferedReader fileBufferReader2 = new BufferedReader(new FileReader(outpFilePath))) {
                    while ((word = fileBufferReader2.readLine()) != null) {
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
        try (BufferedReader wordsFileReader = new BufferedReader(new FileReader(outpFilePath))) {

            String word = "";
            while ((word = wordsFileReader.readLine()) != null) {
                BufferedReader uniqWordsFileReader = new BufferedReader(new FileReader(uniqFilePath));
                if (!isCopy(word, uniqWordsFileReader)) {
                    uniqWordsFileReader.close();
                    try (FileWriter uniqWordsFileWriter = new FileWriter(uniqFilePath,true)) {
                        uniqWordsFileWriter.write(word + '\n');
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