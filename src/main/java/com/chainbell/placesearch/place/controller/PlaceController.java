package com.chainbell.placesearch.place.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/place/")
public class PlaceController {

    @GetMapping("placeList")
    public String getPlaceList(@RequestParam("placeKeyword") String placeKeyword){

        return placeKeyword;
    }

}
