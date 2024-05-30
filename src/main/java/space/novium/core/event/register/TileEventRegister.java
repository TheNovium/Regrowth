package space.novium.core.event.register;

import com.google.gson.JsonObject;
import space.novium.core.event.EventType;
import space.novium.core.resources.ResourceLocation;
import space.novium.core.resources.annotation.AnnotationHandler;
import space.novium.core.resources.registry.RegistryObject;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasHandler;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasType;
import space.novium.util.IOUtils;
import space.novium.util.ImageUtils;
import space.novium.util.TextureUtils;
import space.novium.world.tile.Tile;

import java.awt.image.BufferedImage;

public class TileEventRegister implements IEventRegister<Tile> {
    private final TextureAtlasHandler.Builder builder;
    
    public TileEventRegister(TextureAtlasHandler.Builder builder){
        this.builder = builder;
    }
    
    @Override
    public void registerAll() {
        AnnotationHandler.runEvent(event(), this);
    }
    
    @Override
    public void register(RegistryObject<Tile> value) {
        ResourceLocation loc = value.getKey().getLocation();
        Tile tile = value.get();
        tile.setRegistryName(loc);
        ResourceLocation dataLoc = new ResourceLocation(loc.getNamespace(), "tiles/" + loc.getPath());
        IOUtils.loadJson(dataLoc).ifPresent((obj) -> {
            if(obj.has("textures")){
                JsonObject textures = obj.getAsJsonObject("textures");
                int i = 0;
                BufferedImage builtImage = null;
                while(textures.has("layer" + i)){
                    String imgLoc = textures.get("layer" + i).getAsString();
                    BufferedImage img = IOUtils.loadImage(new ResourceLocation(loc.getNamespace(), imgLoc)).orElse(TextureUtils.NO_TEXTURE);
                    if(builtImage == null){
                        builtImage = img;
                    } else {
                        builtImage = ImageUtils.addImages(builtImage, img);
                    }
                    i++;
                }
                if(i > 0){
                    builder.loadTexture(loc, TextureAtlasType.TILES, builtImage);
                    System.out.println("Loaded tile " + loc);
                } else {
                    System.err.println("Invalid file provided at " + loc);
                }
            }
        });
    }
    
    @Override
    public EventType event() {
        return EventType.TILE_REGISTRATION;
    }
}
