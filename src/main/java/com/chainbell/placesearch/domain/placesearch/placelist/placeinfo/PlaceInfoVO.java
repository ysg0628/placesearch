package com.chainbell.placesearch.domain.placesearch.placelist.placeinfo;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class PlaceInfoVO {
    String address;
    String roadAddress;
    String name;
    int serviceCode; // (중복-0, 카카오-1, 네이버-2)
}
