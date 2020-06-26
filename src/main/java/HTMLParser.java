import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HTMLParser {

    private final String filePath;

    public HTMLParser(String path) {
        filePath = path;
    }

    public int parse() {
        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(filePath))) {
            String fileLineContent;
            StringBuilder res = new StringBuilder();
            StringBuilder tagName = new StringBuilder();
            List<String> ignoredTags = new ArrayList<>(Arrays.asList("style", "script", "image", "object"));
            List<String> closeIgnoredTags = new ArrayList<>(Arrays.asList("/style", "/script", "/image", "/object"));
            boolean openTag = false;
            boolean openIgnoreTag = false;
            int lineCount = 0;
            PrintWriter pw = new PrintWriter("/home/ilgiz/outp.txt", "UTF-8");

            while ((fileLineContent = fileBufferReader.readLine()) != null) {
                lineCount++;

                for (int i = 0, n = fileLineContent.length(); i < n; i++) {

                    char c = fileLineContent.charAt(i);
                    if (c == '>' && !openIgnoreTag) {
                        openTag = false;
                        tagName = new StringBuilder();
                    } else if (c == '>' && openIgnoreTag) {
                        openTag = true;
                        tagName = new StringBuilder();
                    } else if (c == '<') {
                        openTag = true;
                        tagName = new StringBuilder();
                    } else if (openTag) {
                        tagName.append(c);
                        if (ignoredTags.contains(tagName.toString())) {
                            openIgnoreTag = true;
                        } else if (closeIgnoredTags.contains(tagName.toString())) {
                            openIgnoreTag = false;
                            openTag = true;
                        }
                    } else if (!openTag && !openIgnoreTag) {
                        res.append(c);
                    }

                }
                String[] words = res.toString().toUpperCase().split("[ ,.!?\";:()\\[\\]\\r\\n\\t]+");
                for (String word : words) {
                    if (!word.equals("")) {
                        pw.write(word + '\n');
                    }
                }
                res = new StringBuilder();
            }
            pw.close();
            return lineCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}