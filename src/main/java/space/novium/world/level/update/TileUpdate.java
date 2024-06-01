package space.novium.world.level.update;

import space.novium.nebula.graphics.render.Renderer;
import space.novium.nebula.graphics.render.component.RenderObject;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasHandler;
import space.novium.nebula.graphics.texture.atlas.TextureAtlasType;
import space.novium.world.tile.Tile;

public class TileUpdate extends LevelUpdateListener<Tile> {
    private Tile tile;
    
    public TileUpdate(Tile tile) {
        this.tile = tile;
    }
    
    @Override
    public void handleRender(Renderer renderer, TextureAtlasHandler handler) {
        RenderObject obj = handler.getRendererForResourceLocation(tile.getTileType(), TextureAtlasType.TILES);
        renderer.add(obj);
    }
    
    @Override
    public Tile getObject() {
        return tile;
    }
}
