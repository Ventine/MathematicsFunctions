import java.util.Scanner;

public class RaicesPolinomio {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el grado del polinomio:");
        int n = sc.nextInt();
        double[] coef = new double[n + 1];

        System.out.println("Ingrese los coeficientes desde el término de mayor grado hasta el término independiente:");
        for (int i = 0; i <= n; i++) {
            coef[i] = sc.nextDouble();
        }

        System.out.println("Buscando raíces reales aproximadas...");
        buscarRaices(coef, -1000, 1000, 0.001);
    }

    static double evaluar(double[] coef, double x) {
        double r = 0;
        for (double c : coef) {
            r = r * x + c;
        }
        return r;
    }

    static void buscarRaices(double[] coef, double inicio, double fin, double paso) {
        double x1 = inicio;
        double y1 = evaluar(coef, x1);

        for (double x2 = inicio + paso; x2 <= fin; x2 += paso) {
            double y2 = evaluar(coef, x2);
            if (y1 * y2 <= 0) {
                double raiz = biseccion(coef, x1, x2, 1e-6);
                System.out.printf("Raíz aproximada: %.6f%n", raiz);
            }
            x1 = x2;
            y1 = y2;
        }
    }

    static double biseccion(double[] coef, double a, double b, double tol) {
        double fa = evaluar(coef, a);
        double fb = evaluar(coef, b);
        if (fa * fb > 0) return Double.NaN;

        double m = 0;
        while (Math.abs(b - a) > tol) {
            m = (a + b) / 2;
            double fm = evaluar(coef, m);
            if (fa * fm <= 0) b = m;
            else {
                a = m;
                fa = fm;
            }
        }
        return m;
    }
}
