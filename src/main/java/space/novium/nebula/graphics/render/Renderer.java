package space.novium.nebula.graphics.render;

import space.novium.nebula.graphics.Camera;
import space.novium.nebula.graphics.render.batch.RenderBatch;
import space.novium.nebula.graphics.render.component.RenderObject;
import space.novium.world.level.update.LevelUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    //Static variables
    private static Renderer instance;
    
    public static final int MAX_BATCH_SIZE = 1024;
    public static final int MAX_BATCH_COUNT = 1024;
    private static Camera camera;
    
    //Instance variables
    private List<RenderBatch<?>> batches;
    
    public void render(double dt){
        camera.update(dt);
        for(RenderBatch<?> batch : batches){
            batch.render(camera);
        }
    }
    
    public void add(RenderObject obj){
        boolean added = false;
        for(RenderBatch<?> batch : batches){
        }
    }
    
    private Renderer(){
        batches = new ArrayList<>();
        
        camera = new Camera();
    }
    
    public static Renderer get(){
        if(Renderer.instance == null){
            Renderer.instance = new Renderer();
        }
        return Renderer.instance;
    }
    
    public void processLevelUpdate(LevelUpdateListener listener){}
}
