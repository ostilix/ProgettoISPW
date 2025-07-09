package com.nestaway.utils.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CSVHandler{
    private final String filePath;
    private final String delimiter;

    public CSVHandler(String filePath, String delimiter) {
        this.filePath = filePath;
        this.delimiter = delimiter;
    }

    public void writeAll(List<String[]> rs) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String[] r : rs) {
                writer.write(String.join(delimiter, r));
                writer.newLine();
            }
            writer.flush();
        }
    }

    public List<String[]> readAll() throws IOException{
        List<String[]> rs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rs.add(line.split(delimiter));
            }
        }
        return rs;
    }

    public void writeCleaned(List<String[]> rs) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] r : rs) {
                writer.write(String.join(delimiter, r));
                writer.newLine();
            }
            writer.flush();
        }
    }

    public List<String[]> find(Predicate<String[]> predicate) throws IOException{
        return readAll().stream().filter(predicate).toList();
    }

    public void remove(Predicate<String[]> predicate) throws IOException{
        List<String[]> filtered = readAll();
        filtered = filtered.stream().filter(predicate.negate()).toList();
        writeCleaned(filtered);
    }

    public void remove(List<Predicate<String[]>> predicates) throws IOException{
        List<String[]> filtered = readAll();
        for (Predicate<String[]> predicate : predicates) {
            filtered = filtered.stream().filter(predicate.negate()).toList();
        }
        writeCleaned(filtered);
    }
}

