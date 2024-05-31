package space.novium.nebula.graphics.render.component;

import space.novium.core.resources.ResourceLocation;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasType;
import space.novium.util.math.vector.Vector4f;

public class RenderObject {
    private TextureAtlasType textureAtlasType;
    private ResourceLocation textureLocation;
    private Vector4f color;
    private Vector4f drawLocation;
    private boolean dirty = true;
    
    public RenderObject(TextureAtlasType textureAtlasType, ResourceLocation textureLocation){
        this.textureAtlasType = textureAtlasType;
        this.textureLocation = textureLocation;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawLocation = new Vector4f(1.0f);
    }
    
    public void setDrawLocation(Vector4f draw){
        drawLocation = draw;
        setDirty();
    }
    
    public Vector4f getDrawLocation(){
        return drawLocation;
    }
    
    public TextureAtlasType getTextureAtlasType(){
        return textureAtlasType;
    }
    
    public Vector4f getColor(){
        return color;
    }
    
    public void setColor(Vector4f color){
        this.color = color;
        setDirty();
    }
    
    public void setClean(){
        dirty = false;
    }
    
    private void setDirty(){
        dirty = true;
    }
    
    public boolean isDirty(){
        return dirty;
    }
}
