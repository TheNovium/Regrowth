package space.novium.core.resources.registry.registration;

import space.novium.core.event.EventListener;
import space.novium.core.event.EventType;
import space.novium.core.resources.ResourceLocation;
import space.novium.core.resources.registry.RegistryObject;
import space.novium.nebula.graphics.render.shader.Shader;

import static space.novium.core.resources.registry.Registries.SHADER_REGISTRY;

public class GameShaders {
    private static final Shader DEFAULT_SHADER = new Shader(new ResourceLocation("shaders/default.vert"), new ResourceLocation("shaders/default.frag"));
    
    public static final RegistryObject<Shader> DEFAULT = SHADER_REGISTRY.register("default", () -> DEFAULT_SHADER);
    
    @EventListener(event = EventType.SHADER_REGISTRATION)
    public static void init(){}
    
}
