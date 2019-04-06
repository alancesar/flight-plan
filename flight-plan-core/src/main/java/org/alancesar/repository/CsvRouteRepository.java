package org.alancesar.repository;

import org.alancesar.model.Route;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvRouteRepository implements RouteRepository<String> {

    private final String path;

    public CsvRouteRepository(String path) {
        this.path = path;
    }

    @Override
    public void write(Route input, Function<Route, String> mapper) {
        try {
            String content = mapper.apply(input);
            Files.write(Paths.get(path), content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Route> readAll(Function<String, Route> mapper) {
        try (InputStream inputStream = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines().map(mapper).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
