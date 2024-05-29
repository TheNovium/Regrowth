package space.novium.world.data;

import space.novium.impl.annotation.Stringable;

@Stringable(value = "direction", defaultEntry = "SOUTH")
public enum Direction {
    NORTH("North"),
    EAST("East"),
    SOUTH("South"),
    WEST("West");
    
    private final String value;
    
    Direction(String value){
        this.value = value;
    }
    
    public String getValue() {
        return value;
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
