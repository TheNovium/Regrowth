package space.novium.util;

import space.novium.core.resources.ResourceLocation;
import space.novium.util.math.vector.Vector4f;

import java.awt.image.BufferedImage;

public final class TextureUtils {
    public static final ResourceLocation NO_TEXTURE_LOCATION = new ResourceLocation("no_texture");
    public static final BufferedImage NO_TEXTURE = IOUtils.loadImage(NO_TEXTURE_LOCATION).orElseThrow(() -> new RuntimeException("Cannot initialize without empty image"));
    public static final int[] textInt = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    
    public static float[] getTextureDraw(float x, float y, float w, float h){
        return new float[]{
                x, y + h,
                x, y,
                x + w, y,
                x + w, y + h
        };
    }
    
    public static float[] getTextureDraw(Vector4f vec){
        return getTextureDraw(vec.x, vec.y, vec.w, vec.h);
    }
    
    public static int[] generateIndices(int maxSize){
        int[] elements = new int[6 * maxSize];
        for (int i = 0; i < maxSize; i++) {
            int offsetArray = 6 * i;
            int offsetVertex = 4 * i; // Offset for vertices, not array elements
            
            elements[offsetArray] = offsetVertex;
            elements[offsetArray + 1] = offsetVertex + 1;
            elements[offsetArray + 2] = offsetVertex + 2;
            
            elements[offsetArray + 3] = offsetVertex + 2;
            elements[offsetArray + 4] = offsetVertex + 3;
            elements[offsetArray + 5] = offsetVertex;
        }
        return elements;
    }
}
