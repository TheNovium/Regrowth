package space.novium.util;

import java.awt.image.BufferedImage;

public final class TextureUtils {
    public static final BufferedImage noImage = IOUtils.loadImage("no_texture").orElseThrow(() -> new RuntimeException("Cannot initialize without empty image"));
}
