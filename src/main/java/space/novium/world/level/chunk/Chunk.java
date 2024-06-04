package space.novium.world.level.chunk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import space.novium.core.resources.ResourceLocation;
import space.novium.core.resources.registry.Registries;
import space.novium.core.resources.registry.registration.GameTiles;
import space.novium.util.IOUtils;
import space.novium.util.StringUtils;
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
     * CHUNK_DEPTH: i & 0b111
     * CHUNK_HEIGHT: (i << 3) & 0b1111
     * CHUNK_WIDTH: i << 7
     * **/
    
    private Vector2i chunkPos;
    
    private Tile[] tiles;
    
    private Chunk(int x, int y){
        tiles = new Tile[CHUNK_WIDTH * CHUNK_HEIGHT * CHUNK_DEPTH];
        chunkPos = new Vector2i(x, y);
    }
    
    public int getXOffset(){
        return chunkPos.x * CHUNK_WIDTH;
    }
    
    public int getYOffset(){
        return chunkPos.y * CHUNK_HEIGHT;
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
                for(int z = 0; (z < tiles.size() && z < CHUNK_DEPTH); z++){
                    JsonArray layer = tiles.get(z).getAsJsonArray();
                    for(int l = 0; (l < layer.size() && l < CHUNK_HEIGHT); l++){
                        String row = layer.get(l).getAsString();
                        for(int c = 0; (c < row.length() && c < CHUNK_WIDTH); c++){
                            if(row.charAt(c) == ' ') continue;
                            ResourceLocation tileLoc = tileLocs.getOrDefault(row.charAt(c), DEFAULT_TILE);
                            Tile t = Registries.TILE_REGISTRY.getValue(tileLoc);
                            if(t == null){
                                t = Registries.TILE_REGISTRY.getValue(DEFAULT_TILE);
                            }
                            t = t.clone();
                            t.setRegistryName(tileLoc);
                            t.setPos(c + loading.getXOffset(), (CHUNK_HEIGHT - l) + loading.getYOffset(), z);
                            level.addTile(t);
                        }
                    }
                }
            }
        }, () -> loading.generateNewChunkData(level, random));
        return loading;
    }
    
    private void generateNewChunkData(Level level, Random random){
        for(int i = 0; i < CHUNK_WIDTH * CHUNK_HEIGHT; i++){
            Tile t = random.nextFloat() < 0.8f ? GameTiles.GRASS.get().clone() : GameTiles.SAND.get().clone();
            t.setPos((i % CHUNK_WIDTH) + (chunkPos.x * CHUNK_WIDTH), (i / CHUNK_WIDTH) + (chunkPos.y * CHUNK_HEIGHT));
            tiles[i << 3] = t;
            level.addTile(t);
        }
        saveData();
    }
    
    
    public void saveData(){
        StringUtils.CharCounter charCounter = new StringUtils.CharCounter();
        Map<ResourceLocation, Character> charMap = new HashMap<>();
        JsonObject obj = new JsonObject();
        JsonObject define = new JsonObject();
        JsonObject tilesDefine = new JsonObject();
        JsonArray tileData = new JsonArray(CHUNK_DEPTH);
        for(int i = 0; i < CHUNK_DEPTH; i++){
            JsonArray tempArr = new JsonArray(CHUNK_HEIGHT);
            for(int j = 0; j < CHUNK_HEIGHT; j++){
                tempArr.add("                        ");
            }
            tileData.add(tempArr);
        }
        for(int i = 0; i < tiles.length; i++){
            if(tiles[i] != null){
                Tile t = tiles[i];
                charMap.computeIfAbsent(t.getRegistryName(), (s) -> {
                    char nextChar = charCounter.getNextChar();
                    tilesDefine.add(String.valueOf(nextChar), new JsonPrimitive(t.getRegistryName().toString()));
                    return nextChar;
                });
                char c = charMap.get(t.getRegistryName());
                String s = tileData.get(i & 0b111)
                        .getAsJsonArray().get((i >> 3) & 0b1111).getAsString();
                s = s.substring(0, (i >> 7)) + c + s.substring((i >> 7) + 1);
                tileData.get(i & 0b111).getAsJsonArray().set((i >> 3) & 0b1111, new JsonPrimitive(s));
            }
        }
        define.add("tile", tilesDefine);
        obj.add("define", define);
        obj.add("tiles", tileData);
        IOUtils.saveChunkData(chunkPos.x + "x" + chunkPos.y, obj);
    }
}
