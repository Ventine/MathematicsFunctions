import java.util.*;
import java.util.regex.*;

public class DerivadaPolinomio {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese un polinomio (ejemplo: 3x^3 - 2x^2 + x - 5): ");
        String input = sc.nextLine().replaceAll("\\s+", "");
        sc.close();

        String derivada = derivarPolinomio(input);
        System.out.println("Derivada: " + derivada);
    }

    private static String derivarPolinomio(String polinomio) {
        // Divide los términos incluyendo signos
        List<String> terminos = dividirTerminos(polinomio);
        List<String> resultado = new ArrayList<>();

        for (String termino : terminos) {
            String derivado = derivarTermino(termino);
            if (!derivado.isEmpty()) {
                resultado.add(derivado);
            }
        }

        String derivada = String.join(" ", resultado)
                .replaceAll("\\+ -", "- ")
                .replaceAll("\\s+", " ");
        return derivada.isEmpty() ? "0" : derivada.trim();
    }

    private static List<String> dividirTerminos(String polinomio) {
        List<String> terminos = new ArrayList<>();
        Matcher m = Pattern.compile("([+-]?[^+-]+)").matcher(polinomio);
        while (m.find()) terminos.add(m.group());
        return terminos;
    }

    private static String derivarTermino(String termino) {
        // Caso: constante
        if (!termino.contains("x")) return "";

        // Extraer coeficiente y exponente
        double coeficiente = 1.0;
        int exponente = 1;

        Matcher m = Pattern.compile("([+-]?\\d*(?:\\.\\d+)?)x(?:\\^(\\d+))?").matcher(termino);
        if (m.matches()) {
            String coefStr = m.group(1);
            String expStr = m.group(2);

            if (!coefStr.isEmpty() && !coefStr.equals("+") && !coefStr.equals("-"))
                coeficiente = Double.parseDouble(coefStr);
            else if (coefStr.equals("-"))
                coeficiente = -1.0;

            if (expStr != null)
                exponente = Integer.parseInt(expStr);
        }

        double nuevoCoef = coeficiente * exponente;
        int nuevoExp = exponente - 1;

        if (nuevoExp == 0)
            return formatearNumero(nuevoCoef);
        else if (nuevoExp == 1)
            return formatearNumero(nuevoCoef) + "x";
        else
            return formatearNumero(nuevoCoef) + "x^" + nuevoExp;
    }

    private static String formatearNumero(double num) {
        if (num == (int) num) return String.valueOf((int) num);
        return String.valueOf(num);
    }
}
