package space.novium.nebula.graphics.texture.atlas;

import space.novium.core.resources.ResourceLocation;

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
