package org.alancesar;

import org.alancesar.repository.CsvRouteRepository;
import org.alancesar.service.RouteService;

import java.io.File;
import java.io.IOException;

public class ServiceFactory {

    public static String path;

    public static RouteService getInstance() {
        try {
            ServiceFactory.path = String.format("%s%s.csv", System.getProperty("java.io.tmpdir"),
                    System.currentTimeMillis());
            new File(path).createNewFile();
            return new RouteService(new CsvRouteRepository(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void clean() {
        new File(path).delete();
    }
}
