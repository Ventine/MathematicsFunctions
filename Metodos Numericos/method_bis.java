import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;

public class BiseccionInteractiva {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Método de Bisección");
        System.out.println("Función fija: f(x) = x^3 - x - 2");

        System.out.print("Ingrese el límite inferior (a): ");
        double a = sc.nextDouble();

        System.out.print("Ingrese el límite superior (b): ");
        double b = sc.nextDouble();

        System.out.print("Ingrese la tolerancia (ej. 1e-6): ");
        double tol = sc.nextDouble();

        System.out.print("Ingrese el número máximo de iteraciones: ");
        int maxIter = sc.nextInt();

        DoubleUnaryOperator f = x -> Math.pow(x, 3) - x - 2; // Se puede reemplazar

        if (f.applyAsDouble(a) * f.applyAsDouble(b) >= 0) {
            System.out.println("Error: f(a) y f(b) deben tener signos opuestos.");
            return;
        }

        double raiz = biseccion(f, a, b, tol, maxIter);
        System.out.println("Raíz aproximada: " + raiz);
    }

    public static double biseccion(DoubleUnaryOperator f, double a, double b, double tol, int maxIter) {
        double c = a;
        int iter = 0;

        while ((b - a) / 2 > tol && iter < maxIter) {
            c = (a + b) / 2;
            double fc = f.applyAsDouble(c);

            if (fc == 0.0) break;
            else if (f.applyAsDouble(a) * fc < 0) b = c;
            else a = c;

            iter++;
        }

        System.out.printf("Iteraciones realizadas: %d%n", iter);
        return (a + b) / 2;
    }
}
