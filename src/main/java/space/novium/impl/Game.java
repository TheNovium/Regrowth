package space.novium.impl;

import space.novium.core.resources.annotation.AnnotationHandler;

public class Game {
    private static Game instance;
    
    private AnnotationHandler annotationHandler;
    
    private Game(){
        annotationHandler = AnnotationHandler.get();
    }
    
    public static Game get(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }
}
