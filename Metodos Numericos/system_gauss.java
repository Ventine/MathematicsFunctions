import java.util.Scanner;

public class GaussSolver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Número de ecuaciones: ");
        int n = sc.nextInt();

        double[][] A = new double[n][n + 1];
        double[] x = new double[n];

        System.out.println("Ingrese la matriz aumentada (A|b):");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n + 1; j++)
                A[i][j] = sc.nextDouble();

        // Eliminación hacia adelante
        for (int i = 0; i < n; i++) {
            // Pivoteo parcial
            int max = i;
            for (int k = i + 1; k < n; k++)
                if (Math.abs(A[k][i]) > Math.abs(A[max][i]))
                    max = k;

            double[] temp = A[i];
            A[i] = A[max];
            A[max] = temp;

            // Normalización y eliminación
            for (int k = i + 1; k < n; k++) {
                double factor = A[k][i] / A[i][i];
                for (int j = i; j < n + 1; j++)
                    A[k][j] -= factor * A[i][j];
            }
        }

        // Sustitución regresiva
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++)
                sum += A[i][j] * x[j];
            x[i] = (A[i][n] - sum) / A[i][i];
        }

        System.out.println("Solución del sistema:");
        for (int i = 0; i < n; i++)
            System.out.printf("x%d = %.6f%n", i + 1, x[i]);
    }
}
