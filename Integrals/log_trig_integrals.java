import java.util.Scanner;

public class Integrador {

    static double integrar(Funcion f, double a, double b, int n) {
        double h = (b - a) / n;
        double suma = 0.5 * (f.evaluar(a) + f.evaluar(b));
        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            suma += f.evaluar(x);
        }
        return suma * h;
    }

    interface Funcion {
        double evaluar(double x);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el tipo de función (trig o log):");
        String tipo = sc.nextLine().trim().toLowerCase();

        System.out.println("Ingrese límites de integración a y b:");
        double a = sc.nextDouble();
        double b = sc.nextDouble();

        Funcion f;

        if (tipo.equals("trig")) {
            System.out.println("Ingrese tipo trigonométrica (sin, cos, tan):");
            String op = sc.next().trim().toLowerCase();
            switch (op) {
                case "sin" -> f = x -> Math.sin(x);
                case "cos" -> f = x -> Math.cos(x);
                case "tan" -> f = x -> Math.tan(x);
                default -> {
                    System.out.println("Función desconocida.");
                    return;
                }
            }
        } else if (tipo.equals("log")) {
            f = x -> Math.log(x); // ln(x)
        } else {
            System.out.println("Tipo no válido.");
            return;
        }

        double resultado = integrar(f, a, b, 100000);
        System.out.println("Integral aproximada = " + resultado);
    }
}
