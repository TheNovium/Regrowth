package space.novium.world.tile;

import org.jetbrains.annotations.NotNull;
import space.novium.world.data.Direction;

import java.util.Objects;

public class TilePos implements Comparable<TilePos> {
    private int x, y, z;
    
    public static final TilePos ORIGIN = new TilePos(0, 0, 0);
    
    public TilePos(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public TilePos(double x, double y, double z){
        this((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z));
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getZ(){
        return z;
    }
    
    public TilePos getDirection(Direction dir){
        return getDirection(dir, 1);
    }
    
    public TilePos getDirection(Direction dir, int length){
        return dir.getDirection(this, length);
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setPosition(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public TilePos copy(){
        return new TilePos(x, y, z);
    }
    
    @Override
    public int compareTo(@NotNull TilePos o) {
        return Math.abs(x - o.getX()) + Math.abs(y - o.getY()) + Math.abs(z - o.getZ());
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TilePos other){
            return x == other.getX() && y == other.getY() && z == other.getZ();
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "TilePos: (" + getX() + ", " + getY() + ", " + getZ() + ")";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
