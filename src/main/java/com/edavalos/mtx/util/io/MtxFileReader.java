package com.edavalos.mtx.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class MtxFileReader {
    private final String[] contents;

    public MtxFileReader(String filePath) throws IOException {
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new IOException("The file '" + filePath + "' could not be found.");
        }

        List<String> rows = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            rows.add(fileScanner.nextLine());
        }
        fileScanner.close();

        this.contents = rows.toArray(new String[0]);
    }

    public String[] getContents() {
        return this.contents;
    }
}
