/**
 * IntegrationByParts.java
 *
 * Implementa la integración por partes simbólica usando el principio:
 * ∫ u dv = u*v - ∫ v du
 *
 * Esta versión usa funciones representadas como expresiones simbólicas
 * simplificadas y derivación/integración genérica sobre polinomios básicos.
 * Ejemplo: ∫ x * e^x dx  →  x*e^x - ∫ e^x dx = x*e^x - e^x + C
 *
 * Requiere Java 21 o superior.
 */
public class IntegrationByParts {

    /**
     * Representa una función matemática simbólica simple.
     * Soporta polinomios y exponenciales para demostración.
     */
    public sealed interface Function
            permits Polynomial, Exponential {
        double evaluate(double x);
        Function derivative();
        Function integral();
        String expression();
    }

    /** Representa f(x) = a * x^n */
    public record Polynomial(double a, int n) implements Function {
        @Override
        public double evaluate(double x) {
            return a * Math.pow(x, n);
        }

        @Override
        public Function derivative() {
            return n == 0 ? new Polynomial(0, 0) : new Polynomial(a * n, n - 1);
        }

        @Override
        public Function integral() {
            return new Polynomial(a / (n + 1), n + 1);
        }

        @Override
        public String expression() {
            return String.format("%.2f*x^%d", a, n);
        }
    }

    /** Representa f(x) = a * e^(b*x) */
    public record Exponential(double a, double b) implements Function {
        @Override
        public double evaluate(double x) {
            return a * Math.exp(b * x);
        }

        @Override
        public Function derivative() {
            return new Exponential(a * b, b);
        }

        @Override
        public Function integral() {
            return new Exponential(a / b, b);
        }

        @Override
        public String expression() {
            return String.format("%.2f*e^(%.2f*x)", a, b);
        }
    }

    /**
     * Aplica integración por partes simbólica:
     * ∫ u dv = u*v - ∫ v du
     */
    public static Function integrateByParts(Function u, Function dv) {
        Function v = dv.integral();
        Function du = u.derivative();

        System.out.println("u(x)  = " + u.expression());
        System.out.println("dv(x) = " + dv.expression());
        System.out.println("du(x) = " + du.expression());
        System.out.println("v(x)  = " + v.expression());

        // Resultado simbólico: u*v - ∫v*du
        return new SymbolicResult(u, v, du);
    }

    /**
     * Representa el resultado simbólico ∫ u dv = u*v - ∫ v du
     * No evalúa numéricamente ∫ v du; se muestra de forma simbólica.
     */
    public record SymbolicResult(Function u, Function v, Function du) implements Function {
        @Override
        public double evaluate(double x) {
            return u.evaluate(x) * v.evaluate(x); // valor parcial
        }

        @Override
        public Function derivative() {
            throw new UnsupportedOperationException("No se deriva el resultado simbólico.");
        }

        @Override
        public Function integral() {
            throw new UnsupportedOperationException("Resultado ya es una integral.");
        }

        @Override
        public String expression() {
            return String.format("(%s)*(%s) - ∫(%s)*(%s)dx",
                    u.expression(), v.expression(),
                    v.expression(), du.expression());
        }
    }

    /**
     * Ejemplo de uso:
     * Calcula ∫ x * e^x dx
     */
    public static void main(String[] args) {
        Function u = new Polynomial(1, 1); // x
        Function dv = new Exponential(1, 1); // e^x

        Function result = integrateByParts(u, dv);
        System.out.println("\nResultado simbólico:");
        System.out.println(result.expression());
    }
}
