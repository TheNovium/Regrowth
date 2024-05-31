package space.novium.impl;

import space.novium.core.event.register.ShaderEventRegister;
import space.novium.core.event.register.StringableEventRegister;
import space.novium.core.event.register.TileEventRegister;
import space.novium.core.resources.annotation.AnnotationHandler;
import space.novium.nebula.Window;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasHandler;

public class Game {
    private static Game instance;
    
    private AnnotationHandler annotationHandler;
    private TextureAtlasHandler textureAtlasHandler;
    private Window window;
    
    private Game(){
        annotationHandler = AnnotationHandler.get();
        TextureAtlasHandler.Builder atlasBuilder = new TextureAtlasHandler.Builder();
        window = Window.get();
        
        handleRegistration(atlasBuilder);
        
        window.setWindowTitle("Building Texture Atlas");
        textureAtlasHandler = atlasBuilder.build();
        
        window.setWindowTitle("Regrowth");
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
