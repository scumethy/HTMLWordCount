import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HTMLManager {

    public String url = null;
    public String path;
    public String fileName;

    public File htmlSource;
    public File htmlWords;
    public File uniqWords;

    public void run() {

        while (!inputData()) {
            System.out.println("\nВы ввели неправильные начальные данные! Попробуйте снова.");
            inputData();
        }
        filesPrepares();

        download(htmlSource.getPath());
        HTMLParser p = new HTMLParser(htmlWords.getPath(), htmlSource.getPath());
        p.parse();

        WordCounter wc = new WordCounter(htmlWords.getPath(), uniqWords.getPath());
        wc.count();

    }

    private void filesPrepares() {
        try {
            htmlSource = new File(path + "source.html");
            htmlSource.createNewFile();
            htmlWords = new File(path + "outp.txt");
            htmlWords.createNewFile();
            uniqWords = new File(path + "uniq.txt");
            uniqWords.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean inputData() {
        Scanner scanner = new Scanner(System.in);
        // https://www.simbirsoft.com/
        System.out.println("Введите адрес сайта:");
        url = scanner.nextLine();
        //String url = "https://www.simbirsoft.com/";
        System.out.println("Введите путь до файла для загрузки страницы:");
        path = scanner.nextLine();
        //String path = "D:\\simbdata\\";
        System.out.println("Введите имя файла для загрузки страницы:");
        fileName = scanner.nextLine();
        //String fileName = "roflan.html";
        //path = "D:\\simbdata\\";
        //fileName = "roflan.html";

        try {
            URL connectionURL = new URL(url);
            URLConnection conn = connectionURL.openConnection();
            conn.connect();
        } catch (MalformedURLException e) {
            System.out.println("Неправильно введён адрес сайта!");
            return false;
        } catch (IOException e) {
            System.out.println("Сайт не отвечает!");
            return false;
        }

        File correctDirectory = new File(path);
        if (!correctDirectory.exists()) {
            return false;
        }

        return true;
    }

    private String download(String htmlSourcesFilePath) {
        try {
            URL u = new URL(url);
            try (InputStream inputStream = u.openStream()) {
                try (FileOutputStream stream = new FileOutputStream(htmlSourcesFilePath)) {
                    byte[] buffer = new byte[1024];
                    while (inputStream.read(buffer) != -1) {
                        stream.write(buffer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
