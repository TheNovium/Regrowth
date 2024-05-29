package space.novium.core.resources;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.novium.Regrowth;
import space.novium.util.StringUtils;

import java.util.Objects;

public class ResourceLocation implements Comparable<ResourceLocation> {
    public static final char NAMESPACE_SEPARATOR = ':';
    
    protected final String namespace;
    protected final String path;
    
    protected ResourceLocation(String[] locations){
        this.namespace = StringUtils.isEmpty(locations[0]) ? Regrowth.NAMESPACE : locations[0];
        this.path = locations[1];
    }
    
    public boolean isValidNamespace(String space){
        for(byte b : space.getBytes()){
            if(!isValidNamespaceChar((char) b)){
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValidNamespaceChar(char c){
        return c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.';
    }
    
    public boolean isValidPath(String path){
        for(byte b : path.getBytes()){
            if(!isValidPathChar((char) b)){
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValidPathChar(char c){
        return isValidNamespaceChar(c) || c == '/';
    }
    
    protected static String[] decompose(String s, char c){
        String[] temp = new String[]{Regrowth.NAMESPACE, s};
        int i = s.indexOf(c);
        if(i > 0){
            temp[1] = s.substring(i + 1);
            if(i > 1){
                temp[0] = s.substring(0, i);
            }
        }
        return temp;
    }
    
    public String getNamespace(){
        return namespace;
    }
    
    public String getPath(){
        return path;
    }
    
    public ResourceLocation(String loc){
        this(decompose(loc, NAMESPACE_SEPARATOR));
    }
    
    public ResourceLocation(@Nullable String name, String path){
        this(new String[]{name, path});
    }
    
    /**
     * @return 0 if they are the same, -1 if namespace is different, 1 if namespace is the same but the path is different
     * **/
    @Override
    public int compareTo(@NotNull ResourceLocation o) {
        if(namespace.equals(o.getNamespace())){
            if(path.equals(o.getPath())){
                return 0;
            }
            return 1;
        }
        return -1;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ResourceLocation loc){
            return compareTo(loc) == 0;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return getNamespace() + NAMESPACE_SEPARATOR + getPath();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }
}