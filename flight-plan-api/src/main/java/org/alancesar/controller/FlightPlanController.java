package org.alancesar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightPlanController {

    @GetMapping("/bestRoute")
    public void getBestRoute(@RequestParam("origin") String origin,
                             @RequestParam("destination") String destination) {

        //TODO
    }
}
