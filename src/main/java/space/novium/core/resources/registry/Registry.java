package space.novium.core.resources.registry;

import space.novium.core.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Registry<T> {
    public static final ResourceLocation ROOT_REGISTRY_NAME = new ResourceLocation("root");
    
    private static final Map<ResourceLocation, Registry<?>> REGISTRIES = new HashMap<>();
    
    public static Registry<?> getRegistry(ResourceLocation loc){
        return REGISTRIES.getOrDefault(loc, null);
    }
    
    public static <T> ResourceKey<Registry<T>> createRegistryKey(String key){
        return ResourceKey.createRegistryKey(new ResourceLocation(key));
    }
    
    private final Map<ResourceLocation, ResourceKey<? extends T>> locations;
    private final ResourceKey<? extends Registry<T>> key;
    private final Map<ResourceKey<?>, Supplier<T>> keyMap;
    private final Predicate<T> validator;
    
    public Registry(ResourceKey<Registry<T>> key){
        this(key, null);
    }
    
    public Registry(ResourceKey<Registry<T>> key, @Nullable Predicate<T> validator){
        this.locations = new HashMap<>();
        this.key = key;
        this.keyMap = new HashMap<>();
        this.validator = validator;
        REGISTRIES.put(key.getLocation(), this);
        
    }
    
    public ResourceKey<? extends Registry<T>> getKey(){
        return key;
    }
    
    public <I extends T> RegistryObject<I> register(final String name, final Supplier<? extends I> supplier){
        if(validator != null && !validator.test(supplier.get())){
            throw new IllegalArgumentException(name + " is an invalid object for registry " + key.getRegistryName() + ". Please check documentation.");
        }
        final ResourceLocation loc = new ResourceLocation(name);
        RegistryObject<I> ret;
        if(key != null){
            ret = RegistryObject.create(loc, key);
        } else {
            throw new IllegalStateException("Cannot process a registry without a key!");
        }
        Supplier<? extends T> val = keyMap.putIfAbsent(ret.getKey(), supplier::get);
        if(val != null) throw new IllegalStateException("Unable to add duplicate registration " + name);
        locations.put(ret.getKey().getLocation(), ret.getKey());
        ret.updateRegisterReference();
        return ret;
    }
    
    public boolean containsKey(ResourceLocation location){
        return locations.containsKey(location);
    }
    
    public boolean containsKey(ResourceKey<?> contains){
        return keyMap.containsKey(contains);
    }
    
    public <I extends T> T getValue(ResourceLocation loc){
        if(containsKey(loc)){
            return getValue(locations.get(loc));
        }
        return null;
    }
    
    public <I extends T> T getValue(ResourceKey<?> resourceKey){
        if(containsKey(resourceKey)){
            Supplier<T> temp = keyMap.get(resourceKey);
            if(temp == null) System.out.println("Failed to load from " + resourceKey.getLocation() + " in registry " + resourceKey.getRegistryName());
            return temp != null ? temp.get() : null;
        }
        return null;
    }
    
    public Set<ResourceKey<?>> keySet(){
        return keyMap.keySet();
    }
}
