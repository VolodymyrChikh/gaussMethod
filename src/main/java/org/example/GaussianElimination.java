package org.example;

import java.util.Scanner;

public class GaussianElimination {

  public static double[] solve(double[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length - 1; // The last column is the vector b

    // Forward elimination to reduce to row-echelon form
    for (int i = 0; i < rows; i++) {
      // Find the pivot element
      int maxRowIndex = i;
      for (int j = i + 1; j < rows; j++) {
        if (Math.abs(matrix[j][i]) > Math.abs(matrix[maxRowIndex][i])) {
          maxRowIndex = j;
        }
      }

      // Swap rows
      double[] tempRow = matrix[i];
      matrix[i] = matrix[maxRowIndex];
      matrix[maxRowIndex] = tempRow;

      // Normalize the current row
      double divisor = matrix[i][i];
      for (int j = i; j <= cols; j++) {
        matrix[i][j] /= divisor;
      }

      // Elimination
      for (int k = 0; k < rows; k++) {
        if (k != i) {
          double factor = matrix[k][i];
          for (int j = i; j <= cols; j++) {
            matrix[k][j] -= factor * matrix[i][j];
          }
        }
      }
    }

    // Back substitution to find solutions
    double[] solutions = new double[rows];
    for (int i = 0; i < rows; i++) {
      solutions[i] = matrix[i][cols];
    }

    return solutions;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the number of equations: ");
    int rows = scanner.nextInt();
    System.out.print("Enter the number of variables (including b): ");
    int cols = scanner.nextInt();

    // Create the matrix with appropriate dimensions
    double[][] matrix = new double[rows][cols];

    // Input matrix elements
    for (int i = 0; i < rows; i++) {
      System.out.println("Enter coefficients for equation " + (i + 1) + ":");
      for (int j = 0; j < cols; j++) {
        System.out.print("Enter coefficient for x" + (j + 1) + ": ");
        matrix[i][j] = scanner.nextDouble();
      }
    }

    double[] solutions = solve(matrix);

    // Output solutions
    System.out.println("Solutions:");
    for (int i = 0; i < solutions.length; i++) {
      System.out.println("x" + (i + 1) + " = " + solutions[i]);
    }

    scanner.close();
  }
}
