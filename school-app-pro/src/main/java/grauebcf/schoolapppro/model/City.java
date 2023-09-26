package grauebcf.schoolapppro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CITIES", schema = "spring_school")
@Getter
@Setter
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "city_name")
    private String cityName;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
}
