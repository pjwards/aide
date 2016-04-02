package com.pjwards.aide.service.user;

import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.builder.UserBuilder;
import com.pjwards.aide.domain.enums.Role;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private static final Long ID = 1L;
    private static final String NAME = "jisung";
    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "4194105091094";
    private static final String COMPANY = "google";
    private static final String DAY = "2016-04-01";
    private static final String NEXT_DAY = "2016-04-02";
    private static final Role ROLE = Role.ADMIN;
    private static final String UPDATE_NAME = "seodong";
    private static final String UPDATE_EMAIL = "a@b.com";
    private static final String UPDATE_PASS_WOARD = "1234567";
    private static final String UPDATE_COMPANY = "facebook";
    private static final String UPDATE_LAST_DAY = "2016-04-03";
    private static final Role UPDATE_ROLE = Role.USER;

    private DateFormat formatter;

    private UserRepository userRepositoryMock;
    private UserService userService;

    @Before
    public void setup(){
        userRepositoryMock = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfUser(){
        List<User> models = new ArrayList<>();
        when(userRepositoryMock.findAll()).thenReturn(models);

        List<User> actual = userService.findAll();

        verify(userRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(userRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewUser_ShouldSaveUser() throws ParseException{
        User user = new UserBuilder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        userService.add(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());
        verifyNoMoreInteractions(userRepositoryMock);

        User model = userArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getName(), is(NAME));
        assertThat(model.getEmail(), is(EMAIL));
        assertThat(model.getPassword(), is(PASSWORD));
        assertThat(model.getCompany(), is(COMPANY));
        assertThat(model.getCreatedDate(), is(formatter.parse(DAY)));
        assertThat(model.getLastDate(), is(formatter.parse(NEXT_DAY)));
        assertThat(model.getRole(), is(ROLE));
    }

    @Test
    public void testFindById_UserFound_ShouldReturnFoundUser() throws UserNotFoundException, ParseException{
        User model = new UserBuilder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userRepositoryMock.findOne(ID)).thenReturn(model);

        User actual = userService.findById(ID);

        verify(userRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(userRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindById_UserNotFound_ShouldThrowException() throws UserNotFoundException{
        when(userRepositoryMock.findOne(ID)).thenReturn(null);

        userService.findById(ID);

        verify(userRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void testUpdate_UserFound_ShouldUpdateUser() throws UserNotFoundException, ParseException{
        User updated = new UserBuilder()
                .id(ID)
                .name(UPDATE_NAME)
                .email(UPDATE_EMAIL)
                .password(UPDATE_PASS_WOARD)
                .company(UPDATE_COMPANY)
                .createdDate(DAY)
                .lastDate(UPDATE_LAST_DAY)
                .role(UPDATE_ROLE)
                .build();

        User model = new UserBuilder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userRepositoryMock.findOne(updated.getId())).thenReturn(model);

        userService.update(updated);

        verify(userRepositoryMock, times(1)).findOne(model.getId());
        verify(userRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(userRepositoryMock);


        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getName(), is(updated.getName()));
        assertThat(model.getEmail(), is(updated.getEmail()));
        assertThat(model.getPassword(), is(updated.getPassword()));
        assertThat(model.getCompany(), is(updated.getCompany()));
        assertThat(model.getCreatedDate(), is(updated.getCreatedDate()));
        assertThat(model.getLastDate(), is(updated.getLastDate()));
        assertThat(model.getRole(), is(updated.getRole()));
    }

    @Test(expected = UserNotFoundException.class)
    public void testUpdate_UserNotFound_ShouldThrowException() throws UserNotFoundException, ParseException{
        User updated = new UserBuilder()
                .id(ID)
                .name(UPDATE_NAME)
                .email(UPDATE_EMAIL)
                .password(UPDATE_PASS_WOARD)
                .company(UPDATE_COMPANY)
                .createdDate(DAY)
                .lastDate(UPDATE_LAST_DAY)
                .role(UPDATE_ROLE)
                .build();

        when(userRepositoryMock.findOne(updated.getId())).thenReturn(null);

        userService.update(updated);

        verify(userRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void testDeleteById_UserFound_ShouldDeleteUserAndReturnIt() throws UserNotFoundException, ParseException{
        User model = new UserBuilder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userRepositoryMock.findOne(ID)).thenReturn(model);

        User actual = userService.deleteById(ID);

        verify(userRepositoryMock, times(1)).findOne(ID);
        verify(userRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(userRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = UserNotFoundException.class)
    public void testDeleteById_UserNotFound_ShouldThrowException() throws UserNotFoundException{
        when(userRepositoryMock.findOne(ID)).thenReturn(null);

        userService.deleteById(ID);

        verify(userRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(userRepositoryMock);
    }
}
