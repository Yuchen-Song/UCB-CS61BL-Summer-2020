import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPuzzles {
    public static List<String> urlRegex(String[] urls) {
        List<String> ret = new ArrayList<>();
        String pattern = "\\(.*?https?://(\\w+\\.)+[a-z]{2,3}/\\w+.html.*?\\)";
        for (String s:urls) {
            if (s.matches(pattern)) {
                ret.add(s);
            }
        }
        return ret;
    }

    public static List<String> findStartupName(String[] names) {
        List<String> ret = new ArrayList<>();
        String pattern = "(Data|App|my|on|un)[A-Za-hj-z]+(ly|sy|ify|\\.io|\\.fm|\\.tv)";
        for (String s:names) {
            if (s.matches(pattern)) {
                ret.add(s);
            }
        }
        return ret;
    }

    public static BufferedImage imageRegex(String filename, int width, int height) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file found: " + filename);
        }

        // Initialize both Patterns and 3-d array
        int [][][] arr = new int[width][height][3];
        Pattern coordinatePattern = Pattern.compile("\\((\\d{1,3}), (\\d{1,3})\\)");
        Pattern RGBPattern = Pattern.compile("\\[(\\d{1,3}), (\\d{1,3}), (\\d{1,3})\\]");
        try {
            String line;
            while ((line = br.readLine()) != null) {
                // Initialize both Matchers and find() for each
                Matcher m1 = coordinatePattern.matcher(line);
                Matcher m2 = RGBPattern.matcher(line);
                m1.find();
                m2.find();
                // Parse each group as an Integer
                int x = Integer.parseInt(m1.group(1));
                int y  = Integer.parseInt(m1.group(2));
                int r = Integer.parseInt(m2.group(1));
                int g = Integer.parseInt(m2.group(2));
                int b = Integer.parseInt(m2.group(3));
                // Store in array
                arr[x][y][0] = r;
                arr[x][y][1] = g;
                arr[x][y][2] = b;
            }
        } catch (IOException e) {
            System.err.printf("Input error: %s%n", e.getMessage());
            System.exit(1);
        }
        // Return the BufferedImage of the array
        return arrayToBufferedImage(arr);
    }

    public static BufferedImage arrayToBufferedImage(int[][][] arr) {
        BufferedImage img = new BufferedImage(arr.length,
        	arr[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int pixel = 0;
                for (int k = 0; k < 3; k++) {
                    pixel += arr[i][j][k] << (16 - 8*k);
                }
                img.setRGB(i, j, pixel);
            }
        }

        return img;
    }

    public static void main(String[] args) {
        /* For testing image regex */
        BufferedImage img = imageRegex("mystery.txt", 400, 400);

        File outputfile = new File("output_img.jpg");
        try {
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
