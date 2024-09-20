package com.example.routing.models;

import com.example.routing.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(indexes =
        {@Index(name = "merchantId", columnList = "merchantId"), @Index(name = "tag", columnList = "tag"), @Index(name = "originCountry", columnList = "originCountry"), @Index(name = "configurationId", columnList = "configurationId")})
public class MerchantConfiguration {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(unique = true, length = 21)
    private String configurationId;
    @Column(unique = false, length = 10)
    private String currencyConstrint;
    private BigDecimal maxAmmount;
    private BigDecimal minAmount;
    @Column(unique = false, length = 21)
    private String merchantId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "originCountryId", referencedColumnName = "id")
    private Country originCountry;
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider", referencedColumnName = "id")
    private Provider provider;
}
