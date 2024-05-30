package space.novium.util;

import java.awt.*;

public final class ColorUtils {
    public static int layerNormal(int dst, int src){
        float transparency = getAlpha(src) / 255.0f;
        Color color = new Color(src, true);
        System.out.println(getAlpha(src));
        float calc = 1.0f - transparency;
        return toColor((int)(getRed(src) * transparency + getRed(dst) * calc), (int)(getGreen(src) * transparency + getGreen(dst) * calc), (int)(getBlue(src) * transparency + getBlue(dst) * calc), (int)(getAlpha(src) * transparency + getAlpha(dst) * calc));
    }
    
    public static int getAlpha(int clr){
        return (clr >> 24) & 0xff;
    }
    
    public static int getRed(int clr){
        return (clr & 0xff0000) >> 16;
    }
    
    public static int getGreen(int clr){
        return (clr & 0xff00) >> 8;
    }
    
    public static int getBlue(int clr){
        return clr & 0xff;
    }
    
    public static int toColor(int r, int g, int b, int a){
        //System.out.println("(" + r + ", " + g + ", " + b + ", " + a + ")");
        return a << 24 | r << 16 | g << 8 | b;
    }
    
    private ColorUtils(){}
}
