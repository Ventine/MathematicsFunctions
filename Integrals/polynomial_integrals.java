import java.util.*;

public class IntegralPolinomio {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el polinomio (ej: 3x^2 + 2x + 1): ");
        String entrada = sc.nextLine().replace(" ", "");
        sc.close();

        List<Termino> terminos = parsear(entrada);
        List<Termino> integrados = integrar(terminos);

        System.out.print("∫f(x)dx = ");
        for (int i = 0; i < integrados.size(); i++) {
            Termino t = integrados.get(i);
            if (t.coeficiente > 0 && i > 0) System.out.print("+");
            if (t.exponente == 0)
                System.out.print(t.coeficiente);
            else if (t.exponente == 1)
                System.out.print(t.coeficiente + "x");
            else
                System.out.print(t.coeficiente + "x^" + t.exponente);
        }
        System.out.println("+ C");
    }

    static class Termino {
        double coeficiente;
        int exponente;
        Termino(double c, int e) { coeficiente = c; exponente = e; }
    }

    static List<Termino> parsear(String entrada) {
        List<Termino> terminos = new ArrayList<>();
        entrada = entrada.replace("-", "+-");
        String[] partes = entrada.split("\\+");
        for (String p : partes) {
            if (p.isEmpty()) continue;
            double coef = 0;
            int exp = 0;
            if (!p.contains("x")) {
                coef = Double.parseDouble(p);
            } else {
                String[] seccion = p.split("x");
                coef = seccion[0].equals("") || seccion[0].equals("+") ? 1 :
                        seccion[0].equals("-") ? -1 : Double.parseDouble(seccion[0]);
                exp = seccion.length > 1 && seccion[1].startsWith("^")
                        ? Integer.parseInt(seccion[1].substring(1))
                        : 1;
            }
            terminos.add(new Termino(coef, exp));
        }
        return terminos;
    }

    static List<Termino> integrar(List<Termino> terminos) {
        List<Termino> r = new ArrayList<>();
        for (Termino t : terminos) {
            int nuevoExp = t.exponente + 1;
            double nuevoCoef = t.coeficiente / nuevoExp;
            r.add(new Termino(nuevoCoef, nuevoExp));
        }
        return r;
    }
}
