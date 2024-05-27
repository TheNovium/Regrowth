package space.novium.util;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public final class ShaderUtils {
    private final static List<Integer> shaders = new LinkedList<>();
    
    private ShaderUtils(){}
    
    public static int loadShaderProgram(String vertPath, String fragPath){
        String vert = IOUtils.loadAsString(vertPath).orElseThrow(() -> new RuntimeException("Failed to load vertex shader file!"));
        String frag = IOUtils.loadAsString(fragPath).orElseThrow(() -> new RuntimeException("Failed to load fragment shader file!"));
        
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        
        glShaderSource(vertexShader, vert);
        glCompileShader(vertexShader);
        checkShaderCompilation(vertexShader);
        
        glShaderSource(fragmentShader, frag);
        glCompileShader(fragmentShader);
        checkShaderCompilation(fragmentShader);
        
        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        checkProgramLinkage(shaderProgram);
        
        glDetachShader(shaderProgram, vertexShader);
        glDetachShader(shaderProgram, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        
        shaders.add(shaderProgram);
        return shaderProgram;
    }
    
    private static void checkShaderCompilation(int shader){
        if(glGetShaderi(shader, GL_COMPILE_STATUS) != GL_TRUE){
            throw new RuntimeException("Failed to compile shader: " + glGetShaderInfoLog(shader));
        }
    }
    
    private static void checkProgramLinkage(int program){
        if(glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE){
            throw new RuntimeException("Failed to link shader: " + glGetProgramInfoLog(program));
        }
    }
    
    public static void dispose(){
        glUseProgram(0);
        for(Integer i : shaders){
            glDeleteProgram(i);
        }
    }
}
