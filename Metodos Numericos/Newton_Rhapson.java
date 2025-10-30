import java.util.function.DoubleUnaryOperator;

public final class NewtonRaphson {

    /**
     * Ejecuta Newton-Raphson para encontrar una raíz real de f(x).
     *
     * @param f           función objetivo f(x)
     * @param fPrime      derivada f'(x)
     * @param x0          aproximación inicial
     * @param tol         tolerancia absoluta para |f(x)| y |x_{n+1}-x_n|
     * @param maxIter     número máximo de iteraciones
     * @return            raíz encontrada
     * @throws IllegalArgumentException si entrada inválida
     * @throws IllegalStateException    si no converge o derivada cero en iteración
     */
    public static double solve(DoubleUnaryOperator f,
                               DoubleUnaryOperator fPrime,
                               double x0,
                               double tol,
                               int maxIter) {
        if (f == null || fPrime == null) throw new IllegalArgumentException("Funciones no pueden ser null");
        if (tol <= 0) throw new IllegalArgumentException("Tolerancia debe ser > 0");
        if (maxIter <= 0) throw new IllegalArgumentException("maxIter debe ser > 0");

        double x = x0;

        for (int i = 1; i <= maxIter; i++) {
            double fx = f.applyAsDouble(x);
            double fpx = fPrime.applyAsDouble(x);

            if (Double.isNaN(fx) || Double.isNaN(fpx))
                throw new IllegalStateException("Evaluación produjo NaN");

            if (Math.abs(fx) <= tol) return x;

            if (fpx == 0.0) throw new IllegalStateException("Derivada nula en iteración " + i + ", x=" + x);

            double xNext = x - fx / fpx;

            if (Double.isNaN(xNext) || Double.isInfinite(xNext))
                throw new IllegalStateException("Iteración produjo valor inválido en iteración " + i);

            if (Math.abs(xNext - x) <= tol) return xNext;

            x = xNext;
        }

        throw new IllegalStateException("No convergió en " + maxIter + " iteraciones");
    }

    // Ejemplo de uso y prueba rápida
    public static void main(String[] args) {
        // Ejemplo: f(x) = x^3 - 2x - 5  (raíz real cerca de 2.094551)
        DoubleUnaryOperator f = x -> x * x * x - 2 * x - 5;
        DoubleUnaryOperator fPrime = x -> 3 * x * x - 2;

        double inicial = 2.0;
        double tol = 1e-12;
        int maxIter = 100;

        try {
            double raiz = solve(f, fPrime, inicial, tol, maxIter);
            System.out.printf("Raíz encontrada: %.15f%n", raiz);
            System.out.printf("f(raiz) = %.3e%n", f.applyAsDouble(raiz));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
