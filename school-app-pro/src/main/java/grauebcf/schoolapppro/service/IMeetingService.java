package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.model.Meeting;
import grauebcf.schoolapppro.service.exception.MeetingNotFoundException;

import java.util.List;

public interface IMeetingService {
    List<Meeting> getAllMeetings();
    List<Meeting> searchMeetings(String classroom);
    Meeting insertMeeting(Meeting meeting);
    Meeting updateMeeting(Meeting meeting) throws MeetingNotFoundException;
    void deleteMeeting(Long meetingId) throws MeetingNotFoundException;
}
