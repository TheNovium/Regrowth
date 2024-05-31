package space.novium.core.event.register;

import space.novium.core.event.EventType;
import space.novium.core.resources.ResourceLocation;
import space.novium.core.resources.annotation.AnnotationHandler;
import space.novium.core.resources.registry.Registries;
import space.novium.core.resources.registry.RegistryObject;
import space.novium.nebula.graphics.render.shader.Shader;

import java.util.Set;

public class ShaderEventRegister implements IEventRegister<Shader>{
    public ShaderEventRegister(){}
    
    @Override
    public void registerAll() {
        AnnotationHandler.runEvent(event());
        Set<ResourceLocation> resources = Registries.SHADER_REGISTRY.locationSet();
        resources.forEach((rl) -> System.out.println("Registered shader " + rl));
    }
    
    @Override
    public void register(RegistryObject<Shader> value) {
    
    }
    
    @Override
    public EventType event() {
        return EventType.SHADER_REGISTRATION;
    }
}
