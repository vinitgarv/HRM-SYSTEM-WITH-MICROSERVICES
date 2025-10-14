//package com.moonstack.serviceImpl;
//
//import com.moonstack.constants.Message;
//import com.moonstack.dtos.response.SessionLogsResponse;
//import com.moonstack.entity.SessionLogs;
//import com.moonstack.exception.NotFoundException;
//import com.moonstack.repository.SessionLogsRepository;
//import com.moonstack.response.PageResponse;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class SessionLoginServiceImplTest
//{
//    @Mock
//    private SessionLogsRepository sessionLoginRepository;
//
//    @InjectMocks
//    private SessionLogsServiceImpl sessionLoginService;
//
//    @Test
//    void testAdd_success()
//    {
//        SessionLoginRequest request = SessionLoginRequest.builder()
//                .username("fake_user")
//                .action("Login")
//                .build();
//
//        SessionLogs sessionLogin = new SessionLogs();
//        when(sessionLoginRepository.save(any(SessionLogs.class))).thenReturn(sessionLogin);
//
//        String result = sessionLoginService.add(request);
//
//        assertEquals(Message.SESSION+Message.TAB+Message.SAVED+Message.TAB+Message.SUCCESSFULLY+Message.DOT,result);
//        verify(sessionLoginRepository,times(1)).save(any(SessionLogs.class));
//    }
//
//    @Test
//    void testGetAll_withPagenation()
//    {
//        SessionLogs sessionLogin = new SessionLogs();
//
//        Page<SessionLogs> page = new PageImpl<>(List.of(sessionLogin));
//
//        when(sessionLoginRepository.findAll(any(Pageable.class))).thenReturn(page);
//
//        PageResponse<SessionLogsResponse> response = sessionLoginService.getAll(0,5);
//
//        assertNotNull(response);
//        assertEquals(1,response.getElements());
//        assertEquals(1,response.getTotalPages());
//        verify(sessionLoginRepository,times(1)).findAll(any(Pageable.class));
//    }
//
//    @Test
//    void testGetAll_withoutPagenation()
//    {
//            when(sessionLoginRepository.findAll()).thenReturn(List.of(new SessionLogs()));
//
//            PageResponse<SessionLogsResponse> response = sessionLoginService.getAll(null,null);
//
//            assertNotNull(response);
//            assertEquals(1,response.getElements());
//            verify(sessionLoginRepository,times(1)).findAll();
//    }
//
//    @Test
//    void testGetById_success()
//    {
//            SessionLogs sessionLogin = SessionLogs.builder()
//                    .id("123")
//                    .build();
//
//            when(sessionLoginRepository.findById("123")).thenReturn(Optional.of(sessionLogin));
//
//            SessionLogsResponse result = sessionLoginService.getById("123");
//
//            assertNotNull(result);
//            verify(sessionLoginRepository,times(1)).findById("123");
//    }
//
//    @Test
//    void testGetById_NotFound()
//    {
//        when(sessionLoginRepository.findById("123")).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class,() -> sessionLoginService.getById("123"));
//    }
//
//}
