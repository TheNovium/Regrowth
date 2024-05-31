package space.novium.nebula.graphics.texture.atlas;

import space.novium.core.resources.ResourceLocation;
import space.novium.nebula.graphics.render.component.RenderObject;
import space.novium.util.math.vector.Vector4f;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TextureAtlasHandler {
    private final Map<TextureAtlasType, TextureAtlas> atlases;
    
    private TextureAtlasHandler(){
        atlases = new HashMap<>();
    }
    
    public TextureAtlas getAtlas(TextureAtlasType type){
        return atlases.get(type);
    }
    
    public RenderObject getRendererForResourceLocation(ResourceLocation loc, TextureAtlasType type){
        if(atlases.get(type).hasResource(loc)){
            TextureAtlas.LocationInformation info = atlases.get(type).getSpriteAtlasLocation(loc);
            RenderObject obj = new RenderObject(type, loc);
            if(info.spriteAtlas() != null){
                obj.withSpriteAtlas(info.spriteAtlas());
            }
            obj.setDrawLocation(atlases.get(type).getRelativeImageLocation(loc));
            return obj;
        }
        RenderObject obj = new RenderObject(TextureAtlasType.NONE, loc);
        obj.setDrawLocation(new Vector4f(0.0f, 0.0f, 1.0f, 1.0f));
        return obj;
    }
    
    public static class Builder {
        private final Map<TextureAtlasType, TextureAtlas.Builder> builders;
        
        public Builder(){
            builders = new HashMap<>(TextureAtlasType.values().length);
        }
        
        public Builder loadTexture(ResourceLocation loc, TextureAtlasType type){
            builders.computeIfAbsent(type, TextureAtlas.Builder::new).addImage(loc);
            return this;
        }
        
        public Builder loadTexture(ResourceLocation loc, TextureAtlasType type, BufferedImage img){
            builders.computeIfAbsent(type, TextureAtlas.Builder::new).addImage(loc, img);
            return this;
        }
        
        public TextureAtlasHandler build(){
            TextureAtlasHandler handler = new TextureAtlasHandler();
            for(TextureAtlasType type : TextureAtlasType.values()){
                TextureAtlas.Builder builder = builders.computeIfAbsent(type, TextureAtlas.Builder::new);
                handler.atlases.put(type, builder.build());
            }
            return handler;
        }
    }
}
