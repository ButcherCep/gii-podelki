package ru.sbp.gazii.podelki.arrays.tasks;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/770/

public class Zadacha_11 {

    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // Транспонируем матрицу
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Зеркально отражаем матрицу по вертикали
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        Zadacha_11 solution = new Zadacha_11();
        solution.rotate(matrix);

        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}