package space.novium.world.tile;

import space.novium.core.resources.ResourceLocation;
import space.novium.util.math.vector.Vector4f;
import space.novium.world.entity.Player;
import space.novium.world.level.Level;

import java.util.Objects;
import java.util.Random;

public class Tile {
    private final TilePos pos;
    private ResourceLocation registryName;
    
    public Tile(){
        this(TilePos.ORIGIN.copy());
    }
    
    public Tile(TilePos pos){
        this.pos = pos;
    }
    
    public TilePos getPos() {
        return pos;
    }
    
    public void setPos(int x, int y){
        pos.setPosition(x, y);
    }
    
    public void setPos(int x, int y, int z){
        pos.setPosition(x, y, z);
    }
    
    public boolean ticks(){
        return false;
    }
    
    public void tick(Level level, Player player, Random random){}
    
    public ResourceLocation getRegistryName() {
        return registryName;
    }
    
    public void setRegistryName(ResourceLocation loc){
        if(getRegistryName() != null){
            System.err.println("Unable to set registry name to " + registryName + " because tile is already registered as " + getRegistryName());
        } else {
            this.registryName = loc;
        }
    }
    
    public boolean collide(){
        return false;
    }
    
    public Vector4f getHitBox(){
        return new Vector4f();
    }
    
    public Tile clone(){
        Tile ret = new Tile(getPos().copy());
        ret.setRegistryName(getRegistryName());
        return ret;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(pos.getX() % 32, pos.getY() % 24, pos.getZ());
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tile tile){
            return tile.getPos().compareTo(getPos()) == 0 && tile.getRegistryName().equals(getRegistryName());
        }
        return false;
    }
    
    @Override
    public String toString(){
        return getRegistryName() + " @" + getPos().toString();
    }
}
