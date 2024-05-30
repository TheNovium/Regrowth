package space.novium.core.resources.registry;

public class Registries {
    //Registry Keys
    public static final ResourceKey<Registry<Enum<?>>> STRINGABLES = Registry.createRegistryKey("stringables");
    
    //Registries
    public static final Registry<Enum<?>> STRINGABLE_REGISTRY = new Registry<>(STRINGABLES);
}
