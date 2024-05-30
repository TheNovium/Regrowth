package space.novium.core.resources.registry;

import space.novium.impl.annotation.Stringable;
import space.novium.world.tile.Tile;

public class Registries {
    //Registry Keys
    public static final ResourceKey<Registry<Enum<?>>> STRINGABLES = Registry.createRegistryKey("stringables");
    public static final ResourceKey<Registry<Tile>> TILES = Registry.createRegistryKey("tiles");
    
    //Registries
    public static final Registry<Enum<?>> STRINGABLE_REGISTRY = new Registry<>(STRINGABLES, Stringable.StringableAnnotationChecker::implementsStringable);
    public static final Registry<Tile> TILE_REGISTRY = new Registry<>(TILES);
}
