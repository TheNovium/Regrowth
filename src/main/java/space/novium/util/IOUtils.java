package space.novium.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public final class IOUtils {
    private static final String ROOT = System.getProperty("user.dir") + "/resources/";
    
    private IOUtils(){
        System.out.println(ROOT);
    }
    
    public static Optional<String> loadAsString(String location){
        StringBuilder result = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(getAsResourceStream(location));
            char[] buffer = new char[1024];
            int charsRead;
            while((charsRead = reader.read(buffer)) != -1){
                result.append(buffer, 0, charsRead);
            }
            reader.close();
            return Optional.of(result.toString());
        } catch (Exception e){
            System.err.println("Resource not found at location " + location);
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    public static Font loadFont(String location){
        try {
            InputStream stream = getAsResourceStream(location, "resources/fonts", "ttf");
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (Exception e){
            System.err.println("Failed to load font at " + location);
            e.printStackTrace();
            return new Font("Monospace", Font.PLAIN, 48);
        }
    }
    
    public static Optional<BufferedImage> loadImage(String location){
        try {
            InputStream stream = getAsResourceStream(location, "textures", ".png");
            BufferedImage img = ImageIO.read(stream);
            stream.close();
            return Optional.of(img);
        } catch (Exception e){
            System.err.println("Failed to load texture at " + location);
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    private static InputStream getAsResourceStream(String location) throws FileNotFoundException {
        return getAsResourceStream(location, "data");
    }
    
    private static InputStream getAsResourceStream(String location, String subfolder) throws FileNotFoundException {
        return getAsResourceStream(location, subfolder, "");
    }
    
    private static InputStream getAsResourceStream(String location, String subfolder, String filetype) throws FileNotFoundException {
        return new FileInputStream(ROOT + subfolder + "/" + location + filetype);
    }
}
