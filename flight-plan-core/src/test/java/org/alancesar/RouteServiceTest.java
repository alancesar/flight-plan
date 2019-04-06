package org.alancesar;

import org.alancesar.model.Route;
import org.alancesar.service.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RouteServiceTest {

    private Service<Route> service = ServiceFactory.getInstance();

    @Test
    void saveAndRead() {
        Route route = new Route("ORI", "DES", 10);

        service.save(route);
        List<Route> routes = service.getAll();

        Assertions.assertEquals(1, routes.size());
        Assertions.assertEquals(route.getOrigin(), routes.get(0).getOrigin());
        Assertions.assertEquals(route.getDestination(), routes.get(0).getDestination());
        Assertions.assertEquals(route.getPrice(), routes.get(0).getPrice());

        ServiceFactory.clean();
    }
}
