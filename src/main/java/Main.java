import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        // https://www.simbirsoft.com/
        String url;
        String path;
        String fileName;
        url = "https://www.simbirsoft.com/";
        path = "/home/ilgiz";
        fileName = "naconecto.html";

        HTMLManager m = new HTMLManager(url, path, fileName);
        String fullPathToFile = m.download();
        HTMLParser p = new HTMLParser(fullPathToFile);
        int lineCount = p.parse();
        WordCounter wc = new WordCounter(fullPathToFile, lineCount);
        wc.run();
    }
}
