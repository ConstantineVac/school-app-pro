package grauebcf.schoolapppro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityUpdateDTO {
    private Long cityId;
    private String cityName;

    public CityUpdateDTO(String cityName) {
        this.cityName = cityName;
    }
}
