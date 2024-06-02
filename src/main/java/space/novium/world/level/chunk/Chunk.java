package space.novium.world.level.chunk;

import space.novium.core.resources.ResourceLocation;
import space.novium.core.resources.registry.registration.GameTiles;
import space.novium.util.IOUtils;
import space.novium.util.math.vector.Vector2i;
import space.novium.world.level.Level;
import space.novium.world.tile.Tile;

import java.util.HashMap;
import java.util.Map;

public class Chunk {
    public static final int CHUNK_WIDTH = 24;
    public static final int CHUNK_HEIGHT = 16;
    public static final int CHUNK_DEPTH = 8;
    /**
     * Chunk indices can be represented as an in by using
     * CHUNK_WIDTH << 7 | CHUNK_HEIGHT << 3 | CHUNK_DEPTH
     * **/
    
    private Vector2i chunkPos;
    
    private Tile[] tiles;
    
    private Chunk(int x, int y){
        tiles = new Tile[CHUNK_WIDTH * CHUNK_HEIGHT * CHUNK_DEPTH];
        chunkPos = new Vector2i(x, y);
    }
    
    public static Chunk loadRegion(int x, int y, Level level){
        Chunk loading = new Chunk(x, y);
        IOUtils.loadChunkData(x + "x" + y).ifPresentOrElse((obj) -> {
            Map<Character, ResourceLocation> resources = new HashMap<>();
            
        }, () -> loading.generateNewChunkData(level));
        return new Chunk(x, y);
    }
    
    private void generateNewChunkData(Level level){
        for(int i = 0; i < CHUNK_WIDTH * CHUNK_HEIGHT; i++){
            Tile t = GameTiles.GRASS.get().clone();
            t.setPos((i % CHUNK_WIDTH) + (chunkPos.x * CHUNK_WIDTH), (i / CHUNK_WIDTH) + (chunkPos.y * CHUNK_HEIGHT));
            tiles[i << 3] = t;
            level.addTile(t);
        }
    }
    
    
    public void saveData(){}
}
