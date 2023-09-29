package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findMeetingByClassroom(String classroom);

//    @Query("SELECT m FROM Meeting m WHERE m.teacher.lastname LIKE %:lastName%")
//    List<Meeting> getMeetingByTeacherFirstname(@Param("lastname") String lastname);

    @Query("SELECT m FROM Meeting m WHERE m.meetingId = :meetingId")
    Meeting getMeetingByMeetingId(@Param("meetingId") Long meetingId);
}
