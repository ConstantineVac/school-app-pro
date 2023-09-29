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
        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting updateMeeting(Meeting meeting) throws MeetingNotFoundException {
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
