package space.novium.util.math.vector;

public class Vector2f {
    public float x, y;
    
    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    public Vector2f(float d){
        this(d, d);
    }
    
    public Vector2f(){
        this(0, 0);
    }
    
    public void add(float dx, float dy){
        x += dx;
        y += dy;
    }
}
