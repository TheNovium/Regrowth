package space.novium.core.resources.registry.registration;

import space.novium.core.event.EventListener;
import space.novium.core.event.EventType;
import space.novium.core.event.register.IEventRegister;
import space.novium.core.resources.registry.RegistryObject;
import space.novium.world.tile.Tile;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static space.novium.core.resources.registry.Registries.TILE_REGISTRY;

public class GameTiles {
    private static List<RegistryObject<Tile>> tiles = new LinkedList<>();
    
    public static final RegistryObject<Tile> GRASS = register("grass", Tile::new);
    public static final RegistryObject<Tile> SAND = register("sand", Tile::new);
    public static final RegistryObject<Tile> RED_BRICK = register("red_brick", Tile::new);
    
    @EventListener(event = EventType.TILE_REGISTRATION)
    public static void init(IEventRegister<Tile> tileRegister){
        for(RegistryObject<Tile> tile : tiles){
            tileRegister.register(tile);
        }
    }
    
    private static RegistryObject<Tile> register(String name, Supplier<Tile> tile){
        RegistryObject<Tile> obj = TILE_REGISTRY.register(name, tile);
        tiles.add(obj);
        return obj;
    }
}
