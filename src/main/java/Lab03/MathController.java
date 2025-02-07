package Lab03;

@RestController
public class MathController {

    @GetMapping("/e")
    public static String e(String basura){
        return Double.toString(Math.E);
    }

}
