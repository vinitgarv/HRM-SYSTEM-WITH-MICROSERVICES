//package com.moonstack.serviceImpl;
//
//import com.moonstack.constants.Message;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthServiceImplTest
//{
//    @InjectMocks
//    private AuthServiceImpl authService;
//
//    @Mock
//    private SessionLogsServiceImpl sessionLoginService;
//
////    @Mock
////    private ServletContext servletContext;
//
////    @BeforeEach
////    void setUp()
////    {
////        when(servletContext.getAttribute("username")).thenReturn("fake_user");
////    }
//
//    @Test
//    void testLogin_success()
//    {
//        String result = authService.login(2);
//
//        assertEquals(Message.SUCCESS,result);
//        verify(sessionLoginService,times(1)).add(any(SessionLoginRequest.class));
//    }
//
//    @Test
//    void testLogin_failed()
//    {
//        String result = authService.login(3);
//
//        assertEquals(Message.FAILED,result);
//        verify(sessionLoginService,times(1)).add(any(SessionLoginRequest.class));
//    }
//
//    @Test
//    void testLogout_success()
//    {
//        String result = authService.logout(2);
//
//        assertEquals(Message.SUCCESS,result);
//        verify(sessionLoginService,times(1)).add(any(SessionLoginRequest.class));
//    }
//
//    @Test
//    void testLogout_failed()
//    {
//        String result = authService.logout(3);
//
//        assertEquals(Message.FAILED,result);
//        verify(sessionLoginService,times(1)).add(any(SessionLoginRequest.class));
//    }
//}
