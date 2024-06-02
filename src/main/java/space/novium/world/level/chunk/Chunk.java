package space.novium.world.level.chunk;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import space.novium.core.resources.ResourceLocation;
import space.novium.core.resources.registry.Registries;
import space.novium.core.resources.registry.registration.GameTiles;
import space.novium.util.IOUtils;
import space.novium.util.math.vector.Vector2i;
import space.novium.world.level.Level;
import space.novium.world.tile.Tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Chunk {
    private static final ResourceLocation DEFAULT_TILE = GameTiles.GRASS.get().getRegistryName();
    
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
    
    public static Chunk loadRegion(int x, int y, Level level, Random random){
        Chunk loading = new Chunk(x, y);
        IOUtils.loadChunkData(x + "x" + y).ifPresentOrElse((obj) -> {
            Map<Character, ResourceLocation> tileLocs = new HashMap<>();
            if(obj.has("define")){
                JsonObject definitions = obj.get("define").getAsJsonObject();
                if(definitions.has("tile")){
                    JsonObject tileDefinitions = definitions.get("tile").getAsJsonObject();
                    for(String s : tileDefinitions.keySet()){
                        char c = s.charAt(0);
                        tileLocs.put(c, new ResourceLocation(tileDefinitions.get(s).getAsString()));
                    }
                }
            }
            if(obj.has("tiles")){
                JsonArray tiles =  obj.getAsJsonArray("tiles");
                for(int z = 0; z < tiles.size(); z++){
                    JsonArray layer = tiles.get(z).getAsJsonArray();
                    for(int l = 0; l < layer.size(); l++){
                        String row = layer.get(l).getAsString();
                        for(int c = 0; c < row.length(); c++){
                            ResourceLocation tileLoc = tileLocs.getOrDefault(row.charAt(c), DEFAULT_TILE);
                            Tile t = Registries.TILE_REGISTRY.getValue(tileLoc);
                            if(t == null){
                                t = Registries.TILE_REGISTRY.getValue(DEFAULT_TILE);
                            }
                            t = t.clone();
                            t.setRegistryName(tileLoc);
                            t.setPos(c, l, z);
                            level.addTile(t);
                        }
                    }
                }
            }
            System.out.println(tileLocs);
        }, () -> loading.generateNewChunkData(level, random));
        return new Chunk(x, y);
    }
    
    private void generateNewChunkData(Level level, Random random){
        for(int i = 0; i < CHUNK_WIDTH * CHUNK_HEIGHT; i++){
            Tile t = random.nextFloat() < 0.8f ? GameTiles.GRASS.get().clone() : GameTiles.SAND.get().clone();
            t.setPos((i % CHUNK_WIDTH) + (chunkPos.x * CHUNK_WIDTH), (i / CHUNK_WIDTH) + (chunkPos.y * CHUNK_HEIGHT));
            tiles[i << 3] = t;
            level.addTile(t);
        }
    }
    
    
    public void saveData(){}
}
