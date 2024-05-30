package space.novium.nebula;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import space.novium.impl.Game;
import space.novium.nebula.graphics.render.shader.Shader;
import space.novium.nebula.graphics.texture.Texture;
import space.novium.util.ShaderUtils;
import space.novium.util.math.vector.Vector2f;
import space.novium.util.math.vector.Vector2i;
import space.novium.nebula.graphics.render.Renderer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    //Static variables
    private static Window instance;
    
    //Window variables
    private Vector2i windowSize;
    private Vector2f mousePos;
    private long window;
    private Renderer renderer;
    private Game game;
    
    private Window(){
        System.out.println("Creating a new window using GLFW...");
        start();
    }
    
    private void update(double dt){
        glfwPollEvents();
    }
    
    private void render(double dt){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        renderer.render(dt);
        
        glfwSwapBuffers(window);
    }
    
    private void start(){
        init();
        
        run();
    }
    
    private void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        
        if(!glfwInit()){
            throw new RuntimeException("Failed to initialize GLFW!");
        }
    
        windowSize = new Vector2i(800, 600);
    
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(windowSize.x, windowSize.y, "Init", NULL, NULL);
        
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();
        
        mousePos = new Vector2f();
        glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mousePos.x = (float)xpos;
                mousePos.y = (float)ypos;
            }
        });
        
        //TODO mouse button callback
        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
        
            }
        });
        
        //TODO key callback
        glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
        
            }
        });
        
        //TODO on window resize
        glfwSetFramebufferSizeCallback(window, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
        
            }
        });
        
        glfwSetWindowCloseCallback(window, new GLFWWindowCloseCallback() {
            @Override
            public void invoke(long window) {
                Texture.dispose();
                ShaderUtils.dispose();
            }
        });
    
        Shader.loadShaders();
        
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALWAYS);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        
        renderer = Renderer.get();
        game = Game.get();
    }
    
    private void run(){
        double time = glfwGetTime();
        while(!glfwWindowShouldClose(window)){
            double startTime = glfwGetTime();
            double dt = startTime - time;
            time = startTime;
            
            update(dt);
            render(dt);
        }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        System.exit(0);
    }
    
    public static Window get(){
        if(Window.instance == null){
            Window.instance = new Window();
        }
        return Window.instance;
    }
}
