package com.ridematefinder.controllers;
import com.ridematefinder.controllers.UserController;
import com.ridematefinder.repository.PictureRepository;
import com.ridematefinder.repository.UserRepository;
import com.ridematefinder.sql.Pictures;
import com.ridematefinder.sql.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userRepository, pictureRepository);
    }

    @Test
    void showProfileForm() {
        String viewName = userController.showProfileForm(model);
        assertEquals("profileForm", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any(User.class));
    }

    @Test
    void addUserData_noErrors() throws IOException {
        User user = new User();
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(pictureRepository.save(any(Pictures.class))).thenReturn(new Pictures());
        when(userRepository.save(any(User.class))).thenReturn(user);

        String viewName = userController.addUserData(user, bindingResult, file, model);

        assertEquals("index", viewName);
        verify(pictureRepository, times(1)).save(any(Pictures.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void addUserData_withErrors() throws IOException {
        User user = new User();
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.addUserData(user, bindingResult, file, model);

        assertEquals("profileForm", viewName);
        verifyNoInteractions(pictureRepository);
        verifyNoInteractions(userRepository);
    }
}
