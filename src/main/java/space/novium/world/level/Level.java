package space.novium.world.level;

import space.novium.world.entity.Player;
import space.novium.world.level.chunk.ChunkLoader;
import space.novium.world.level.update.LevelUpdateListener;
import space.novium.world.level.update.TileUpdate;
import space.novium.world.tile.Tile;

import java.util.*;
import java.util.function.Consumer;

public class Level {
    private Player player;
    private List<Tile> tiles;
    
    private final Random levelRandom = new Random();
    private final Random dataRandom = new Random();
    
    private final List<Consumer<LevelUpdateListener<?>>> levelListeners;
    private final Stack<LevelUpdateListener<?>> updates;
    
    private ChunkLoader chunkLoader;
    
    public Level(){
        tiles = new ArrayList<>();
        levelListeners = new LinkedList<>();
        updates = new Stack<>();
        chunkLoader = new ChunkLoader(this, levelRandom);
    }
    
    public void addUpdateListener(Consumer<LevelUpdateListener<?>> listener){
        levelListeners.add(listener);
    }
    
    public void addTile(Tile tile){
        updates.add(new TileUpdate(tile));
    }
    
    public void tick(){
        while(!updates.isEmpty()){
            LevelUpdateListener<?> listener = updates.pop();
            for(Consumer<LevelUpdateListener<?>> consumer : levelListeners){
                consumer.accept(listener);
            }
        }
        chunkLoader.tick();
    }
}
