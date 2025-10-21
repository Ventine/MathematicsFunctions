import java.util.Scanner;

public class DerivadaLogaritmo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la función logarítmica (ej: ln(x) o log(3, x)): ");
        String input = sc.nextLine().trim();
        sc.close();

        String derivada = derivarLogaritmo(input);
        System.out.println("Derivada: " + derivada);
    }

    private static String derivarLogaritmo(String funcion) {
        funcion = funcion.replaceAll("\\s+", "").toLowerCase();

        if (funcion.startsWith("ln(") && funcion.endsWith(")")) {
            String argumento = funcion.substring(3, funcion.length() - 1);
            return "1/(" + argumento + ")";
        }

        if (funcion.startsWith("log(") && funcion.endsWith(")")) {
            String contenido = funcion.substring(4, funcion.length() - 1);
            String[] partes = contenido.split(",");
            if (partes.length == 2) {
                String base = partes[0];
                String argumento = partes[1];
                return "1/(" + argumento + " * ln(" + base + "))";
            }
        }

        return "Error: formato no reconocido. Use ln(x) o log(base, x).";
    }
}
