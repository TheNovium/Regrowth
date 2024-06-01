package space.novium.nebula.graphics;

import space.novium.util.math.Interpolate;
import space.novium.util.math.Matrix4f;
import space.novium.util.math.vector.Vector3f;

public class Camera {
    private Vector3f position;
    private float rotation;
    
    //Shader stuff
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    
    //Smooth movement stuff
    private Vector3f targetPos;
    private float smoothMoveTime = 0.0f;
    private float smoothMoveTimer = 0.0f;
    
    private float smoothZoomTime = 0.0f;
    private float smoothZoomTimer = 0.0f;
    private float targetZoomX = 1.0f;
    private float targetZoomY = 1.0f;
    private float zoomX = 1.0f;
    private float zoomY = 1.0f;
    
    public Camera(){
        this.position = new Vector3f(0, 0, 1);
        this.rotation = 0.0f;
        this.projectionMatrix = Matrix4f.standardOrthographic();
        this.viewMatrix = Matrix4f.identity();
        this.targetPos = position.copy();
    }
    
    private void updateViewMatrix(){
        viewMatrix = Matrix4f.identity();
        viewMatrix.translate(-position.x, -position.y, 0);
        viewMatrix.rotate(-rotation);
    }
    
    private void updateProjectionMatrix(){
        projectionMatrix = Matrix4f.orthographic(-zoomX, zoomX, -zoomY, zoomY, -1.0f, 1.0f);
    }
    
    public void zoom(float dz){
        zoom(dz, dz);
    }
    
    public void zoom(float dx, float dy){
        zoomX += dx;
        zoomY += dy;
        
        zoomX = Math.max(zoomX, 0.1f);
        zoomY = Math.max(zoomY, 0.1f);
        
        updateProjectionMatrix();
    }
    
    public void snapZoom(float x, float y){
        zoomX = Math.max(x, 0.1f);
        zoomY = Math.max(y, 0.1f);
        
        updateProjectionMatrix();
    }
    
    public void smoothZoomBy(float dx, float dy, float duration){
        targetZoomX += dx;
        targetZoomY += dy;
        smoothZoomTime = duration;
        smoothZoomTimer = 0.0f;
    }
    
    public void move(float dx, float dy){
        position.x += dx;
        position.y += dy;
        updateViewMatrix();
    }
    
    public void snapMove(float x, float y){
        position.x = x;
        position.y = y;
        updateViewMatrix();
    }
    
    public void smoothMove(float dx, float dy, float duration){
        smoothMoveTo(dx + position.x, dy + position.y, duration);
    }
    
    public void smoothMoveTo(float targetX, float targetY, float duration){
        targetPos.x = targetX;
        targetPos.y = targetY;
        smoothMoveTime = duration;
        smoothZoomTimer = 0.0f;
    }
    
    public void rotate(float angle){
        rotation += angle;
        updateViewMatrix();
    }
    
    public Matrix4f getViewMatrix(){
        return viewMatrix;
    }
    
    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }
    
    public void update(double dt){
        if(smoothMoveTimer < smoothMoveTime){
            smoothZoomTimer += dt;
            
            float progress = Math.min(smoothMoveTimer / smoothZoomTime, 1.0f);
            float t = Interpolate.cubic(progress);
            
            position.x = Interpolate.lerp(position.x, targetPos.x, t);
            position.y = Interpolate.lerp(position.y, targetPos.y, t);
            snapMove(position.x, position.y);
        }
        if(smoothZoomTimer < smoothZoomTime){
            smoothZoomTimer += dt;
            
            float progress = Math.min(smoothMoveTimer / smoothZoomTime, 1.0f);
            float t = Interpolate.cubic(progress);
            
            zoom((targetZoomX - zoomX) * t, (targetZoomY - zoomY) * t);
        }
    }
}
