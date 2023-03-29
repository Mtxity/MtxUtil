package com.edavalos.mtx.util.io;

import com.edavalos.mtx.util.string.MtxMatrixFormatter;
import com.edavalos.mtx.util.string.MtxStringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class MtxCsvParser {
    private String[][] csvContents;

    public MtxCsvParser(String filePath) throws IOException {
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new IOException("The file '" + filePath + "' could not be found.");
        }

        String[] fileName = filePath.split(Pattern.quote("."));
        if (!fileName[fileName.length - 1].equalsIgnoreCase("csv")) {
            throw new IOException("The file '" + filePath + "' is not a csv file.");
        }

        List<String[]> rows = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            rows.add(MtxStringUtil.splitAtCommas(fileScanner.nextLine()));
        }
        fileScanner.close();

        this.csvContents = rows.toArray(new String[0][]);
    }

    public String[][] getCsvContents() {
        return this.csvContents;
    }

    @Override
    public String toString() {
        return MtxMatrixFormatter.formatBorder(this.csvContents);
    }
}
