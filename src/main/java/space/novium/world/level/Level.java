package space.novium.world.level;

import space.novium.core.resources.registry.registration.GameTiles;
import space.novium.nebula.graphics.Camera;
import space.novium.nebula.graphics.render.Renderer;
import space.novium.world.entity.Player;
import space.novium.world.level.update.LevelUpdateListener;
import space.novium.world.level.update.TileUpdate;
import space.novium.world.tile.Tile;
import space.novium.world.tile.TilePos;

import java.util.*;
import java.util.function.Consumer;

public class Level {
    private Player player;
    private List<Tile> tiles;
    
    private final Random random = new Random();
    
    private final List<Consumer<LevelUpdateListener<?>>> levelListeners;
    private final Stack<LevelUpdateListener<?>> updates;
    
    public Level(){
        tiles = new ArrayList<>();
        levelListeners = new LinkedList<>();
        updates = new Stack<>();
        
        //TODO change this to load from a file eventually, for now just
        generateLevel();
    }
    
    public void addUpdateListener(Consumer<LevelUpdateListener<?>> listener){
        levelListeners.add(listener);
    }
    
    public void addTile(Tile tile){
        TilePos pos = tile.getPos();
        updates.add(new TileUpdate(tile));
    }
    
    public void tick(){
        while(!updates.isEmpty()){
            LevelUpdateListener<?> listener = updates.pop();
            for(Consumer<LevelUpdateListener<?>> consumer : levelListeners){
                consumer.accept(listener);
            }
        }
    }
    
    private void generateLevel(){
        addTile(GameTiles.GRASS.get());
    }
}
