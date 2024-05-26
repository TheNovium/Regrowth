package space.novium.util.math;

public class Matrix4f {
    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];
    
    private Matrix4f(){}
    
    public static Matrix4f identity(){
        Matrix4f result = new Matrix4f();
        for(int i = 0; i < SIZE; i++){
            result.elements[i + i * SIZE] = 1.0f;
        }
        return result;
    }
    
    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far){
        Matrix4f result = identity();
        result.elements[0] = 2.0f / (right - left);
        result.elements[5] = 2.0f / (top - bottom);
        result.elements[10] = 2.0f / (near - far);
        
        result.elements[12] = (left + right) / (left - right);
        result.elements[13] = (bottom + top) / (bottom - top);
        result.elements[14] = (far + near) / (far - near);
        
        return result;
    }
    
    public static Matrix4f standardOrthographic(){
        return orthographic(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
    }
}
