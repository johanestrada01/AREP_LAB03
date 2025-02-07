package Lab03;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MicroServer {

    public static Map<String, Method> services = new HashMap();

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        loadComponents(args);
        System.out.println(simulateRequests("/greeting"));
        System.out.println(simulateRequests("/pi"));
    }

    private static String simulateRequests(String route) throws InvocationTargetException, IllegalAccessException {
        Method m = services.get(route);
        String response = "\"HTTP/1.1 200 OK\\r\\n\"\n"
                                        + "Content-Type: application/json\\r\\n\"\n"
                                        + "\r\n"
                                        + "{\"resp\":\""+ m.invoke(null, "Pedro") +"\"}";
        return response;
    }

    private static void loadComponents(String[] args) throws ClassNotFoundException {
        Class c = Class.forName(args[0]);
        if(!c.isAnnotationPresent(RestController.class)){
            System.exit(0);
        }
        for(Method method : c.getDeclaredMethods()){
            if(method.isAnnotationPresent(GetMapping.class)) {
                GetMapping a = method.getAnnotation(GetMapping.class);
                services.put(a.value(), method);
            }
        }

    }
}
 //java -cp target/classes Lab03.MicroServer Lab03.GreetingController