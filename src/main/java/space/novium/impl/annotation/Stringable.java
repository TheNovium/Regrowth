package space.novium.impl.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Stringable {
    String defaultEntry();
    
    final class StringableAnnotationChecker {
        public static String getDefaultEntry(Class<? extends Enum<?>> enumClass){
            Stringable annotation = enumClass.getAnnotation(Stringable.class);
            if(annotation != null){
                return annotation.defaultEntry();
            } else {
                throw new IllegalArgumentException("Enum " + enumClass.getSimpleName() + " does not have the @Stringable annotation");
            }
        }
        
        public static Boolean implementsStringable(Class<? extends Enum<?>> enumClass){
            return enumClass.getAnnotation(Stringable.class) != null;
        }
    }
}