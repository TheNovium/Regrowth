package space.novium.util;

public class StringUtils {
    public static boolean isEmpty(final CharSequence chars){
        return chars == null || chars.length() == 0;
    }
}
