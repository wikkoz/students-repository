package com.services.file;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class FileService {

    private static String SPLITTER = ",";

    public InputStream decodeBase64(FileDto encoded) {
        return new ByteArrayInputStream(Base64.getDecoder().decode(encoded.getBase64()));
    }

    public File getFile(InputStream inputStream, List<String> headers) {
        File file = new File();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))){
            compareHeaders(headers, reader.readLine());
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(SPLITTER);
                file.addNewRow(Arrays.asList(row));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return file;
    }

    private void compareHeaders(List<String> headers, String header) {
        String[] headersTable = header.split(SPLITTER);
        if (!Sets.newTreeSet(Arrays.asList(headersTable)).equals(Sets.newTreeSet(headers)))
            throw new IllegalArgumentException(String.format("Nie poprawny nagłowek pliku %s oczekiwano %s",
                    header, headers));
    }

    public <T> List<T> getObjectsFromFile(File file, Function<List<String>, List<T>> fun) {
        return file.stream()
                .map(fun)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public <T> List<T> getObjectFromFile(File file, Function<List<String>, T> fun) {
        return file.stream()
                .map(fun)
                .collect(Collectors.toList());
    }
}
