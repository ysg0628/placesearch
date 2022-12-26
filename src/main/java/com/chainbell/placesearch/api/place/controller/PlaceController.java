package com.chainbell.placesearch.api.place.controller;

import com.chainbell.placesearch.api.place.dto.PlaceRankDTO;
import com.chainbell.placesearch.api.place.service.impl.PlaceSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/place/")
public class PlaceController {

    @Autowired
    PlaceSearchServiceImpl placeSearchService;

    @GetMapping("placeList")
    public List getPlaceList(@RequestParam("placeKeyword") String placeKeyword){

        List result = placeSearchService.getPlaceList(placeKeyword);

        return result;
    }

    @GetMapping("placeKeywordRank")
    public List<PlaceRankDTO> getPlaceKeywordRank(){

        List<PlaceRankDTO> result = placeSearchService.getPopularPlaceKeywordList();

        return result;
    }

}
