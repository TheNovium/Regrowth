package space.novium.world.level.update;

import space.novium.nebula.graphics.render.Renderer;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasHandler;

public abstract class LevelUpdateListener<T> {
    
    public abstract void handleRender(Renderer renderer, TextureAtlasHandler textureAtlasHandler);
    
    public abstract T getObject();
}
