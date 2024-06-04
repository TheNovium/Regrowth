package space.novium.world.entity;

import space.novium.util.math.vector.Vector2f;
import space.novium.util.math.vector.Vector4f;
import space.novium.world.data.Direction;
import space.novium.world.level.Level;
import space.novium.world.tile.TilePos;

import java.util.Random;
import java.util.UUID;

public class Entity {
    private Vector2f position;
    private Direction facing;
    private float speed;
    private Vector4f hitBox;
    
    private final UUID uuid = UUID.randomUUID();
    
    public Entity(Properties properties){
        this.position = properties.position;
        this.facing = properties.facing;
        this.speed = properties.speed;
        this.hitBox = properties.hitBox;
    }
    
    public void tick(Level level, Random random){}
    
    public TilePos getTilePos(){
        return new TilePos(position.x, position.y, 4);
    }
    
    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public Direction getFacing() {
        return facing;
    }
    
    public Vector4f getHitBox() {
        return hitBox;
    }
    
    public void move(float dx, float dy){
        position.add(dx, dy);
    }
    
    public void setFacing(Direction dir){
        facing = dir;
    }
    
    public static class Properties{
        Vector2f position = new Vector2f();
        Direction facing = Direction.NORTH;
        float speed = 0.15f;
        Vector4f hitBox = new Vector4f(1.0f);
        
        public Properties setHitBox(float x, float y, float w, float h){
            hitBox.x = x;
            hitBox.y = y;
            hitBox.w = w;
            hitBox.h = h;
            return this;
        }
        
        public Properties setSpeed(float speed){
            this.speed = Math.max(0, speed);
            return this;
        }
        
        public Properties setPosition(float x, float y){
            position.x = x;
            position.y = y;
            return this;
        }
    }
}
