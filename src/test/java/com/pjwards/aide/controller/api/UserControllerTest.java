package com.pjwards.aide.controller.api;


import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.builder.UserBuilder;
import com.pjwards.aide.domain.enums.Role;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.user.UserService;
import com.pjwards.aide.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Date;

import static java.lang.Math.abs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class UserControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);
    private static final String NAME = "jisung";
    private static final String EMAIL = "a@a.com";
    private static final String PASSWORD = "4194105091094";
    private static final String COMPANY = "google";
    private static final Date DAY = new Date();
    private static final Date NEXT_DAY = new Date();
    private static final Role ROLE = Role.ADMIN;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(userService);
    }

    @Test
    public void testGetAll_UsersFound_ShouldReturnFoundUser() throws Exception {
        User first = new UserBuilder()
                .id(1L)
                .name("korea")
                .email("a@b.com")
                .password("12345")
                .company("facebook")
                .createdDate(DAY)
                .lastDate(DAY)
                .role(Role.USER)
                .build();

        User second = new UserBuilder()
                .id(2L)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userService.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("korea")))
                .andExpect(jsonPath("$[0].email", is("a@b.com")))
                .andExpect(jsonPath("$[0].password", is("12345")))
                .andExpect(jsonPath("$[0].company", is("facebook")))
                .andExpect(jsonPath("$[0].createdDate", is(TestUtil.convertUTCDateToGMTString(DAY))))
                .andExpect(jsonPath("$[0].lastDate", is(TestUtil.convertUTCDateToGMTString(DAY))))
                .andExpect(jsonPath("$[0].role", is(Role.USER.toString())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is(NAME)))
                .andExpect(jsonPath("$[1].email", is(EMAIL)))
                .andExpect(jsonPath("$[1].password", is(PASSWORD)))
                .andExpect(jsonPath("$[1].company", is(COMPANY)))
                .andExpect(jsonPath("$[1].createdDate", is(TestUtil.convertUTCDateToGMTString(DAY))))
                .andExpect(jsonPath("$[1].lastDate", is(TestUtil.convertUTCDateToGMTString(NEXT_DAY))))
                .andExpect(jsonPath("$[1].role", is(ROLE.toString())));

        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testCreate_NewUser_ShouldAddUserReturnAddedUser() throws Exception {
        User added = new UserBuilder()
                .id(1L)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userService.add(any(User.class))).thenReturn(added);

        mockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("User created successfully")))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.email", is(EMAIL)))
                .andExpect(jsonPath("$.user.password", is(PASSWORD)))
                .andExpect(jsonPath("$.user.company", is(COMPANY)))
                .andExpect(jsonPath("$.user.createdDate", is(TestUtil.convertUTCDateToGMTString(DAY))))
                .andExpect(jsonPath("$.user.lastDate", is(TestUtil.convertUTCDateToGMTString(NEXT_DAY))))
                .andExpect(jsonPath("$.user.role", is(ROLE.toString())));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).add(userArgumentCaptor.capture());
        verifyNoMoreInteractions(userService);

        User userArgument = userArgumentCaptor.getValue();
        assertThat(userArgument.getId(), is(1L));
        assertThat(userArgument.getName(), is(NAME));
        assertThat(userArgument.getEmail(), is(EMAIL));
        assertThat(userArgument.getPassword(), is(PASSWORD));
        assertThat(userArgument.getCompany(), is(COMPANY));
        assertTrue("Day dates aren't close enough to each other!",
                abs(userArgument.getCreatedDate().getTime() - DAY.getTime()) < 1000);
        assertTrue("Next day dates aren't close enough to each other!",
                abs(userArgument.getLastDate().getTime() - NEXT_DAY.getTime()) < 1000);
        assertThat(userArgument.getRole(), is(ROLE));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptyUser_ShouldOccurNoInteractionsWanted() throws Exception {
        User user = new User();

        mockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(userService);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_NameAndCompanyAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(User.MAX_LENGTH_NAME + 1);
        String company = TestUtil.createStringWithLength(User.MAX_LENGTH_COMPANY + 1);
        User user = new UserBuilder()
                .id(1L)
                .name(name)
                .email(EMAIL)
                .password(PASSWORD)
                .company(company)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(userService);
    }

    @Test
    public void testGetDetails_UserFound_ShouldReturnFoundUser() throws Exception {
        User found = new UserBuilder()
                .id(1L)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userService.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.email", is(EMAIL)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.company", is(COMPANY)))
                .andExpect(jsonPath("$.createdDate", is(TestUtil.convertUTCDateToGMTString(DAY))))
                .andExpect(jsonPath("$.lastDate", is(TestUtil.convertUTCDateToGMTString(NEXT_DAY))))
                .andExpect(jsonPath("$.role", is(ROLE.toString())));

        verify(userService, times(1)).findById(1L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetDetails_UserNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(userService.findById(1L)).thenThrow(new UserNotFoundException(""));

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).findById(1L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testUpdate_UserFound_ShouldUpdateRoomAndReturnIt() throws Exception {
        User updated = new UserBuilder()
                .id(1L)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userService.update(any(User.class))).thenReturn(updated);

        mockMvc.perform(put("/api/users/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("User updated successfully")))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.email", is(EMAIL)))
                .andExpect(jsonPath("$.user.password", is(PASSWORD)))
                .andExpect(jsonPath("$.user.company", is(COMPANY)))
                .andExpect(jsonPath("$.user.createdDate", is(TestUtil.convertUTCDateToGMTString(DAY))))
                .andExpect(jsonPath("$.user.lastDate", is(TestUtil.convertUTCDateToGMTString(NEXT_DAY))))
                .andExpect(jsonPath("$.user.role", is(ROLE.toString())));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).update(userArgumentCaptor.capture());
        verifyNoMoreInteractions(userService);

        User userArgument = userArgumentCaptor.getValue();
        assertThat(userArgument.getId(), is(1L));
        assertThat(userArgument.getName(), is(NAME));
        assertThat(userArgument.getEmail(), is(EMAIL));
        assertThat(userArgument.getPassword(), is(PASSWORD));
        assertThat(userArgument.getCompany(), is(COMPANY));
        assertTrue("Day dates aren't close enough to each other!",
                abs(userArgument.getCreatedDate().getTime() - DAY.getTime()) < 1000);
        assertTrue("Next day dates aren't close enough to each other!",
                abs(userArgument.getLastDate().getTime() - NEXT_DAY.getTime()) < 1000);
        assertThat(userArgument.getRole(), is(ROLE));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptyUser_ShouldOccurNoInteractionsWanted() throws Exception {
        User user = new UserBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/users/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(userService);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_NameAndCompanyAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(User.MAX_LENGTH_NAME + 1);
        String company = TestUtil.createStringWithLength(User.MAX_LENGTH_COMPANY + 1);
        User user = new UserBuilder()
                .id(1L)
                .name(name)
                .email(EMAIL)
                .password(PASSWORD)
                .company(company)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        mockMvc.perform(put("/api/users/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(userService);
    }

    @Test
    public void testUpdate_UserNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        User updated = new UserBuilder()
                .id(1L)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userService.update(any(User.class))).thenThrow(new UserNotFoundException(""));

        mockMvc.perform(put("/api/users/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).update(userArgumentCaptor.capture());
        verifyNoMoreInteractions(userService);

        User userArgument = userArgumentCaptor.getValue();
        assertThat(userArgument.getId(), is(1L));
        assertThat(userArgument.getName(), is(NAME));
        assertThat(userArgument.getEmail(), is(EMAIL));
        assertThat(userArgument.getPassword(), is(PASSWORD));
        assertThat(userArgument.getCompany(), is(COMPANY));
        assertTrue("Day dates aren't close enough to each other!",
                abs(userArgument.getCreatedDate().getTime() - DAY.getTime()) < 1000);
        assertTrue("Next day dates aren't close enough to each other!",
                abs(userArgument.getLastDate().getTime() - NEXT_DAY.getTime()) < 1000);
        assertThat(userArgument.getRole(), is(ROLE));
    }

    @Test
    public void testDelete_UserFound_ShouldDeleteUserAndReturnIt() throws Exception {
        User deleted = new UserBuilder()
                .id(1L)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .company(COMPANY)
                .createdDate(DAY)
                .lastDate(NEXT_DAY)
                .role(ROLE)
                .build();

        when(userService.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("User deleted successfully")))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.email", is(EMAIL)))
                .andExpect(jsonPath("$.user.password", is(PASSWORD)))
                .andExpect(jsonPath("$.user.company", is(COMPANY)))
                .andExpect(jsonPath("$.user.createdDate", is(TestUtil.convertUTCDateToGMTString(DAY))))
                .andExpect(jsonPath("$.user.lastDate", is(TestUtil.convertUTCDateToGMTString(NEXT_DAY))))
                .andExpect(jsonPath("$.user.role", is(ROLE.toString())));

        verify(userService, times(1)).deleteById(1L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testDelete_UserNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(userService.deleteById(3L)).thenThrow(new UserNotFoundException(""));

        mockMvc.perform(delete("/api/users/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).deleteById(3L);
        verifyNoMoreInteractions(userService);
    }
}
