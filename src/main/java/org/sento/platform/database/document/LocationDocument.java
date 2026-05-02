package org.sento.platform.database.document;

import lombok.*;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocationDocument implements Serializable {

    @Field("name")
    private String name;

    @Field("address")
    private String address;

    @Field("city")
    private String city;

    @Field("country")
    private String country;

    @GeoSpatialIndexed
    @Field("coordinates")
    private double[] coordinates;

    @Builder.Default
    @Field("type")
    private String type = "Point";

    @Field("place_id")
    private String placeId;

    @Field("accuracy")
    private Double accuracy;

    @Field("provider")
    private String provider;
}