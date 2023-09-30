package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m WHERE m.classroom LIKE %:classroom%")
    List<Meeting> findMeetingByClassroom(String classroom);

    @Query("SELECT m FROM Meeting m WHERE m.meetingId = :meetingId")
    Meeting getMeetingByMeetingId(@Param("meetingId") Long meetingId);
}
