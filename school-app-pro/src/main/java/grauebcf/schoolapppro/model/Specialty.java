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
@Table(name = "SPECIALTIES", schema = "spring_school")
@Getter
@Setter
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id")
    private Long specialtyId;

    @Column(name = "specialty_name", nullable = false)
    private String specialtyName;

    @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Teacher> teachers = new ArrayList<>();

    // Convenience methods
    public void addTeacher(Teacher teacher) {
        if (teacher != null && !teachers.contains(teacher)) {
            teachers.add(teacher);
            teacher.setSpecialty(this);
        }
    }

    public void removeTeacher(Teacher teacher) {
        if (teacher != null && teachers.contains(teacher)) {
            teachers.remove(teacher);
            teacher.setSpecialty(null);
        }
    }
}
