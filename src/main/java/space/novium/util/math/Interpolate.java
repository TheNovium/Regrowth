package space.novium.util.math;

public final class Interpolate {
    public static float cubic(float t){
        return t * t * (3 - 2 * t);
    }
    
    public static float lerp(float start, float end, float time){
        return start + (end - start) * time;
    }
}
