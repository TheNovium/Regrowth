package space.novium.util.math.vector;

public class Vector2i {
    public int x, y;
    
    public Vector2i(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Vector2i(int d){
        this(d, d);
    }
}
