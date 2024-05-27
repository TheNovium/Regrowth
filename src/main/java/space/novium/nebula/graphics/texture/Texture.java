package space.novium.nebula.graphics.texture;

import space.novium.util.buffer.BufferUtils;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int width, height;
    private final int texture;
    
    private static List<Integer> allTextures = new LinkedList<>();
    
    public Texture(BufferedImage image){
        texture = processImage(image);
        Texture.allTextures.add(texture);
    }
    
    private int processImage(BufferedImage image){
        width = image.getWidth();
        height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        int[] data = new int[pixels.length];
        for(int i = 0; i < pixels.length; i++){
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);
            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        int ret = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, ret);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        return ret;
    }
    
    public int getTexture(){
        return texture;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void bind(){
        glBindTexture(GL_TEXTURE_2D, texture);
    }
    
    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    public static void dispose(){
        glBindTexture(GL_TEXTURE_2D, 0);
        for(Integer i : allTextures){
            glDeleteTextures(i);
        }
    }
}
