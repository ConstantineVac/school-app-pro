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
@Table(name = "TEACHERS", schema = "spring_school")
@Getter
@Setter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "ssn", nullable = false)
    private String ssn;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Meeting> meetings = new ArrayList<>();

    // Convenience methods
    public void setSpecialty(Specialty specialty) {
        if (this.specialty != null && !this.specialty.equals(specialty)) {
            this.specialty.getTeachers().remove(this);
        }

        this.specialty = specialty;

        if (specialty != null && !specialty.getTeachers().contains(this)) {
            specialty.getTeachers().add(this);
        }
    }

    public void addMeeting(Meeting meeting) {
        if (meeting != null && !meetings.contains(meeting)) {
            meetings.add(meeting);
            meeting.setTeacher(this);
        }
    }

    public void removeMeeting(Meeting meeting) {
        if (meeting != null && meetings.contains(meeting)) {
            meetings.remove(meeting);
            meeting.setTeacher(null);
        }
    }
}
