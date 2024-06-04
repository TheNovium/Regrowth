package space.novium.util;

public class StringUtils {
    public static boolean isEmpty(final CharSequence chars){
        return chars == null || chars.length() == 0;
    }
    
    public static class CharCounter{
        private char c;
        
        public CharCounter(){
            c = 34;
        }
        
        public char getNextChar(){
            c++;
            if(c == 127) c++;
            return c;
        }
    }
}
