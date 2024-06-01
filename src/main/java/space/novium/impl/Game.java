package space.novium.impl;

import space.novium.core.event.register.ShaderEventRegister;
import space.novium.core.event.register.StringableEventRegister;
import space.novium.core.event.register.TileEventRegister;
import space.novium.core.resources.annotation.AnnotationHandler;
import space.novium.nebula.Window;
import space.novium.nebula.graphics.Camera;
import space.novium.nebula.graphics.render.Renderer;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasHandler;
import space.novium.world.level.Level;
import space.novium.world.level.update.LevelUpdateListener;

public class Game {
    private static Game instance;
    
    private AnnotationHandler annotationHandler;
    private TextureAtlasHandler textureAtlasHandler;
    private Window window;
    private Renderer renderer;
    private Level level;
    private double timeSinceLastTick;
    private double secondsPerTick;
    
    private Game(){
        annotationHandler = AnnotationHandler.get();
        TextureAtlasHandler.Builder atlasBuilder = new TextureAtlasHandler.Builder();
        window = Window.get();
        renderer = Renderer.get();
        
        handleRegistration(atlasBuilder);
        
        window.setWindowTitle("Building Texture Atlas");
        textureAtlasHandler = atlasBuilder.build();
        renderer.bindTextures(textureAtlasHandler);
        
        level = new Level();
        level.addUpdateListener(this::handleUpdate);
        
        timeSinceLastTick = 0.0;
        secondsPerTick = 0.05;
        
        window.setWindowTitle("Regrowth");
    }
    
    public void update(double dt){
        timeSinceLastTick += dt;
        while(timeSinceLastTick > secondsPerTick){
            level.tick();
            timeSinceLastTick -= secondsPerTick;
        }
    }
    
    public void render(double dt){
        renderer.render(dt);
    }
    
    public void onResize(float ratio){
        Camera camera = renderer.getCamera();
        camera.snapZoom(ratio * 12.0f, 12.0f);
    }
    
    public void handleUpdate(LevelUpdateListener<?> l){
        l.handleRender(renderer, textureAtlasHandler);
    }
    
    public static Game get(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }
    
    private void handleRegistration(TextureAtlasHandler.Builder builder){
        window.setWindowTitle("Completing Registration");
        new ShaderEventRegister().registerAll();
        
        new StringableEventRegister().registerAll();
        
        new TileEventRegister(builder).registerAll();
    }
}
