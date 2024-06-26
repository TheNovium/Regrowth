package space.novium.nebula.graphics.render.shader;

import space.novium.core.resources.ResourceLocation;
import space.novium.util.ShaderUtils;
import space.novium.util.math.Matrix4f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int ID;
    private final Map<String, Integer> locationCache;
    
    public Shader(ResourceLocation vertexPath, ResourceLocation fragmentPath){
        ID = ShaderUtils.loadShaderProgram(vertexPath, fragmentPath);
        locationCache = new HashMap<>();
    }
    
    public void enable(){
        glUseProgram(ID);
    }
    
    public void disable(){
        glUseProgram(0);
    }
    
    public int getUniform(String name){
        if(locationCache.containsKey(name)){
            return locationCache.get(name);
        }
        int result = glGetUniformLocation(ID, name);
        if(result == -1){
            System.err.println("Unable to locate uniform with name " + name);
        } else {
            locationCache.put(name, result);
        }
        return result;
    }
    
    public void setUniformIntArr(String name, int[] arr){
        glUniform1iv(getUniform(name), arr);
    }
    
    public void setUniformMat4(String name, Matrix4f mat4){
        glUniformMatrix4fv(getUniform(name), false, mat4.toFloatBuffer());
    }
    
    public int shaderID(){
        return ID;
    }
}