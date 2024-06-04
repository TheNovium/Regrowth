package space.novium.world.level.chunk;

import space.novium.util.math.vector.Vector2i;
import space.novium.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChunkLoader {
    private Map<Vector2i, Chunk> loadedChunks;
    private Level level;
    
    public ChunkLoader(Level level, Random tileGen){
        loadedChunks = new HashMap<>();
        this.level = level;
        for(int i = -1; i < 1; i++){
            for(int j = -1; j < 1; j++){
                loadedChunks.put(new Vector2i(i, j), Chunk.loadRegion(i, j, level, tileGen));
            }
        }
    }
}
