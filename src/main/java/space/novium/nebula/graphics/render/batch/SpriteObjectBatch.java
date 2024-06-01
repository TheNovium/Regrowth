package space.novium.nebula.graphics.render.batch;

import space.novium.core.resources.registry.registration.GameShaders;
import space.novium.nebula.graphics.Camera;
import space.novium.nebula.graphics.render.Renderer;
import space.novium.nebula.graphics.render.component.RenderObject;
import space.novium.nebula.graphics.render.shader.Shader;
import space.novium.util.TextureUtils;
import space.novium.util.math.vector.Vector2f;
import space.novium.util.math.vector.Vector4f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class SpriteObjectBatch extends RenderBatch<RenderObject> {
    /**
     * Vertex Array Information
     * ------------------------
     * Position                 Color                           Texture Coordinates     Rotation    Texture ID
     * float, float, float,     float, float, float, float,     float, float,           float,      float
     * **/
    private static final int POSITION_SIZE = 3;
    private static final int COLOR_SIZE = 4;
    private static final int TEXTURE_COORDINATES_SIZE = 2;
    private static final int ROTATION_SIZE = 1;
    private static final int ID_SIZE = 1;
    private static final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + TEXTURE_COORDINATES_SIZE + ROTATION_SIZE + ID_SIZE;
    
    private static final int POSITION_OFFSET = 0;
    private static final int COLOR_OFFSET = POSITION_OFFSET + POSITION_SIZE * Float.BYTES;
    private static final int TEXTURE_COORDINATES_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    private static final int ROTATION_OFFSET = TEXTURE_COORDINATES_OFFSET + TEXTURE_COORDINATES_SIZE * Float.BYTES;
    private static final int ID_OFFSET = ROTATION_OFFSET + ROTATION_SIZE * Float.BYTES;
    private static final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;
    
    private RenderObject[] renderObjects;
    private int numObjects;
    private float[] vertices;
    
    private int vao, vbo, ebo;
    private float zDraw;
    
    public SpriteObjectBatch(int maxBatchSize, int zIndex) {
        super(maxBatchSize, zIndex);
        this.renderObjects = new RenderObject[maxBatchSize];
        
        this.vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];
        
        this.numObjects = 0;
        this.zDraw = ((float)zIndex) / ((float) Renderer.MAX_BATCH_COUNT);
    }
    
    @Override
    public void render(Camera camera) {
        boolean rebuffData = false;
        for(int i = 0; i < numObjects; i++){
            RenderObject obj = renderObjects[i];
            if(obj.isDirty()){
                loadVertexProperties(i);
                obj.setClean();
                rebuffData = true;
            }
        }
        
        if(rebuffData){
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }
    
        Shader shader = GameShaders.DEFAULT.get();
        shader.enable();
        shader.setUniformMat4("ortho_matrix", camera.getProjectionMatrix());
        shader.setUniformMat4("view_matrix", camera.getViewMatrix());
        shader.setUniformIntArr("textures", TextureUtils.textInt);
    
        glBindVertexArray(vao);
        
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glEnableVertexAttribArray(4);
        
        glDrawElements(GL_TRIANGLES, numObjects * 6, GL_UNSIGNED_INT, 0);
        
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glDisableVertexAttribArray(4);
        glBindVertexArray(0);
        
        shader.disable();
    }
    
    @Override
    public void start() {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (long)vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);
        
        ebo = glGenBuffers();
        int[] indices = TextureUtils.generateIndices(maxBatchSize);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        
        glVertexAttribPointer(0, POSITION_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POSITION_OFFSET);
        glEnableVertexAttribArray(0);
        
        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);
        
        glVertexAttribPointer(2, TEXTURE_COORDINATES_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEXTURE_COORDINATES_OFFSET);
        glEnableVertexAttribArray(2);
        
        glVertexAttribPointer(3, ROTATION_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, ROTATION_OFFSET);
        glEnableVertexAttribArray(3);
        
        glVertexAttribPointer(4, ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, ID_OFFSET);
        glEnableVertexAttribArray(4);
    }
    
    @Override
    public boolean hasRoom(RenderObject check) {
        return numObjects < maxBatchSize;
    }
    
    @Override
    public void clean() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
    }
    
    public void addRenderObject(RenderObject o){
        if(hasRoom(o)){
            renderObjects[numObjects] = o;
            loadVertexProperties(numObjects);
            numObjects++;
        }
        for(int i = 0; i < vertices.length; i++){
            if(i % VERTEX_SIZE == 0){
                System.out.println("\b");
            }
            System.out.print(vertices[i] + ", ");
        }
    }
    
    private void loadVertexProperties(int index){
        RenderObject obj = renderObjects[index];
        int offset = index * 4 * VERTEX_SIZE;
        Vector4f color = obj.getColor();
        float[] texCoords = TextureUtils.getTextureDraw(obj.getDrawLocation());
        float xAdd = 0;
        float yAdd = 0;
        for(int i = 0; i < 4; i++){
            if(i == 1){
                yAdd = 1.0f;
            } else if(i == 2){
                xAdd = 1.0f;
            } else if(i == 3){
                yAdd = 0.0f;
            }
            Vector2f pos = new Vector2f(0.0f, 0.0f);
            Vector2f scale = new Vector2f(1.0f, 1.0f);
            vertices[offset] = pos.x + (scale.x * xAdd);
            vertices[offset + 1] = pos.y + (scale.y * yAdd);
            vertices[offset + 2] = zDraw;
            
            vertices[offset + 3] = color.x;
            vertices[offset + 4] = color.y;
            vertices[offset + 5] = color.w;
            vertices[offset + 6] = color.h;
            
            vertices[offset + 7] = texCoords[i * 2];
            vertices[offset + 8] = texCoords[(i * 2) + 1];
            
            vertices[offset + 9] = 0.0f;
            
            vertices[offset + 10] = obj.getTextureAtlasType().getId();
            
            offset += VERTEX_SIZE;
        }
    }
}
