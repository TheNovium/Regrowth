package space.novium.util;

import java.awt.image.*;

public final class ImageUtils {
    public static BufferedImage addImages(BufferedImage dst, BufferedImage src){
        int width = Math.min(dst.getWidth(), src.getWidth());
        int height = Math.min(dst.getHeight(), src.getHeight());
        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] srcClr = new int[width * height];
        int[] dstClr = new int[width * height];
        dst.getRGB(0, 0, width, height, srcClr, 0, width);
        src.getRGB(0, 0, width, height, dstClr, 0, width);
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                finalImage.setRGB(x, y, ColorUtils.layerNormal(srcClr[(x + (y * width))], dstClr[x + (y * width)]));
            }
        }
        return finalImage;
    }
    
    private ImageUtils(){}
}
