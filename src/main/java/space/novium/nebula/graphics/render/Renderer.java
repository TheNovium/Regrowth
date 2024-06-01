package space.novium.nebula.graphics.render;

import space.novium.nebula.graphics.Camera;
import space.novium.nebula.graphics.render.batch.RenderBatch;
import space.novium.nebula.graphics.render.batch.SpriteObjectBatch;
import space.novium.nebula.graphics.render.component.RenderObject;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasHandler;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasType;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL13.*;

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
            if(batch instanceof SpriteObjectBatch sprBatch && sprBatch.hasRoom(obj)){
                sprBatch.addRenderObject(obj);
                added = true;
                break;
            }
        }
        
        if(!added){
            SpriteObjectBatch batch = new SpriteObjectBatch(MAX_BATCH_SIZE, batches.size());
            batch.start();
            batch.addRenderObject(obj);
            batches.add(batch);
        }
    }
    
    private Renderer(){
        batches = new ArrayList<>();
        
        camera = new Camera();
    }
    
    public void bindTextures(TextureAtlasHandler handler){
        for(TextureAtlasType type : TextureAtlasType.values()){
            glActiveTexture(type.getGlTexture());
            handler.getAtlas(type).getTexture().bind();
        }
    }
    
    public Camera getCamera() {
        return camera;
    }
    
    public static Renderer get(){
        if(Renderer.instance == null){
            Renderer.instance = new Renderer();
        }
        return Renderer.instance;
    }
}
