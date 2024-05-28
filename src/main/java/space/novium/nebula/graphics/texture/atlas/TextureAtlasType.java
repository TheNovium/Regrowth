package space.novium.nebula.graphics.texture.atlas;

import static org.lwjgl.opengl.GL13.*;

public enum TextureAtlasType {
    BLANK("blank"),
    BACKGROUND("background"),
    ENTITY("entities"),
    ITEMS("items"),
    NONE("no_atlas"),
    TEXT("text"),
    TILES("tiles"),
    UI("user_interface");
    
    private final String description;
    private final int glTexture;
    
    TextureAtlasType(String description){
        this.description = description;
        this.glTexture = GL_TEXTURE0 + this.ordinal(); //This should automatically assign a unique GL_TEXTURE to each atlas type
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getGlTexture(){
        return glTexture;
    }
    
    public int getId(){
        return glTexture - GL_TEXTURE0;
    }
}
