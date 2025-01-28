package org.anth0o0ny.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.anth0o0ny.enums.Climate;
import org.anth0o0ny.enums.Government;
import org.anth0o0ny.enums.StandardOfLiving;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @NotNull
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Min(0)
    private int area;

    @Min(0)
    private int population;

    @Column(name = "meters_above_sea_level")
    private Long metersAboveSeaLevel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "climate", columnDefinition = "climate_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Climate climate;

    @Enumerated(EnumType.STRING)
    @Column(name = "government", columnDefinition = "government_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Government government;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "standard_of_living", columnDefinition = "standard_of_living_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private StandardOfLiving standardOfLiving;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "governor_id", referencedColumnName = "id")
    private Human governor;
}
