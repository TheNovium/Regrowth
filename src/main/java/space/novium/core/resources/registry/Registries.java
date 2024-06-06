package space.novium.core.resources.registry;

import space.novium.impl.annotation.Stringable;
import space.novium.nebula.graphics.render.shader.Shader;
import space.novium.world.entity.Entity;
import space.novium.world.tile.Tile;

public class Registries {
    //Registry Keys
    public static final ResourceKey<Registry<Entity>> ENTITIES = Registry.createRegistryKey("entities");
    public static final ResourceKey<Registry<Shader>> SHADERS = Registry.createRegistryKey("shaders");
    public static final ResourceKey<Registry<Enum<?>>> STRINGABLES = Registry.createRegistryKey("stringables");
    public static final ResourceKey<Registry<Tile>> TILES = Registry.createRegistryKey("tiles");
    
    //Registries
    public static final Registry<Entity> ENTITY_REGISTRY = new Registry<>(ENTITIES);
    public static final Registry<Shader> SHADER_REGISTRY = new Registry<>(SHADERS, Registries::isValidShader);
    public static final Registry<Enum<?>> STRINGABLE_REGISTRY = new Registry<>(STRINGABLES, Stringable.StringableAnnotationChecker::implementsStringable);
    public static final Registry<Tile> TILE_REGISTRY = new Registry<>(TILES);
    
    //Validation
    public static boolean isValidShader(Shader s){
        return s.shaderID() != -1;
    }
}
