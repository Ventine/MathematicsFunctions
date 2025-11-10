/**
 * MathApiClient.java
 *
 * Ejemplo de cliente HTTP en Java 21 que consume una API pública de matemáticas.
 * Usa la API gratuita de Math.js (https://api.mathjs.org/) para evaluar expresiones.
 *
 * Ejemplo:
 * Entrada:  "2 * (7 + 3)"
 * Salida:   20
 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MathApiClient {

    /**
     * Evalúa una expresión matemática usando la API pública de mathjs.org
     * @param expression Expresión matemática, ej: "2*(7+3)"
     * @return Resultado de la evaluación en formato String
     */
    public static String evaluateExpression(String expression) throws IOException, InterruptedException {
        String encodedExpr = URLEncoder.encode(expression, StandardCharsets.UTF_8);
        String apiUrl = "https://api.mathjs.org/v4/?expr=" + encodedExpr;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    /**
     * Punto de entrada.
     * Demuestra cómo consumir la API y manejar errores comunes.
     */
    public static void main(String[] args) {
        try {
            String expr = "sqrt(16) + 5^2 / (3 - 1)";
            String result = evaluateExpression(expr);
            System.out.println("Expresión: " + expr);
            System.out.println("Resultado: " + result);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al conectar con la API: " + e.getMessage());
        }
    }
}
