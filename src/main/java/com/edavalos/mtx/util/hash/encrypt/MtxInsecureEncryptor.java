package com.edavalos.mtx.util.hash.encrypt;

public final class MtxInsecureEncryptor {
    private static final char WHITESPACE = ' ';

    private MtxInsecureEncryptor() { }

    public static String encrypt(String string) {
        char[] deconstructedString = string.toCharArray();
        int l = deconstructedString.length;
        int x = (int) Math.ceil(Math.sqrt(l));

        // Generate encryption matrix
        char[][] matrix = new char[x][x];
        int k = 0;
        for (int row = 0; row < x; row++) {
            for (int col = 0; col < x; col++) {
                if (k < l) {
                    matrix[row][col] = deconstructedString[k++];
                } else {
                    matrix[row][col] = WHITESPACE;
                }
            }
        }

        // Generate encrypted string
        StringBuilder encryptedString = new StringBuilder();
        for (int row2 = 0; row2 < x; row2++) {
            for (int col2 = 0; col2 < x; col2++) {
                // rows and columns are flipped
                encryptedString.append(matrix[col2][row2]);
            }
        }

        return encryptedString.toString();
    }

    public static String decrypt(String encryptedString) {
        char[] deconstructedEncryptedString = encryptedString.toCharArray();
        int l = deconstructedEncryptedString.length;
        int y = (int) Math.ceil(Math.sqrt(l));

        // Generate decryption matrix
        char[][] matrix = new char[y][y];
        int k = 0;
        for (int row = 0; row < y; row++) {
            for (int col = 0; col < y; col++) {
                if (k < l) {
                    matrix[row][col] = deconstructedEncryptedString[k++];
                } else {
                    matrix[row][col] = WHITESPACE;
                }
            }
        }

        // Decrypt string
        StringBuilder decryptedString = new StringBuilder();
        for (int row2 = 0; row2 < y; row2++) {
            for (int col2 = 0; col2 < y; col2++) {
                // rows and columns are flipped
                decryptedString.append(matrix[col2][row2]);
            }
        }

        return decryptedString.toString().stripTrailing();
    }
}
