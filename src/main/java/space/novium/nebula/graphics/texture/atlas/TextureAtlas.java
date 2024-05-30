package space.novium.nebula.graphics.texture.atlas;

import org.jetbrains.annotations.Nullable;
import space.novium.core.resources.ResourceLocation;
import space.novium.nebula.graphics.texture.Texture;
import space.novium.util.IOUtils;
import space.novium.util.TextureUtils;
import space.novium.util.math.vector.Vector2i;
import space.novium.util.math.vector.Vector4i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class TextureAtlas {
    private final TextureAtlasType type;
    private final Map<ResourceLocation, LocationInformation> spriteAtlasMap;
    private Texture texture;
    
    private TextureAtlas(TextureAtlasType type){
        this.type = type;
        this.spriteAtlasMap = new HashMap<>();
    }
    
    private void setLocation(ResourceLocation location, LocationInformation info){
        spriteAtlasMap.put(location, info);
    }
    
    public LocationInformation getSpriteAtlasLocation(ResourceLocation loc){
        return spriteAtlasMap.getOrDefault(loc, getMissingTextureLocation());
    }
    
    public LocationInformation getMissingTextureLocation(){
        return spriteAtlasMap.getOrDefault("no_texture", new LocationInformation(null, new Vector4i(0)));
    }
    
    public boolean hasResource(ResourceLocation location){
        return spriteAtlasMap.containsKey(location);
    }
    
    public Texture getTexture() {
        return texture;
    }
    
    public TextureAtlasType getType() {
        return type;
    }
    
    private void setTexture(Texture texture){
        this.texture = texture;
    }
    
    public static class Builder{
        private Map<ResourceLocation, BufferedImage> images;
        private Vector2i largest;
        private TextureAtlas atlas;
        
        public Builder(TextureAtlasType type){
            this.images = new HashMap<>();
            this.largest = new Vector2i(0);
            this.atlas = new TextureAtlas(type);
            addImage(TextureUtils.NO_TEXTURE_LOCATION, TextureUtils.NO_TEXTURE);
        }
        
        public Builder addImage(ResourceLocation loc){
            IOUtils.loadImage(loc).ifPresent((img) -> addImage(loc, img));
            return this;
        }
        
        public Builder addImage(ResourceLocation loc, BufferedImage img){
            largest.x = Math.max(largest.x, img.getWidth());
            largest.y = Math.max(largest.y, img.getHeight());
            images.put(loc, img);
            return this;
        }
        
        public TextureAtlas build(){
            BufferedImage atlasImage;
            switch (atlas.getType()){
                case TEXT -> {
                    int height = 0;
                    for(ResourceLocation loc : images.keySet()){
                        BufferedImage temp = images.get(loc);
                        height += temp.getHeight();
                    }
                    atlasImage = new BufferedImage(largest.x, height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = atlasImage.createGraphics();
                    int y = 0;
                    for(ResourceLocation loc : images.keySet()){
                        BufferedImage temp = images.get(loc);
                        g2d.drawImage(temp, 0, y, null);
                        atlas.setLocation(loc, new LocationInformation(null, new Vector4i(0, y, temp.getWidth(), temp.getHeight())));
                        y += temp.getHeight();
                    }
                    g2d.dispose();
                }
                case NONE -> atlasImage = new BufferedImage(TextureUtils.NO_TEXTURE.getColorModel(), TextureUtils.NO_TEXTURE.getRaster(), TextureUtils.NO_TEXTURE.isAlphaPremultiplied(), null);
                case BLANK -> {
                    atlasImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                    atlasImage.setRGB(0, 0, 0xFFFFFFFF);
                }
                default -> {
                    int x = 0;
                    int y = 0;
                    int width = 0;
                    int height = 0;
                    int maxWidth = (int)(Math.max(largest.x, largest.y) * Math.ceil(Math.sqrt(images.size())));
                    List<ResourceLocation> imagesByShelf = new ArrayList<>();
                    for(ResourceLocation loc : images.keySet()) {
                        BufferedImage temp = images.get(loc);
                        width = temp.getWidth();
                        if(x + width > maxWidth){
                            x = 0;
                            y += height;
                            height = 0;
                        }
                        height = Math.max(height, temp.getHeight());
                        atlas.setLocation(loc, new LocationInformation(new SpriteAtlas(loc), new Vector4i(x, y, width, temp.getHeight())));
                        x += width;
                        imagesByShelf.add(loc);
                    }
                    atlasImage = new BufferedImage(maxWidth, y + height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d  = atlasImage.createGraphics();
                    for(ResourceLocation loc : imagesByShelf){
                        Vector4i imgLoc = atlas.getSpriteAtlasLocation(loc).location;
                        g2d.drawImage(images.get(loc), imgLoc.x, imgLoc.y, null);
                    }
                    g2d.dispose();
                }
            }
            atlas.setTexture(new Texture(atlasImage));
            IOUtils.saveImage(new ResourceLocation(atlas.getType().name()), atlasImage);
            return atlas;
        }
    }
    
    private record LocationInformation(@Nullable SpriteAtlas spriteAtlas, Vector4i location){}
}
