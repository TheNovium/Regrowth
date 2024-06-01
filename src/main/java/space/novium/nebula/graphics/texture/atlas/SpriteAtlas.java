package space.novium.nebula.graphics.texture.atlas;

import com.google.gson.JsonObject;
import space.novium.core.resources.ResourceLocation;
import space.novium.util.IOUtils;
import space.novium.util.math.vector.Vector4f;

import java.awt.image.BufferedImage;
import java.util.Random;

public class SpriteAtlas {
    private boolean rotation = false;
    
    public SpriteAtlas(ResourceLocation location){
        /*IOUtils.loadJson(location).ifPresent((data) -> {
            if(data.has("rotation")){
                rotation = data.get("rotation").getAsBoolean();
            }
        });*/
    }
    
    public Vector4f getCurrentDrawLocation(Vector4f totalLocation){
        Vector4f ret = totalLocation.copy();
        if(rotation){
            Random random = new Random();
            int dirX = random.nextInt(2);
            int dirY = random.nextInt(2);
            ret.x += totalLocation.w * dirX;
            ret.y += totalLocation.h * dirY;
            ret.w -= totalLocation.w * 2 * dirX;
            ret.h -= totalLocation.h * 2 * dirY;
        }
        return ret;
    }
}
