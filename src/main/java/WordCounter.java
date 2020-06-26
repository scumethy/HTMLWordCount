import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordCounter {

    private final String filePath;
    private final int lineCount;

    public WordCounter(String filePath, int lineCount) {
        this.filePath = filePath;
        this.lineCount = lineCount;
    }

    public void run() throws FileNotFoundException {
        getUniqWords();
        countCopies();
    }

    private void countCopies() {
        try (BufferedReader fileBufferReader1 = new BufferedReader(new FileReader("/home/ilgiz/uniq.txt"))) {
            try (BufferedReader fileBufferReader2 = new BufferedReader(new FileReader("/home/ilgiz/outp.txt"))) {
                try (PrintWriter wcResWriter = new PrintWriter("/home/ilgiz/res.txt", "UTF-8")) {
                    String uniqWord;
                    String word;
                    int wordCount = 0;
                    while ((uniqWord = fileBufferReader1.readLine()) != null) {
                        for (int i = 0; i < lineCount; i++) {
                            word = fileBufferReader2.readLine();
                            if (word.equals(uniqWord)) {
                                wordCount++;
                            }
                        }
                        wcResWriter.write(uniqWord + ": " + wordCount);
                        wordCount = 0;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUniqWords() throws FileNotFoundException {
        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader("/home/ilgiz/outp.txt"))) {
            try (RandomAccessFile uniqWordsFileWriter = new RandomAccessFile("/home/ilgiz/uniq.txt", "rw")) {
                FileOutputStream fos = new FileOutputStream(uniqWordsFileWriter.getFD());
                OutputStreamWriter osw = new OutputStreamWriter(fos);

                String word = "";
                for (int i = 0; i < lineCount; i++) {
                    word = fileBufferReader.readLine();
                    if (!isCopy(word, uniqWordsFileWriter)) {
                        osw.write(word+'\n');
                        osw.flush();
                    }
                }

                osw.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCopy(String currentWord, RandomAccessFile raf) throws IOException {
        String word;
        raf.seek(0);

        while ((word = raf.readLine()) != null) {
            word = new String(word.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(word);
            if (currentWord.equals(word)) {
                return true;
            }
        }

        return false;
    }
}