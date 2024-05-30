package space.novium.core.event.register;

import space.novium.core.event.EventType;
import space.novium.core.resources.ResourceLocation;
import space.novium.core.resources.annotation.AnnotationHandler;
import space.novium.core.resources.registry.Registries;
import space.novium.core.resources.registry.RegistryObject;
import space.novium.impl.annotation.Stringable;

import java.util.Set;

public class StringableEventRegister implements IEventRegister<Enum<?>> {
    public StringableEventRegister(){}
    
    @Override
    public void registerAll() {
        AnnotationHandler.runEvent(event());
        Set<ResourceLocation> resources = Registries.STRINGABLE_REGISTRY.locationSet();
        resources.forEach((rl) -> System.out.println("Registered Stringable " + rl));
    }
    
    @Override
    public boolean register(RegistryObject<Enum<?>> value) {
        return Stringable.StringableAnnotationChecker.implementsStringable(value.get());
    }
    
    @Override
    public EventType event() {
        return EventType.STRINGABLE_REGISTRATION;
    }
}
