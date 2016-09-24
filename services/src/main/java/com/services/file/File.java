package com.services.file;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Stream;

public class File {
    private int size;
    private List<List<String>> file = Lists.newArrayList();

    public void addNewRow(List<String> row) {
        if (size == 0)
            size = row.size();
        else if (size != row.size())
            throw new IllegalArgumentException(String.format("Nie poprawny wiersz %s", row.toString()));

        file.add(row);
    }

    public List<List<String>> getFile() {
        return file;
    }

    public Stream<List<String>> stream() {
        return file.stream();
    }
}
