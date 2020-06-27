import java.io.*;

public class WordCounter {

    private String outpFilePath;
    private String outpFullFilePath;
    private final String uniqFileFullPath;
    private final String resFileFullPath;

    public WordCounter(String path) {
        outpFilePath = path;
        outpFullFilePath = outpFilePath + "outp.txt";
        uniqFileFullPath = outpFilePath + "uniq.txt";
        resFileFullPath = outpFilePath + "res.txt";
    }

    public void run() throws FileNotFoundException {
        getUniqWords1(outpFullFilePath, uniqFileFullPath);
        countCopies(outpFullFilePath, uniqFileFullPath, resFileFullPath);
    }

    private void countCopies(String outpFilePath, String uniqFilePath, String resFilePath) {
        try (BufferedReader fileBufferReader1 = new BufferedReader(new FileReader(uniqFilePath))) {
            String uniqWord;
            String word;
            int wordCount = 0;
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

    /*private void getUniqWords(String outpFilePath, String uniqFilePath) throws FileNotFoundException {
        try (BufferedReader wordsFileReader = new BufferedReader(new FileReader(outpFilePath))) {
            try (RandomAccessFile uniqWordsFileWriter = new RandomAccessFile(uniqFilePath, "rw")) {
                FileOutputStream fos = new FileOutputStream(uniqWordsFileWriter.getFD());
                OutputStreamWriter osw = new OutputStreamWriter(fos);

                String word = "";
                while ((word = wordsFileReader.readLine()) != null) {
                    if (!isCopy(word, uniqWordsFileWriter)) {
                        osw.write(word + '\n');
                        osw.flush();
                    }
                }

                osw.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void getUniqWords1(String outpFilePath, String uniqFilePath) throws FileNotFoundException {
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