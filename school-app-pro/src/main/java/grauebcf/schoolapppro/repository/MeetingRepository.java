package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
