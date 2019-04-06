package org.alancesar;

import org.alancesar.model.Route;
import org.alancesar.repository.CsvRouteRepository;
import org.alancesar.service.RouteService;
import org.alancesar.service.Service;

import java.io.File;
import java.io.IOException;

class ServiceFactory {

    private static String path;

    static Service<Route> getInstance() {
        try {
            ServiceFactory.path = String.format("%s%s.csv", System.getProperty("java.io.tmpdir"),
                    System.currentTimeMillis());

            if (!new File(path).createNewFile()) {
                throw new IOException("Cannot create the CSV file");
            }

            return new RouteService(new CsvRouteRepository(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    static void clean() {
        if (!new File(path).delete()) {
            System.out.println("Error on clean command");
        }
    }
}
