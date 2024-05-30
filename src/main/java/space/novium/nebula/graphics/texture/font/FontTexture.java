package space.novium.nebula.graphics.texture.font;


import org.jetbrains.annotations.Nullable;
import space.novium.core.resources.ResourceLocation;
import space.novium.util.IOUtils;
import space.novium.util.math.vector.Vector4f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FontTexture {
    private final Font font;
    private final Map<Character, Glyph> glyphs;
    private final BufferedImage textImage;
    private final float size;
    
    public FontTexture(ResourceLocation location, float size){
        this.size = size;
        this.font = IOUtils.loadFont(location).deriveFont(Font.PLAIN, size);
        this.glyphs = new HashMap<>(224);
        this.textImage = createFontTexture();
    }
    
    private BufferedImage createFontTexture(){
        int textureWidth = 0;
        int textureHeight = 0;
        
        Map<Character, BufferedImage> charImages = new HashMap<>();
        
        for(char c = 32; c < 256; c++){
            if(c == 127) continue;
            BufferedImage currentChar = createCharImage(c);
            if(currentChar == null) continue;
            textureWidth += currentChar.getWidth();
            textureHeight = Math.max(textureHeight, currentChar.getHeight());
            charImages.put(c, currentChar);
        }
        
        BufferedImage img = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        int x = 0;
        
        for(Character c : charImages.keySet()){
            BufferedImage charImg = charImages.get(c);
            Glyph glyph = new Glyph(x, textureHeight - charImg.getHeight(), charImg.getWidth(), charImg.getHeight());
            g2d.drawImage(img, glyph.x, glyph.y, null);
            x += glyph.width;
            glyphs.put(c, glyph);
        }
        
        g2d.dispose();
        
        return textImage;
    }
    
    private @Nullable BufferedImage createCharImage(char c){
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.dispose();
        
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();
        if(charWidth == 0) return null;
        
        img = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(c), 0, metrics.getMaxAscent());
        g2d.dispose();
        
        return img;
    }
    
    public BufferedImage getTextImage(){
        return textImage;
    }
    
    public Vector4f getNormalizedCharLocation(char c){
        if(glyphs.containsKey(c)){
            Glyph g = glyphs.get(c);
            float imageW = (float)textImage.getWidth();
            float imageH = (float)textImage.getHeight();
            return new Vector4f((float)g.x / imageW, (float)g.y / imageH, (float)g.width / imageW, (float)g.height / imageH);
        }
        return new Vector4f(0.0f);
    }
    
    public record Glyph(int x, int y, int width, int height){}
}
