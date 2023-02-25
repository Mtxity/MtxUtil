package com.edavalos.mtx.util.hash.encrypt;

public final class MtxInsecureEncryptor {

    private MtxInsecureEncryptor() { }

    public static String encrypt(String string) {
        char[] deconstructedString = string.toCharArray();
        int l = deconstructedString.length;
        int a = (int) Math.floor(Math.sqrt(l));
        int b = (int) Math.ceil(Math.sqrt(l));
        if (a * b < l) {
            if (Math.min(b, a) == b) {
                b++;
            } else {
                a++;
            }
        }

        // Generate encryption matrix
        char[][] matrix = new char[a][b];
        int k = 0;
        for (int row = 0; row < a; row++) {
            for (int col = 0; col < b; col++) {
                if (k < l) {
                    matrix[row][col] = deconstructedString[k];
                }
                k++;
            }
        }

        // Generate encrypted string
        StringBuilder encryptedString = new StringBuilder();
        for (int row2 = 0; row2 < b; row2++) {
            for (int col2 = 0; col2 < a; col2++) {
                // rows and columns are flipped
                encryptedString.append(matrix[col2][row2]);
            }
        }

        return encryptedString.toString();
    }

    public static String decrypt(String encryptedString) {
        char[] deconstructedEncryptedString = encryptedString.toCharArray();
        int l = deconstructedEncryptedString.length;
        int a = (int) Math.floor(Math.sqrt(l));
        int b = (int) Math.ceil(Math.sqrt(l));
        while (a != b) {
            if (Math.min(b, a) == b) {
                b++;
            } else {
                a++;
            }
        }

        // Generate decryption matrix
        char[][] matrix = new char[a][b];
        int k = 0;
        for (int row = 0; row < b; row++) {
            for (int col = 0; col < a; col++) {
                if (k < l) {
                    matrix[row][col] = deconstructedEncryptedString[k];
                }
                k++;
            }
        }

        // Decrypt string
        StringBuilder decryptedString = new StringBuilder();
        for (int row2 = 0; row2 < a; row2++) {
            for (int col2 = 0; col2 < b; col2++) {
                // null chars may appear
                if (matrix[col2][row2] == '\u0000') {
                    continue;
                }
                // rows and columns are flipped
                decryptedString.append(matrix[col2][row2]);
            }
        }

        return decryptedString.toString().strip();
    }
}
