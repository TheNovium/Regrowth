package space.novium.util.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public final class BufferUtils {
    private BufferUtils(){}
    
    public static IntBuffer createIntBuffer(int[] arr){
        IntBuffer result = ByteBuffer.allocateDirect(arr.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(arr).flip();
        return result;
    }
}
