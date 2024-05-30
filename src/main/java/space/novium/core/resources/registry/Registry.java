package space.novium.core.resources.registry;

import space.novium.core.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Registry<T> {
    public static final ResourceLocation ROOT_REGISTRY_NAME = new ResourceLocation("root");
    
    private static final Map<ResourceLocation, Registry<?>> REGISTRIES = new HashMap<>();
    
    public static Registry<?> getRegistry(ResourceLocation loc){
        return REGISTRIES.getOrDefault(loc, null);
    }
    
    private final Map<ResourceLocation, ResourceKey<?>> locations;
    private final ResourceKey<? extends Registry<T>> key;
    private final Map<ResourceKey<?>, Supplier<T>> keyMap;
    
    public Registry(ResourceKey<Registry<T>> key){
        this.locations = new HashMap<>();
        this.key = key;
        this.keyMap = new HashMap<>();
        REGISTRIES.put(key.getLocation(), this);
    }
    
    public ResourceKey<? extends Registry<T>> getKey(){
        return key;
    }
    
    public <I extends T> RegistryObject<I> register(final String name, final Supplier<I> supplier){
        final ResourceLocation loc = new ResourceLocation(name);
        RegistryObject<I> ret;
        if(key != null){
            ret = RegistryObject.create(loc, key);
        } else {
            throw new IllegalStateException("Cannot process a registry without a key!");
        }
        Supplier<T> val = keyMap.putIfAbsent(ret.getKey(), supplier::get);
        if(val == null) throw new IllegalStateException("Unable to add duplicate registration " + name);
        locations.put(ret.getKey().getLocation(), ret.getKey());
        ret.updateRegisterReference();
        return ret;
    }
    
    public boolean containsKey(ResourceLocation location){
        return locations.containsKey(location);
    }
    
    public boolean containsKey(ResourceKey<T> contains){
        return keyMap.containsKey(contains);
    }
    
    public <I extends T> T getValue(ResourceLocation loc){
        if(containsKey(loc)){
            Supplier<T> temp = keyMap.get(locations.get(loc));
            if(temp == null) System.out.println("Failed to load from " + loc.toString());
            return temp != null ? temp.get() : null;
        }
        return null;
    }
}
