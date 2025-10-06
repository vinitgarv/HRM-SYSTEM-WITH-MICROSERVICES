package com.example.serviceImpl;

import com.example.apiResponse.ApiResponse;
import com.example.client.UserClient;
import com.example.dto.request.AttendanceRecord;
import com.example.dto.response.AttendanceDataResponse;
import com.example.dto.response.AttendanceList;
import com.example.dto.response.AttendanceLogsResponse;
import com.example.dto.response.UserResponse;
import com.example.entity.AttendanceData;
import com.example.entity.AttendanceLogs;
import com.example.mapper.AttandanceLogsMapper;
import com.example.repository.AttendanceLogsRepository;
import com.example.service.AttendanceDataService;
import com.example.service.AttendanceLogsService;
import com.example.util.UtilsMethods;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStreamReader;
import java.time.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AttendanceLogsServiceImpl implements AttendanceLogsService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private AttendanceLogsRepository repository;

    @Autowired
    private AttendanceDataService attendanceDataService;


    @Override
    public String punchIn(String userId) {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();

        LocalDate today = LocalDate.now();

        List<AttendanceLogs> todayLogs = repository.findByUserAndDate(user.getId(), today);

        if (todayLogs.size() >= 2) {
            throw new IllegalStateException("You have already checked in and checked out today.");
        }

        String punchType = todayLogs.isEmpty() ? "Checkin" : "Checkout";

        AttendanceLogs attendanceLogs = AttandanceLogsMapper
                .convertAttendenceLogRequestToAttandenceLog(punchType);
        attendanceLogs.setUser(user.getId());
        repository.save(attendanceLogs);

        if (punchType.equals("Checkin")) {
            // create new attendance row
            AttendanceData attendanceData = AttendanceData.builder()
                    .id(UtilsMethods.generateId())
                    .user(user.getId())
                    .date(today)
                    .firstPunchInTime(attendanceLogs.getPunchTime())
                    .lastPunchoutTime(null)
                    .attendanceStatus("Pending")
                    .valid(true)
                    .isActive(true)
                    .isDeleted(false)
                    .build();

            attendanceDataService.add(attendanceData);

        } else {
            // update existing attendance row
            AttendanceData attendanceData = attendanceDataService.findByUserAndDate(user.getId(), today);
            if (attendanceData == null) {
                throw new IllegalStateException("No check-in record found for today.");
            }

            attendanceData.setLastPunchoutTime(attendanceLogs.getPunchTime());

            Duration worked = Duration.between(attendanceData.getFirstPunchInTime(),
                    attendanceData.getLastPunchoutTime());

            long workedHours = worked.toHours();

            if (workedHours < 4) {
                attendanceData.setAttendanceStatus("Half Day");
            } else {
                attendanceData.setAttendanceStatus("Present");
            }

            attendanceDataService.add(attendanceData);
        }

        return punchType + " successful at " + attendanceLogs.getPunchTime();
    }


    private static final LocalTime OFFICE_START = LocalTime.of(9, 30);
    private static final LocalTime OFFICE_END = LocalTime.of(18, 25);
    private static final LocalTime LATE_THRESHOLD = LocalTime.of(9, 45);
    private static final LocalTime HALFDAY_THRESHOLD = LocalTime.of(13, 30);

