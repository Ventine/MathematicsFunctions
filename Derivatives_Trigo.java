// Java 21
// Programa: Derivador de funciones trigonométricas
// Autor: [Tu nombre o alias]
// Descripción: Calcula la derivada simbólica de funciones trigonométricas simples digitadas por el usuario.
//               Soporta: sin(x), cos(x), tan(x), cot(x), sec(x), csc(x)
//               Solo maneja derivadas básicas de primer orden.

import java.util.*;
import java.util.function.Function;

public class DerivadorTrigonometrico {

    // Mapa de funciones trigonométricas y sus derivadas
    private static final Map<String, Function<String, String>> DERIVADAS = new HashMap<>();

    static {
        DERIVADAS.put("sin", f -> "cos(" + f + ")");
        DERIVADAS.put("cos", f -> "-sin(" + f + ")");
        DERIVADAS.put("tan", f -> "sec(" + f + ")^2");
        DERIVADAS.put("cot", f -> "-csc(" + f + ")^2");
        DERIVADAS.put("sec", f -> "sec(" + f + ")*tan(" + f + ")");
        DERIVADAS.put("csc", f -> "-csc(" + f + ")*cot(" + f + ")");
    }

    // Método principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Derivador de funciones trigonométricas ===");
        System.out.print("Ingrese una función (ejemplo: sin(x), cos(x), tan(3x)): ");
        String input = sc.nextLine().trim().toLowerCase();

        try {
            String derivada = derivar(input);
            System.out.println("Derivada: " + derivada);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    // Método para derivar funciones trigonométricas simples
    private static String derivar(String funcion) {
        for (String clave : DERIVADAS.keySet()) {
            if (funcion.startsWith(clave)) {
                String argumento = extraerArgumento(funcion);
                if (!argumento.equals("x")) {
                    // Regla de la cadena
                    return "(" + DERIVADAS.get(clave).apply(argumento) + ")*(" + derivarArgumento(argumento) + ")";
                }
                return DERIVADAS.get(clave).apply(argumento);
            }
        }
        throw new IllegalArgumentException("Función no reconocida o formato inválido.");
    }

    // Extrae el contenido entre paréntesis
    private static String extraerArgumento(String funcion) {
        int inicio = funcion.indexOf('(');
        int fin = funcion.lastIndexOf(')');
        if (inicio == -1 || fin == -1 || fin <= inicio) {
            throw new IllegalArgumentException("Formato de función incorrecto.");
        }
        return funcion.substring(inicio + 1, fin).trim();
    }

    // Derivación simbólica básica de argumentos lineales: ej. 3x -> 3, 5x -> 5
    private static String derivarArgumento(String argumento) {
        if (argumento.equals("x")) return "1";
        if (argumento.matches("-?\\d*\\.?\\d*x")) {
            String coef = argumento.replace("x", "");
            if (coef.isEmpty() || coef.equals("+")) return "1";
            if (coef.equals("-")) return "-1";
            return coef;
        }
        throw new IllegalArgumentException("Argumento no lineal o no soportado.");
    }


}
