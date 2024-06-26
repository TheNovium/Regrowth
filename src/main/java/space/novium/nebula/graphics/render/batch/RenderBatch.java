package space.novium.nebula.graphics.render.batch;

import space.novium.nebula.graphics.Camera;

public abstract class RenderBatch<T> implements Comparable<RenderBatch<T>> {
    protected int maxBatchSize;
    protected int zIndex;
    
    public RenderBatch(int maxBatchSize, int zIndex){
        this.maxBatchSize = maxBatchSize;
        this.zIndex = zIndex;
    }
    
    public abstract void render(Camera camera);
    
    public abstract void start();
    
    public abstract boolean hasRoom(T check);
    
    public int getzIndex(){
        return zIndex;
    }
    
    public abstract void clean();
    
    @Override
    public int compareTo(RenderBatch o) {
        return zIndex - o.getzIndex();
    }
}
