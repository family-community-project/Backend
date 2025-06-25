package com.example.family.place.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(name = "search_id")
    private String searchId;

    private String addressName;

    private String categoryGroupCode;
    private String categoryGroupName;
    private String categoryName;

    private String phoneNumber;
    private String placeName;
    private String placeUrl;

    private String roadAddressName;
    private String latitude;
    private String longitude;

}
