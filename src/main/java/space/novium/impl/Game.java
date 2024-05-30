package space.novium.impl;

import space.novium.core.event.register.StringableEventRegister;
import space.novium.core.resources.annotation.AnnotationHandler;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasHandler;

public class Game {
    private static Game instance;
    
    private AnnotationHandler annotationHandler;
    private TextureAtlasHandler textureAtlasHandler;
    
    private Game(){
        annotationHandler = AnnotationHandler.get();
        TextureAtlasHandler.Builder atlasBuilder = new TextureAtlasHandler.Builder();
        
        handleRegistration();
    }
    
    public static Game get(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }
    
    private void handleRegistration(){
        new StringableEventRegister().registerAll();
        
    }
}
