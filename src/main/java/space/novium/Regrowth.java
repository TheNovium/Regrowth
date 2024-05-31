package space.novium;

import space.novium.nebula.Window;

public class Regrowth {
    public static final String NAMESPACE = "regrowth";
    
    private static Window window;
    
    public static void main(String[] args) {
        window = Window.get();
        window.start();
    }
}