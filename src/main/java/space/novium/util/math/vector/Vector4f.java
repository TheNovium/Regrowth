package space.novium.util.math.vector;

public class Vector4f {
    public float x, y, w, h;
    
    public Vector4f(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public Vector4f(float d){
        this(d, d, d, d);
    }
}
