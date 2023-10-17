package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.model.Meeting;
import grauebcf.schoolapppro.repository.MeetingRepository;
import grauebcf.schoolapppro.service.exception.MeetingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MeetingService implements IMeetingService{

    private final MeetingRepository meetingRepository;


    @Autowired
    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public List<Meeting> searchMeetings(String classroom) {
        return meetingRepository.findMeetingByClassroom(classroom);
    }

    @Override
    public Meeting insertMeeting(Meeting meeting) {
        // If the meeting has a teacher or student associated,
        // use the convenience methods to ensure consistency
        if (meeting.getTeacher() != null) {
            meeting.getTeacher().addMeeting(meeting);
        }

        if (meeting.getStudent() != null) {
            meeting.getStudent().addMeeting(meeting);
        }

        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting updateMeeting(Meeting meeting) throws MeetingNotFoundException {
        if (!meetingRepository.existsById(meeting.getMeetingId())) {
            throw new MeetingNotFoundException(meeting.getMeetingId());
        }

        Meeting existingMeeting = meetingRepository.findById(meeting.getMeetingId()).orElse(null);

        // If the meeting was previously associated with a different teacher, update that relationship
        if (existingMeeting != null && existingMeeting.getTeacher() != null && !existingMeeting.getTeacher().equals(meeting.getTeacher())) {
            existingMeeting.getTeacher().removeMeeting(existingMeeting);
        }

        // If the meeting was previously associated with a different student, update that relationship
        if (existingMeeting != null && existingMeeting.getStudent() != null && !existingMeeting.getStudent().equals(meeting.getStudent())) {
            existingMeeting.getStudent().removeMeeting(existingMeeting);
        }

        // Now, set the new relationships
        if (meeting.getTeacher() != null) {
            meeting.getTeacher().addMeeting(meeting);
        }

        if (meeting.getStudent() != null) {
            meeting.getStudent().addMeeting(meeting);
        }

        return meetingRepository.save(meeting);
    }

    @Override
    public void deleteMeeting(Long meetingId) throws MeetingNotFoundException {
        if (!meetingRepository.existsById(meetingId)) {
            throw new MeetingNotFoundException(meetingId);
        }

        // Delete the meeting, by meeting ID.
        meetingRepository.deleteById(meetingId);
    }
}