//    @Override
//    public String punchIn(String userId) {
//        User user = userService.findById(userId);
//        LocalDate today = LocalDate.now();
//
//        List<AttendanceLogs> todayLogs = repository.findByUserAndDate(user, today);
//
//        if (todayLogs.size() >= 2) {
//            throw new IllegalStateException("You have already checked in and checked out today.");
//        }
//
//        String punchType = todayLogs.isEmpty() ? "Checkin" : "Checkout";
//
//        AttendanceLogs attendanceLogs = AttandanceLogsMapper
//                .convertAttendenceLogRequestToAttandenceLog(punchType);
//
//        attendanceLogs.setUser(user);
//        repository.save(attendanceLogs);
//
//        AttendanceData attendanceData;
//
//        if (punchType.equals("Checkin")) {
//            attendanceData = AttendanceData.builder()
//                    .id(UtilsMethods.generateId())
//                    .user(user)
//                    .date(today)
//                    .firstPunchInTime(attendanceLogs.getPunchTime())
//                    .lastPunchoutTime(null)
//                    .attendanceStatus("Present")
//                    .valid(true)
//                    .isActive(true)
//                    .isDeleted(false)
//                    .build();
//            attendanceDataService.add(attendanceData);
//
//        } else {
//            attendanceData = attendanceDataService.findByUserAndDate(user, today);
////            if (attendanceData == null) {
////                throw new IllegalStateException("No checkin record found for today.");
////            }
//
//            attendanceData.setLastPunchoutTime(attendanceLogs.getPunchTime());
//            attendanceDataService.add(attendanceData);
//        }
//        return punchType + " successful at " + attendanceLogs.getPunchTime();
//    }

    private final List<AttendanceRecord> attendanceList = new ArrayList<>();

    public AttendanceLogsServiceImpl() {
        loadCsvFromResources();
    }

    private void loadCsvFromResources() {
        try (CSVReader reader = new CSVReader(
                new InputStreamReader(getClass().getResourceAsStream("/attendance_punches data.csv")))) {

            String[] header = reader.readNext(); // skip header
            String[] row;

            while ((row = reader.readNext()) != null) {
                if (row.length == 0 || (row[0] == null || row[0].trim().isEmpty())) {
                    continue;
                }

                String userId = row[0];
                List<LocalDateTime> punches = new ArrayList<>();

                for (int i = 1; i < row.length; i++) {
                    if (row[i] != null && !row[i].isBlank()) {
                        punches.add(LocalDateTime.parse(row[i].trim()));
                    }
                }

                attendanceList.add(new AttendanceRecord(userId, punches));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AttendanceLogsResponse> getAllAttendanceOfaUserByDate(LocalDate date, String userId) {
       // UserResponse user= userClient.getByUserId(userId);
        List<AttendanceLogs> attendanceLogs = repository.findByDateAndUser(date,userId);
        attendanceLogs.sort(Comparator.comparing(AttendanceLogs::getPunchTime));
        return attendanceLogs.stream()
                .map(AttandanceLogsMapper::AttandenceLogToAttandenceLogResponse)
                .toList();
    }

    @Override
    public List<AttendanceLogsResponse> getAllAttendanceOfaUserByMonth(String userId,int month, int year)
    {
        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();
        List<AttendanceLogs> attendanceLogs = repository.findByMonthAndUser(month,year,user.getId());
        attendanceLogs.sort(
                Comparator.comparing(AttendanceLogs::getDate)
                          .thenComparing(AttendanceLogs::getPunchTime)
        );
        return attendanceLogs.stream()
                .map(AttandanceLogsMapper::AttandenceLogToAttandenceLogResponse)
                .toList();
    }

//    @Override
//    public void add(AttandenceLogsRequest request) {
//        repository.save(AttandanceLogsMapper.convertAttendenceLogRequestToAttandenceLog(request));
//    }


    @Override
    public List<AttendanceLogsResponse> addAllUserAttendance() {
        for (int i = 0; i < attendanceList.size(); i++) {
            String userId = attendanceList.get(i).getUserId();
            List<LocalDateTime> punchTimes = attendanceList.get(i).getPunches();


            for (int j = 0; j < punchTimes.size(); j++) {
                AttendanceLogs log = AttendanceLogs.builder()
                        .id(UtilsMethods.generateId())
                        .isDeleted(false)
                        .isActive(true)
                        .punchType(j % 2 == 0 ? "CHECK_IN" : "CHECK_OUT")
                        .punchTime(punchTimes.get(j).toLocalTime())
                        .date(punchTimes.get(j).toLocalDate())
                        .bmType("Face")
                        .location("Office")
                        .user(userId)
                        .build();
                repository.save(log);
            }

            LocalDate logDate = punchTimes.get(0).toLocalDate();
            List<AttendanceLogsResponse> attendanceLogs = getAllAttendanceOfaUserByDate(logDate, userId);

            System.out.println("\n*\n*\n*\n*");
            System.out.println(attendanceLogs);
            System.out.println("\n*\n*\n*\n*");

            int totalWorkedMinutes = 0;
            int logSize = attendanceLogs.size();

            if (logSize >= 2 && logSize % 2 == 0) {
                for (int k = 0; k < logSize; k += 2) {
                    LocalDateTime checkIn = LocalDateTime.of(
                            attendanceLogs.get(k).getDate(),
                            attendanceLogs.get(k).getPunchTime()
                    );
                    LocalDateTime checkOut = LocalDateTime.of(
                            attendanceLogs.get(k + 1).getDate(),
                            attendanceLogs.get(k + 1).getPunchTime()
                    );

                    int hours = checkOut.getHour() - checkIn.getHour();
                    int minutes = checkOut.getMinute() - checkIn.getMinute();
                    if (minutes < 0) {
                        hours -= 1;
                        minutes += 60;
                    }

                    totalWorkedMinutes += hours * 60 + minutes;
                }

                LocalDateTime firstIn = LocalDateTime.of(
                        attendanceLogs.get(0).getDate(),
                        attendanceLogs.get(0).getPunchTime()
                );
                LocalDateTime lastOut = LocalDateTime.of(
                        attendanceLogs.get(logSize - 1).getDate(),
                        attendanceLogs.get(logSize - 1).getPunchTime()
                );

                long totalDayMinutes = java.time.Duration.between(firstIn, lastOut).toMinutes();
                long freeMinutes = totalDayMinutes - totalWorkedMinutes;

                String workedTime = String.format("%02d:%02d", totalWorkedMinutes / 60, totalWorkedMinutes % 60);
                String dayTime = String.format("%02d:%02d", totalDayMinutes / 60, totalDayMinutes % 60);
                String freeTime = String.format("%02d:%02d", freeMinutes / 60, freeMinutes % 60);

                AttendanceData attendanceData = AttendanceData.builder()
                        .id(UtilsMethods.generateId())
                        .user(userId)
                        .date(logDate)
                        .firstPunchInTime(attendanceLogs.get(0).getPunchTime())
                        .lastPunchoutTime(attendanceLogs.get(logSize - 1).getPunchTime())
                        .isActive(true)
                        .isDeleted(false)
                        .attendanceStatus("present")
                        .valid(true)
                        .build();


                attendanceDataService.add(attendanceData);
            } else {
                AttendanceData absentData = AttendanceData.builder()
                        .id(UtilsMethods.generateId())
                        .user(userId)
                        .date(logDate)
                        .firstPunchInTime(null)
                        .lastPunchoutTime(null)
                        .isActive(true)
                        .isDeleted(false)
                        .attendanceStatus("absent")
                        .valid(false)
                        .build();
                attendanceDataService.add(absentData);
            }
        }

        List<AttendanceLogs> allLogs = repository.findAll();
        return allLogs.stream()
                .map(AttandanceLogsMapper::AttandenceLogToAttandenceLogResponse)
                .toList();
    }

    @Override
    public List<AttendanceRecord> getAllAttendance() {
        return attendanceList;
    }

    @Override
    public List<AttendanceLogsResponse> getAllLogsOfaUser(String id) {
        return List.of();
    }

    @Override
    public AttendanceDataResponse userAttendance(String userId,int month , int year)
    {
        AttendanceDataResponse response = new AttendanceDataResponse();

        List<AttendanceLogsResponse> attendanceLogs = getAllAttendanceOfaUserByMonth(userId,month,year);
        List<AttendanceList> attendanceLists = new ArrayList<>();

        ApiResponse<UserResponse> apiResponse = userClient.getByUserId(userId);
        UserResponse user = apiResponse.getData();


        long totalWorkMinutes = 0;
        long totalBreakMinutes = 0;
        int totalLeaves = 0;

        int i = 0;
        while (i < attendanceLogs.size())
        {
            String uId = attendanceLogs.get(i).getUserId();
            LocalDate currentDate = attendanceLogs.get(i).getDate();
            List<AttendanceLogsResponse> punchesForDay = new ArrayList<>();

            // collect all punches of same day
            while (i < attendanceLogs.size() && attendanceLogs.get(i).getDate().equals(currentDate))
            {
                punchesForDay.add(attendanceLogs.get(i));
                i++;
            }

            String status = evaluateStatus(punchesForDay);
            AttendanceList attendance = new AttendanceList();
            attendance.setDate(currentDate);
            attendance.setAttendanceStatus(status);
            attendanceLists.add(attendance);

            if (status.equals("On Leave"))
            {
                totalLeaves++;
            }

            // calculate work time & break time
            if (punchesForDay.size() % 2 == 0)
            {
                for (int j = 0; j < punchesForDay.size(); j += 2)
                {
                    LocalTime in = punchesForDay.get(j).getPunchTime();
                    LocalTime out = punchesForDay.get(j + 1).getPunchTime();
                    totalWorkMinutes += Duration.between(in, out).toMinutes();

                    if (j + 2 < punchesForDay.size())
                    {
                        LocalTime breakStart = punchesForDay.get(j + 1).getPunchTime();
                        LocalTime breakEnd = punchesForDay.get(j + 2).getPunchTime();
                        totalBreakMinutes += Duration.between(breakStart, breakEnd).toMinutes();
                    }
                }
            }
            response.setUserId(userId);
        }

        response.setUsername(user.getEmail());
        response.setAttendanceList(attendanceLists);
        response.setTotalWorkTime(formatMinutes(totalWorkMinutes));
        response.setTotalBreakTime(formatMinutes(totalBreakMinutes));
        response.setTotalLeaves(String.valueOf(totalLeaves));

        return response;
    }

    private String evaluateStatus(List<AttendanceLogsResponse> punches)
    {
        LocalDate date = punches.get(0).getDate();
        DayOfWeek day = date.getDayOfWeek();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
        {
            return "Holiday";
        }

        // odd punches ⇒ directly Absent
        if (punches.size() % 2 != 0)
        {
            return "Absent";
        }

        LocalTime firstPunch = punches.get(0).getPunchTime();
        LocalTime lastPunch = punches.get(punches.size() - 1).getPunchTime();

        if (firstPunch.isAfter(LATE_THRESHOLD))
        {
            return "Late";
        }

        if (punches.size() % 2 == 0)
        {
            if (lastPunch.isBefore(HALFDAY_THRESHOLD) || lastPunch.equals(HALFDAY_THRESHOLD))
            {
                return "Halfday";
            }
            if (firstPunch.isBefore(LATE_THRESHOLD) && lastPunch.isAfter(OFFICE_END))
            {
                return "Present";
            }
        }
        return "On Leave";
    }

    private String formatMinutes(long minutes)
    {
        long hours = minutes / 60;
        long mins = minutes % 60;
        return String.format("%dhr %dmin", hours, mins);
    }
}
