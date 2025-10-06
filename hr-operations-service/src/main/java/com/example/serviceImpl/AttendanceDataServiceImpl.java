package com.example.serviceImpl;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.dto.request.EmailRequest;
import com.example.dto.response.UserResponse;
import com.example.entity.AttendanceData;
import com.example.exception.NotFoundException;
import com.example.repository.AttendanceDataRepository;
import com.example.service.AttendanceDataService;
import com.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceDataServiceImpl implements AttendanceDataService {

    @Autowired
    private AttendanceDataRepository repository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private EmailService emailService;

    @Override
    public AttendanceData add(AttendanceData attendanceData) {
        return repository.save(attendanceData);
    }

    public AttendanceData findByUserAndDate(String user, LocalDate date){
        AttendanceData attendanceData = repository.findByUserAndDate(user,date).orElseThrow(() -> new NotFoundException("Attendance Data Not Found"));
        return  attendanceData;
    }

    @Override
    @Scheduled(cron = "0 0 20 * * ?", zone = "Asia/Kolkata")
    public void checkLateEmployees() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();
        LocalTime lateTime = LocalTime.of(9, 45);

        ApiResponse<List<UserResponse>> apiResponseList = userClient.findAllUsers();
        List<UserResponse> users = apiResponseList.getData();

        for (UserResponse user : users)
        {
            // Fetch all attendance of current month for this user
            List<AttendanceData> monthlyAttendance = repository.findByUserAndMonthAndYear(user.getId(), month, year);

            // Count how many times user was late this month
            long lateCount = monthlyAttendance.stream()
                    .filter(a -> a.getFirstPunchInTime() != null && a.getFirstPunchInTime().isAfter(lateTime))
                    .count();

            // Get today's attendance
            Optional<AttendanceData> todayAttendanceOpt = repository.findByUserAndDate(user.getId(), today);
            if (todayAttendanceOpt.isEmpty())
            {
                //System.out.println("No attendance recorded yet for user " + user.getId() + " today.");
                continue; // skip if employee hasn't punched in today
            }
            AttendanceData todayAttendance = todayAttendanceOpt.get();

            // Check if today is late
            if (todayAttendance.getFirstPunchInTime() != null && todayAttendance.getFirstPunchInTime().isAfter(lateTime))
            {
                if (lateCount >= 3)
                {
                    // already late 3 times this month
                    todayAttendance.setAttendanceStatus("HALFDAY");
                    repository.save(todayAttendance);

                    String fullName = user.getFirstName() + " " + user.getLastName();

                    EmailRequest emailRequest = EmailRequest.builder()
                            .to(user.getEmail())
                            .subject("Half-Day Marked for Late Attendance")
                            .message("Dear " + fullName + ",\n\n" +
                                    "You have reported late to office (after 9:45 AM) more than 3 times this month. " +
                                    "Today’s attendance has been marked as Half-Day.\n\n" +
                                    "Regards,\nHR Team")
                            .build();
                    emailService.sendEmail(emailRequest);
                }
            }
        }
    }

}
