package space.novium.nebula.graphics.render;

import space.novium.nebula.graphics.render.batch.RenderBatch;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    //Static variables
    private static Renderer instance;
    
    //Instance variables
    private List<RenderBatch<?>> batches;
    
    public void render(double dt){
        for(RenderBatch<?> batch : batches){
            batch.render();
        }
    }
    
    private Renderer(){
        batches = new ArrayList<>();
    }
    
    public static Renderer get(){
        if(Renderer.instance == null){
            Renderer.instance = new Renderer();
        }
        return Renderer.instance;
    }
}
