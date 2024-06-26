package space.novium.util.math.vector;

public class Vector3f {
    public float x, y, z;
    
    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3f copy(){
        return new Vector3f(x, y, z);
    }
}
