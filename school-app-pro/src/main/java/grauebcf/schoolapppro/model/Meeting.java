package grauebcf.schoolapppro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEETINGS", schema = "spring_school")
@Getter
@Setter
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "classroom")
    private String classroom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    // Convenience Methods
    public void setTeacher(Teacher teacher) {
        if (this.teacher != null && !this.teacher.equals(teacher)) {
            this.teacher.getMeetings().remove(this);
        }

        this.teacher = teacher;

        if (teacher != null && !teacher.getMeetings().contains(this)) {
            teacher.getMeetings().add(this);
        }
    }

    public void setStudent(Student student) {
        if (this.student != null && !this.student.equals(student)) {
            this.student.getMeetings().remove(this);
        }

        this.student = student;

        if (student != null && !student.getMeetings().contains(this)) {
            student.getMeetings().add(this);
        }
    }
}
