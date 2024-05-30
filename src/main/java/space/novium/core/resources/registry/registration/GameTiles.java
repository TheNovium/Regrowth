package space.novium.core.resources.registry.registration;

import space.novium.core.event.EventListener;
import space.novium.core.event.EventType;
import space.novium.core.event.register.IEventRegister;
import space.novium.core.resources.registry.RegistryObject;
import space.novium.world.tile.Tile;

import static space.novium.core.resources.registry.Registries.TILE_REGISTRY;

public class GameTiles {
    public static final RegistryObject<Tile> GRASS = TILE_REGISTRY.register("grass", Tile::new);
    
    @EventListener(event = EventType.TILE_REGISTRATION)
    public static void init(IEventRegister<Tile> tileRegister){
        tileRegister.register(GRASS);
    }
}
