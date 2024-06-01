package space.novium.nebula.graphics.render;

import space.novium.util.math.vector.Vector2f;

public class Transform {
    private Vector2f position;
    private int z;
    private Vector2f scale;
    private float rotation;
    private boolean dirty;
    
    public Transform(){
        this(0.0f, 0.0f, 0, 1.0f, 1.0f);
    }
    
    public Transform(float x, float y, int z, float w, float h){
        this(x, y, z, w, h, 0.0f);
    }
    
    public Transform(float x, float y, int z, float w, float h, float rotation){
        this.position = new Vector2f(x, y);
        this.z = z;
        this.scale = new Vector2f(w, h);
        this.rotation = rotation;
        this.dirty = true;
    }
    
    public float getX(){
        return position.x;
    }
    
    public float getY(){
        return position.y;
    }
    
    public int getZ(){
        return z;
    }
    
    public float getW(){
        return scale.x;
    }
    
    public float getH(){
        return scale.y;
    }
    
    public float getRotation(){
        return rotation;
    }
    
    public Transform setPosition(float x, float y){
        position.x = x;
        position.y = y;
        setDirty();
        return this;
    }
    
    public Transform setZ(int z){
        this.z = z;
        setDirty();
        return this;
    }
    
    public Transform setScale(float x, float y){
        scale.x = x;
        scale.y = y;
        setDirty();
        return this;
    }
    
    public boolean isDirty(){
        return dirty;
    }
    
    public void setClean(){
        dirty = false;
    }
    
    private void setDirty(){
        dirty = true;
    }
}
