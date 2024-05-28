package space.novium.util.math.vector;

public class Vector4i {
    public int x, y, w, h;
    
    public Vector4i(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public Vector4i(int d){
        this(d, d, d, d);
    }
}
