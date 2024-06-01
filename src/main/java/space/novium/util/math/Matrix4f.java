package space.novium.util.math;

import space.novium.util.buffer.BufferUtils;
import space.novium.util.math.vector.Vector3f;

import java.nio.FloatBuffer;

public class Matrix4f {
    public static final int SIZE = 4;
    protected float[] elements = new float[SIZE * SIZE];
    
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
    
    public Matrix4f translate(Vector3f vec){
        return translate(vec.x, vec.y, vec.z);
    }
    
    public Matrix4f translate(float x, float y, float z){
        elements[12] += x;
        elements[13] += y;
        elements[14] += z;
        return this;
    }
    
    public Matrix4f scale(float s){
        return scale(s, s);
    }
    
    public Matrix4f scale(float x, float y){
        elements[0] = x;
        elements[5] = y;
        return this;
    }
    
    public Matrix4f rotate(float degrees){
        float rads = (float)Math.toRadians(degrees);
        float sin = (float)Math.sin(rads);
        float cos = (float)Math.cos(rads);
        
        elements[0] = cos;
        elements[1] = sin;
        elements[4] = -sin;
        elements[5] = cos;
        return this;
    }
    
    public FloatBuffer toFloatBuffer(){
        return BufferUtils.createFloatBuffer(elements);
    }
}
