package grauebcf.schoolapppro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STUDENTS", schema = "spring_school")
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "firstname",nullable = false)
    private String firstname;

    @Column(name = "lastname",nullable = false)
    private String lastname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="city_id")
    private City city;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Meeting> meetings = new ArrayList<>();

    // Convenience methods

    // Set the city for this student
    public void setCity(City city) {
        // Check if the current city is different from the new city
        if (this.city != null && !this.city.equals(city)) {
            this.city.getStudents().remove(this);
        }

        this.city = city;

        if (city != null && !city.getStudents().contains(this)) {
            city.getStudents().add(this);
        }
    }

    public void addMeeting(Meeting meeting) {
        if (meeting != null && !meetings.contains(meeting)) {
            meetings.add(meeting);
            meeting.setStudent(this);
        }
    }

    public void removeMeeting(Meeting meeting) {
        if (meeting != null && meetings.contains(meeting)) {
            meetings.remove(meeting);
            meeting.setStudent(null);
        }
    }
}
