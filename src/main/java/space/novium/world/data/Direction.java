package space.novium.world.data;

import space.novium.impl.annotation.Stringable;
import space.novium.world.tile.TilePos;

@Stringable(value = "direction", defaultEntry = "SOUTH")
public enum Direction {
    NORTH("North", 0, 1),
    EAST("East", 1, 0),
    SOUTH("South", 0, -1),
    WEST("West", -1, 0);
    
    private final String value;
    private final int dirX;
    private final int dirY;
    
    Direction(String value, int x, int y){
        this.value = value;
        this.dirX = x;
        this.dirY = y;
    }
    
    public String getValue() {
        return value;
    }
    
    public int getDirX(){
        return dirX;
    }
    
    public int getDirY(){
        return dirY;
    }
    
    public Direction getClockwise(){
        return values()[(ordinal() + 1) % 4];
    }
    
    public Direction getCounterClockwise(){
        return values()[(ordinal() + 3) % 4];
    }
    
    public TilePos getDirection(TilePos pos){
        return getDirection(pos, 1);
    }
    
    public TilePos getDirection(TilePos pos, int length){
        return new TilePos(pos.getX() + (length * dirX), pos.getY() + (length * dirY), pos.getZ());
    }
    
    public static Direction fromString(String text){
        for(Direction d : Direction.values()){
            if(d.value.equalsIgnoreCase(text)){
                return d;
            }
        }
        return Direction.valueOf(Direction.class.getAnnotation(Stringable.class).defaultEntry());
    }
}
