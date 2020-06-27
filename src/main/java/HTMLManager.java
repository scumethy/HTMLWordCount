import org.jsoup.helper.HttpConnection;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HTMLManager {

    private String url;
    private String path;
    private String fileName;

    public HTMLManager(String url, String path, String fileName) {
        this.url = url;
        this.path = path;
        this.fileName = fileName;
    }

    public String download() {
        try {
            URL u = new URL(url);
            String fullPath = path + fileName;
            try (InputStream inputStream = u.openStream()) {
                try (FileOutputStream stream = new FileOutputStream(fullPath)) {
                    byte[] buffer = new byte[1024];
                    while (inputStream.read(buffer) != -1) {
                        stream.write(buffer);
                    }
                }
            }
            return fullPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
