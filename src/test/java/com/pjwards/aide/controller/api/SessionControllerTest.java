package com.pjwards.aide.controller.api;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.config.TestConfig;
import com.pjwards.aide.domain.Session;
import com.pjwards.aide.domain.builder.SessionBuilder;
import com.pjwards.aide.exception.SessionNotFoundException;
import com.pjwards.aide.service.session.SessionService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class SessionControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionControllerTest.class);
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    private MockMvc mockMvc;

    @Qualifier("sessionService")
    @Autowired
    private SessionService sessionServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(sessionServiceMock);
    }

    @Test
    public void testGetAll_SessionsFound_ShoudReturnFoundSession() throws Exception {
        Session first = new SessionBuilder()
                .id(1L)
                .title("title1")
                .description("description1")
                .build();
        Session second = new SessionBuilder()
                .id(2L)
                .title("title2")
                .description("description2")
                .build();

        when(sessionServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/sessions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("title2")))
                .andExpect(jsonPath("$[1].description", is("description2")));

        verify(sessionServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(sessionServiceMock);
    }

    @Test
    public void testCreate_NewSession_ShouldAddSessionReturnAddedSession() throws Exception {
        Session added = new SessionBuilder()
                .id(1L)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        when(sessionServiceMock.add(any(Session.class))).thenReturn(added);

        mockMvc.perform(post("/api/sessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Session created successfully")))
                .andExpect(jsonPath("$.session.id", is(1)))
                .andExpect(jsonPath("$.session.title", is(TITLE)))
                .andExpect(jsonPath("$.session.description", is(DESCRIPTION)));

        ArgumentCaptor<Session> sessionArgumentCaptor = ArgumentCaptor.forClass(Session.class);
        verify(sessionServiceMock, times(1)).add(sessionArgumentCaptor.capture());
        verifyNoMoreInteractions(sessionServiceMock);

        Session sessionArgument = sessionArgumentCaptor.getValue();
        assertThat(sessionArgument.getId(), is(1L));
        assertThat(sessionArgument.getTitle(), is(TITLE));
        assertThat(sessionArgument.getDescription(), is(DESCRIPTION));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptySession_ShouldOccurNoInteractionsWanted() throws Exception {
        Session session = new Session();

        mockMvc.perform(post("/api/sessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(session))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(sessionServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_TitleAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String title = TestUtil.createStringWithLength(Session.MAX_LENGTH_TITLE + 1);
        Session session = new SessionBuilder()
                .title(title)
                .description(DESCRIPTION)
                .build();

        mockMvc.perform(post("/api/sessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(session))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(sessionServiceMock);
    }

    @Test
    public void testGetDetails_SessionFound_ShouldReturnFoundSession() throws Exception {
        Session found = new SessionBuilder()
                .id(1L)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        when(sessionServiceMock.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/sessions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(TITLE)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));

        verify(sessionServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(sessionServiceMock);
    }

    @Test
    public void testGetDetails_SessionNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(sessionServiceMock.findById(1L)).thenThrow(new SessionNotFoundException(""));

        mockMvc.perform(get("/api/sessions/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(sessionServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(sessionServiceMock);
    }

    @Test
    public void testUpdate_SessionFound_ShouldUpdateSessionAndReturnIt() throws Exception {
        Session updated = new SessionBuilder()
                .id(1L)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        when(sessionServiceMock.update(any(Session.class))).thenReturn(updated);

        mockMvc.perform(put("/api/sessions/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Session updated successfully")))
                .andExpect(jsonPath("$.session.id", is(1)))
                .andExpect(jsonPath("$.session.title", is(TITLE)))
                .andExpect(jsonPath("$.session.description", is(DESCRIPTION)));

        ArgumentCaptor<Session> sessionArgumentCaptor = ArgumentCaptor.forClass(Session.class);
        verify(sessionServiceMock, times(1)).update(sessionArgumentCaptor.capture());
        verifyNoMoreInteractions(sessionServiceMock);

        Session sessionArgument = sessionArgumentCaptor.getValue();
        assertThat(sessionArgument.getId(), is(1L));
        assertThat(sessionArgument.getTitle(), is(TITLE));
        assertThat(sessionArgument.getDescription(), is(DESCRIPTION));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptySession_ShouldOccurNoInteractionsWanted() throws Exception {
        Session session = new SessionBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/sessions/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(session))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(sessionServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_TitleAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String title = TestUtil.createStringWithLength(Session.MAX_LENGTH_TITLE + 1);
        Session session = new SessionBuilder()
                .id(1L)
                .title(title)
                .description(DESCRIPTION)
                .build();

        mockMvc.perform(put("/api/sessions/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(session))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(sessionServiceMock);
    }

    @Test
    public void testUpdate_SessionNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Session updated = new SessionBuilder()
                .id(3L)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        when(sessionServiceMock.update(any(Session.class))).thenThrow(new SessionNotFoundException(""));

        mockMvc.perform(put("/api/sessions/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Session> sessionArgumentCaptor = ArgumentCaptor.forClass(Session.class);
        verify(sessionServiceMock, times(1)).update(sessionArgumentCaptor.capture());
        verifyNoMoreInteractions(sessionServiceMock);

        Session sessionArgument = sessionArgumentCaptor.getValue();
        assertThat(sessionArgument.getId(), is(3L));
        assertThat(sessionArgument.getTitle(), is(TITLE));
        assertThat(sessionArgument.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testDelete_SessionFound_ShouldDeleteSessionAndReturnIt() throws Exception {
        Session deleted = new SessionBuilder()
                .id(1L)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        when(sessionServiceMock.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/sessions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Session deleted successfully")))
                .andExpect(jsonPath("$.session.id", is(1)))
                .andExpect(jsonPath("$.session.title", is(TITLE)))
                .andExpect(jsonPath("$.session.description", is(DESCRIPTION)));

        verify(sessionServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(sessionServiceMock);
    }

    @Test
    public void testDelete_SessionNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(sessionServiceMock.deleteById(3L)).thenThrow(new SessionNotFoundException(""));

        mockMvc.perform(delete("/api/sessions/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(sessionServiceMock, times(1)).deleteById(3L);
        verifyNoMoreInteractions(sessionServiceMock);
    }
}
