package space.novium.core.resources.registry;

import org.jetbrains.annotations.NotNull;
import space.novium.core.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Supplier;

public class RegistryObject<T> implements Supplier<T> {
    private final ResourceLocation name;
    private final ResourceLocation register;
    private ResourceKey<?> key;
    private T value;
    
    private RegistryObject(final ResourceLocation name, final ResourceLocation register){
        this.name = name;
        this.register = register;
        this.key = ResourceKey.create(register, name);
        updateReference(register);
    }
    
    public void updateRegisterReference(){
        updateReference(register);
    }
    
    @SuppressWarnings("unchecked")
    private void updateReference(ResourceLocation loc){
        if(name == null || loc == null) return;
        Registry<?> registry = Registry.getRegistry(loc);
        if(registry == null) return;
        if(registry.containsKey(name)){
            this.key = ResourceKey.create(registry.getKey(), name);
            this.value = (T)registry.getValue(name);
        }
    }
    
    public static <T, U extends T> RegistryObject<U> create(final ResourceLocation name, final ResourceKey<? extends Registry<T>> registry) {
        return new RegistryObject<>(name, registry.getLocation());
    }
    
    public ResourceKey<?> getKey(){
        return key;
    }
    
    @NotNull
    @Override
    public T get(){
        T temp = value;
        Objects.requireNonNull(temp, () -> "Registry object not present: " + name);
        return temp;
    }
}
