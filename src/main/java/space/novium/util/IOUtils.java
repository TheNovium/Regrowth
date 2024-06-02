package space.novium.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import space.novium.core.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

public final class IOUtils {
    private static final String ROOT = System.getProperty("user.dir") + "/resources/";
    
    private IOUtils(){
        System.out.println("Root folder located at " + ROOT);
    }
    
    public static Optional<String> loadAsString(ResourceLocation location){
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
    
    public static Font loadFont(ResourceLocation location){
        try {
            InputStream stream = getAsResourceStream(location, "resources/fonts", "ttf");
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (Exception e){
            System.err.println("Failed to load font at " + location);
            e.printStackTrace();
            return new Font("Monospace", Font.PLAIN, 48);
        }
    }
    
    public static Optional<BufferedImage> loadImage(ResourceLocation location){
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
    
    public static void saveImage(ResourceLocation loc, BufferedImage img){
        try {
            File outputFile = new File(ROOT + loc.getNamespace() + "/export/" + loc.getPath() + ".png");
            if(!outputFile.getParentFile().exists()){
                outputFile.getParentFile().mkdirs();
            }
            ImageIO.write(img, "png", outputFile);
        } catch (Exception e){
            System.err.println("Failed to save image to " + loc);
            e.printStackTrace();
        }
    }
    
    public static Optional<JsonObject> loadJson(ResourceLocation loc){
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(getAsResourceStream(loc, "data", ".json")));
            return Optional.of(new Gson().fromJson(reader, JsonObject.class));
        } catch (Exception e){
            System.err.println("Unable to load JSON object from " + loc);
        }
        return Optional.empty();
    }
    
    public static Optional<JsonObject> loadChunkData(String location){
        try {
            InputStream stream = new FileInputStream(ROOT + "/saves/chunks/" + location + ".json");
        } catch (Exception e){
        
        }
        return Optional.empty();
    }
    
    private static InputStream getAsResourceStream(ResourceLocation location) throws FileNotFoundException {
        return getAsResourceStream(location, "data");
    }
    
    private static InputStream getAsResourceStream(ResourceLocation location, String subfolder) throws FileNotFoundException {
        return getAsResourceStream(location, subfolder, "");
    }
    
    private static InputStream getAsResourceStream(ResourceLocation location, String subfolder, String filetype) throws FileNotFoundException {
        return new FileInputStream(ROOT + location.getNamespace() + "/" + subfolder + "/" + location.getPath() + filetype);
    }
}
