package space.novium.util;

import space.novium.core.resources.ResourceLocation;

import java.awt.image.BufferedImage;

public final class TextureUtils {
    public static final ResourceLocation NO_TEXTURE_LOCATION = new ResourceLocation("no_texture");
    public static final BufferedImage NO_TEXTURE = IOUtils.loadImage(NO_TEXTURE_LOCATION).orElseThrow(() -> new RuntimeException("Cannot initialize without empty image"));
}
