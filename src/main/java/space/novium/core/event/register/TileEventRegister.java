package space.novium.core.event.register;

import com.google.gson.JsonObject;
import space.novium.core.event.EventType;
import space.novium.core.resources.ResourceLocation;
import space.novium.core.resources.annotation.AnnotationHandler;
import space.novium.core.resources.registry.RegistryObject;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasHandler;
import space.novium.world.tile.Tile;

public class TileEventRegister implements IEventRegister<Tile> {
    private TextureAtlasHandler.Builder builder;
    
    public TileEventRegister(TextureAtlasHandler.Builder builder){
        this.builder = builder;
    }
    
    @Override
    public void registerAll() {
        AnnotationHandler.runEvent(event(), this);
    }
    
    @Override
    public boolean register(RegistryObject<Tile> value) {
        ResourceLocation loc = value.getKey().getLocation();
        Tile tile = value.get();
        tile.setRegistryName(loc);
        ResourceLocation dataLoc = new ResourceLocation(loc.getNamespace(), "tile/" + loc.getPath());
        return false;
    }
    
    @Override
    public EventType event() {
        return EventType.TILE_REGISTRATION;
    }
}
