package com.chainbell.placesearch.api.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class PlaceListDTO {
    String address;
    String roadAddress;
    String name;
}
