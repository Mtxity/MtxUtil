package com.edavalos.mtx.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MtxJsonParser {
    private final String rawStream;

    public MtxJsonParser(String filePath) throws IOException {
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new IOException("The file '" + filePath + "' could not be found.");
        }

        StringBuilder stream = new StringBuilder();
        while (fileScanner.hasNextLine()) {
            stream.append(fileScanner.nextLine().replaceAll("\n", ""));
        }
        fileScanner.close();

        this.rawStream = stream.toString();
    }

    public String getRawStream() {
        return this.rawStream;
    }
}
