package space.novium.nebula.graphics.render.shader;

import space.novium.util.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private static boolean loaded = false;
    
    public static Shader DEFAULT;
    
    private final int ID;
    private final Map<String, Integer> locationCache;
    
    public Shader(String vertexPath, String fragmentPath){
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
    
    public static void loadShaders(){
        if(!loaded){
            loaded = true;
            Shader.DEFAULT = new Shader("shaders/default.vert","shaders/default.frag");
        }
    }
}