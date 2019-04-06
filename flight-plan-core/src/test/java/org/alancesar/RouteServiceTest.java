package org.alancesar;

import org.alancesar.model.Route;
import org.alancesar.service.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RouteServiceTest {

    private RouteService service = ServiceFactory.getInstance();

    @Test
    public void saveAndRead() {
        Route route = new Route("ORI", "DES", 10);

        service.save(route);
        List<Route> routes = service.getRoutes();

        Assertions.assertEquals(1, routes.size());
        Assertions.assertEquals(route.getOrigin(), routes.get(0).getOrigin());
        Assertions.assertEquals(route.getDestination(), routes.get(0).getDestination());
        Assertions.assertEquals(route.getPrice(), routes.get(0).getPrice());

        ServiceFactory.clean();
    }
}
