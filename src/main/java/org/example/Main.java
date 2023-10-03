package org.example;

import java.util.Arrays;

public class Main {

  public static double[] gaussianElimination(double[][] A, double[] b) {
    int n = A.length;
    double[] x = new double[n];

    for (int i = 0; i < n; i++) {
      int maxRow = i;
      for (int k = i + 1; k < n; k++) {
        if (Math.abs(A[k][i]) > Math.abs(A[maxRow][i])) {
          maxRow = k;
        }
      }
      swapRows(A, i, maxRow);
      swapRows(b, i, maxRow);

      double pivot = A[i][i];
      for (int j = i; j < n; j++) {
        A[i][j] /= pivot;
      }
      b[i] /= pivot;

      for (int k = i + 1; k < n; k++) {
        double factor = A[k][i];
        for (int j = i; j < n; j++) {
          A[k][j] -= factor * A[i][j];
        }
        b[k] -= factor * b[i];
      }
    }

    for (int i = n - 1; i >= 0; i--) {
      x[i] = b[i];
      for (int j = i + 1; j < n; j++) {
        x[i] -= A[i][j] * x[j];
      }
    }

    return x;
  }

  public static double[] matrixVectorMultiply(double[][] A, double[] x) {
    int n = A.length;
    int m = x.length;
    if (A[0].length != m) {
      throw new IllegalArgumentException("Incompatible matrix and vector dimensions");
    }

    double[] result = new double[n];
    Arrays.fill(result, 0);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        result[i] += A[i][j] * x[j];
      }
    }

    return result;
  }

  public static double[] simpleIteration(double[][] A, double[] b, double epsilon, int maxIterations) {
    int n = A.length;
    double[] x = {0, 0, 0};
    int iterations = 0;

    while (iterations < maxIterations) {
      double[] xNew = new double[n];
      System.out.println("Iteration " + iterations + ": " + Arrays.toString(x));
      for (int i = 0; i < n; i++) {
        double sum = 0.0;
        for (int j = 0; j < n; j++) {
          if (j != i) {
            sum += A[i][j] * x[j];
          }
        }
        xNew[i] = (b[i] - sum) / A[i][i];
      }

      double maxDiff = 0.0;
      for (int i = 0; i < n; i++) {
        double diff = Math.abs(xNew[i] - x[i]);
        if (diff > maxDiff) {
          maxDiff = diff;
        }
      }

      if (maxDiff < epsilon) {
        return xNew;
      }

      x = xNew;
      iterations++;
    }

    throw new IllegalArgumentException("The method did not converge.");
  }

  public static void swapRows(double[][] matrix, int row1, int row2) {
    double[] temp = matrix[row1];
    matrix[row1] = matrix[row2];
    matrix[row2] = temp;
  }

  public static void swapRows(double[] array, int row1, int row2) {
    double temp = array[row1];
    array[row1] = array[row2];
    array[row2] = temp;
  }

  public static void main(String[] args) {
    double[][] A = {
        {3, -1, 1},
        {2, -5, -3},
        {1, -1, -1}
    };

    double[] b = {7, -7, -1};

    // Solve using Gaussian Elimination
    double[] gaussianSolution = gaussianElimination(A, b);
    System.out.println("Solution using Gaussian Elimination: " + Arrays.toString(gaussianSolution));

    // Solve using Simple Iteration
    double[] simpleIterationSolution = simpleIteration(A, b, 1e-6, 1000);
    System.out.println("Solution using Simple Iteration: " + Arrays.toString(simpleIterationSolution));
  }
}
