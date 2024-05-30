package space.novium.core.resources.annotation;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import space.novium.core.event.EventListener;
import space.novium.core.event.EventType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationHandler {
    private static AnnotationHandler instance;
    private static boolean loaded = false;
    private static Map<EventType, Set<Method>> events;
    
    private AnnotationHandler(){}
    
    public static AnnotationHandler get(){
        if(instance == null){
            instance = new AnnotationHandler();
            events = instance.collectEvents();
            loaded = true;
        }
        return instance;
    }
    
    public static void runEvent(EventType eventType, Object... args){
        if(loaded){
            if(events.containsKey(eventType)){
                events.get(eventType).forEach(method -> {
                    try {
                        if(method.getAnnotation(EventListener.class).args()){
                            method.invoke(null, args);
                        } else {
                            method.invoke(null, (Object) null);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e){
                        System.out.println("Failed to run event!");
                        e.printStackTrace();
                    }
                });
            } else {
                System.err.println("No methods annotated with event!");
            }
        } else {
            throw new IllegalStateException("Cannot run events without initializing the annotation handler!");
        }
    }
    
    private Map<EventType, Set<Method>> collectEvents(){
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages("").setScanners(Scanners.MethodsAnnotated));
        Set<Method> annotatedMethods = reflections.getMethodsAnnotatedWith(EventListener.class);
        
        if(annotatedMethods.size() == 0){
            System.out.println("There are no annotated methods.");
        }
        
        Map<EventType, Set<Method>> methods = new HashMap<>();
        
        for(Method method : annotatedMethods){
            EventListener annotation = method.getAnnotation(EventListener.class);
            EventType eventType = annotation.event();
            
            methods.computeIfAbsent(eventType, e -> new HashSet<>()).add(method);
        }
        
        return methods;
    }
}
