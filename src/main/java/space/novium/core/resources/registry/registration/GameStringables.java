package space.novium.core.resources.registry.registration;

import space.novium.core.event.EventListener;
import space.novium.core.event.EventType;
import space.novium.core.resources.registry.RegistryObject;
import space.novium.world.data.Direction;

import static space.novium.core.resources.registry.Registries.STRINGABLE_REGISTRY;

public class GameStringables {
    public static final RegistryObject<Enum<?>> DIRECTION = STRINGABLE_REGISTRY.register("direction", () -> Direction.SOUTH);
    
    @EventListener(event = EventType.STRINGABLE_REGISTRATION)
    public static void init(){}
}
