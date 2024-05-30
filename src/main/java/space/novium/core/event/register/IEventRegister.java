package space.novium.core.event.register;

import space.novium.core.event.EventType;
import space.novium.core.resources.registry.RegistryObject;

public interface IEventRegister<V> {
    void registerAll();
    
    void register(RegistryObject<V> value);
    
    EventType event();
}
