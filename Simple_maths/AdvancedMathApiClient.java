/**
 * AdvancedMathApiClient.java
 *
 * Cliente HTTP en Java 21 para consumir una API gratuita de matemáticas.
 * En este ejemplo se usa la API de "New Math API" (https://newton.now.sh)
 * que permite resolver derivadas, integrales, simplificaciones, etc.
 *
 * Ejemplo:
 *   Operación: "derive"
 *   Expresión: "x^2"
 *   Resultado: "2 x"
 *
 * Java 21 – Sin dependencias externas.
 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AdvancedMathApiClient {

    // URL base de la API gratuita
    private static final String BASE_URL = "https://newton.now.sh/api/v2/";

    private final HttpClient client;

    /** Constructor: inicializa el cliente HTTP reutilizable. */
    public AdvancedMathApiClient() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Envía una solicitud a la API Newton para realizar operaciones matemáticas simbólicas.
     *
     * @param operation Operación a realizar. Ejemplos: "simplify", "derive", "integrate", "factor", "tangent"
     * @param expression Expresión matemática. Ejemplo: "x^2 + 2x"
     * @return Resultado textual devuelto por la API o mensaje de error controlado.
     */
    public String calculate(String operation, String expression) {
        try {
            String encodedExpr = URLEncoder.encode(expression, StandardCharsets.UTF_8);
            String url = BASE_URL + operation + "/" + encodedExpr;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Validación del código de estado HTTP
            if (response.statusCode() != 200) {
                return "Error: respuesta HTTP " + response.statusCode() + " - No se pudo obtener resultado.";
            }

            String body = response.body();

            // Verificación de respuesta vacía o inesperada
            if (body == null || body.isBlank()) {
                return "Error: la API devolvió una respuesta vacía.";
            }

            return body;

        } catch (IOException e) {
            return "Error de conexión: " + e.getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error: la solicitud fue interrumpida.";
        } catch (Exception e) {
            return "Error inesperado: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }
    }

    /**
     * Punto de entrada del programa. Demuestra el uso del cliente API.
     */
    public static void main(String[] args) {
        AdvancedMathApiClient client = new AdvancedMathApiClient();

        // Ejemplo 1: derivada
        String operation = "derive";
        String expression = "x^3 + 2x";

        System.out.println("Operación: " + operation);
        System.out.println("Expresión: " + expression);

        String result = client.calculate(operation, expression);
        System.out.println("Resultado: " + result);

        // Ejemplo 2: integral
        String result2 = client.calculate("integrate", "x^2");
        System.out.println("\nOperación: integrate");
        System.out.println("Expresión: x^2");
        System.out.println("Resultado: " + result2);
    }
}
