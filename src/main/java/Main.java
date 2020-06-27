import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        // https://www.simbirsoft.com/
        System.out.println("Введите адрес сайта:");
        String url = scanner.nextLine();
        System.out.println("Введите путь до файла для загрузки страницы:");
        String path = scanner.nextLine();
        System.out.println("Введите имя файла для загрузки страницы:");
        String fileName = scanner.nextLine();
        //path = "D:\\simbdata\\";
        //fileName = "roflan.html";

        HTMLManager m = new HTMLManager(url, path, fileName);
        String fullPathToFile = m.download();
        HTMLParser p = new HTMLParser(path+"outp.txt", fullPathToFile);
        p.parse();
        WordCounter wc = new WordCounter(path);
        System.out.println("Частотный словарь HTMl контента введённой страницы:");
        wc.run();
    }
}
