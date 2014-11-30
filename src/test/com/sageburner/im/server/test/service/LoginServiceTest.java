package com.sageburner.im.server.test.service;

import com.sageburner.im.server.dao.UserDao;
import com.sageburner.im.server.model.User;
import com.sageburner.im.server.service.LoginServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

/**
 * Created by Ryan on 10/17/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private LoginServiceImpl loginService;

//    @Before
//    public void doSetup() {
//        userDao = mock(UserDao.class);
//        loginService = new LoginServiceImpl();
//    }

    @Test
    public void testSaveForm() {

        String username = "user1@sageburner.com";

        when(userDao.addUser(any(User.class)))
                .thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocation) throws Throwable {
                User user = (User) invocation.getArguments()[0];
                user = initializeUser(user);
                return user;
            }
        });

        assertNull(userDao.getUserByUsername(username));

        User testUser = new User();
        testUser.setUsername(username);
        userDao.addUser(testUser);

        testUser = userDao.getUserByUsername(username);
        assertNotNull(testUser);
        assertTrue(Integer.valueOf(testUser.getId()) > 0);
    }

    private User initializeUser(User user) {
        user.setId("1");
        ///user.setUsername("user1@sageburner.com");
        user.setPassword("pass");
        user.setFirstName("Android");
        user.setLastName("Bootstrap");
        user.setPhone("555-555-5555");
        user.setSessionToken("xkygcf502aav0ay54m7xkphvd");
        user.setPublicKey("");
        Calendar cal = Calendar.getInstance();
        cal.set(2012, 7, 10, 21, 38, 51);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        user.setCreatedAt(sdf.format(cal.getTime()));
        cal.set(2012, 8, 3, 3, 32, 56);
        user.setUpdatedAt(sdf.format(cal.getTime()));

        return user;
    }
}
