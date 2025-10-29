package ru.sbp.gazii.podelki.arrays.tasks;

import java.util.HashSet;
import java.util.Set;

//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/769/
public class Zadacha_10 {
    public static void main(String[] args) {
        char[][] sudoku =
                {
                        {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                        {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                        {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                        {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                        {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                        {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                        {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                        {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                };
        System.out.println(isValidSudoku(sudoku));
    }

    public static boolean isValidSudoku(char[][] board) {
        boolean b = true;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < 9; i++) {  //строки
            set.clear();
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    if (set.contains(board[i][j])) {
                        return false;
                    }
                    set.add(board[i][j]);
                }
            }
        }
        for (int i = 0; i < 9; i++) { //столбцы
            set.clear();
            for (int j = 0; j < 9; j++) {
                if (board[j][i] != '.') {
                    if (set.contains(board[j][i])) {
                        return false;
                    }
                    set.add(board[j][i]);
                }
            }
        }
        for (int k = 0; k < 9; k += 3) {
            for (int n = 0; n < 9; n += 3) {
                set.clear();
                for (int p = k; p < k + 3; p++) {
                    for (int o = n; o < n + 3; o++) {
                        if ((board[p][o] != '.')) {
                            if (set.contains(board[p][o])) {
                                return false;
                            }
                            set.add(board[p][o]);
                        }

                    }
                }
            }
        }
        return true;
    }

}
