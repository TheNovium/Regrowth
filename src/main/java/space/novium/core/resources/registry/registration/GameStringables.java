package space.novium.core.resources.registry.registration;

import space.novium.core.resources.registry.RegistryObject;
import space.novium.world.data.Direction;

import static space.novium.core.resources.registry.Registries.STRINGABLE_REGISTRY;

public class GameStringables {
    public static final RegistryObject<Enum<?>> DIRECTION = STRINGABLE_REGISTRY.register("direction", () -> Direction.SOUTH);
    
    public static void init(){}
}
