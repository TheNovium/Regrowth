package space.novium.world.level;

import space.novium.world.entity.Player;
import space.novium.world.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private Player player;
    private List<Tile> tiles;
    
    private final Random random = new Random();
    
    public Level(){
        tiles = new ArrayList<>();
        //TODO change this to load from a file eventually, for now just
        
    }
    
    private void generateLevel(){}
}
