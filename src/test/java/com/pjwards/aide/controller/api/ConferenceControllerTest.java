package com.pjwards.aide.controller.api;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.config.TestConfig;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.builder.ConferenceBuilder;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class ConferenceControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceControllerTest.class);
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    private MockMvc mockMvc;

    @Qualifier("conferenceService")
    @Autowired
    private ConferenceService conferenceServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(conferenceServiceMock);
    }

    @Test
    public void testGetAll_ConferencesFound_ShoudReturnFoundConference() throws Exception {
        Conference first = new ConferenceBuilder()
                .id(1L)
                .name("name1")
                .description("description1")
                .build();
        Conference second = new ConferenceBuilder()
                .id(2L)
                .name("name2")
                .description("description2")
                .build();

        when(conferenceServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/conferences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[1].description", is("description2")));

        verify(conferenceServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(conferenceServiceMock);
    }

    @Test
    public void testCreate_NewConference_ShouldAddConferenceReturnAddedConference() throws Exception {
        Conference added = new ConferenceBuilder()
                .id(1L)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        when(conferenceServiceMock.add(any(Conference.class))).thenReturn(added);

        mockMvc.perform(post("/api/conferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Conference created successfully")))
                .andExpect(jsonPath("$.conference.id", is(1)))
                .andExpect(jsonPath("$.conference.name", is(NAME)))
                .andExpect(jsonPath("$.conference.description", is(DESCRIPTION)));

        ArgumentCaptor<Conference> conferenceArgumentCaptor = ArgumentCaptor.forClass(Conference.class);
        verify(conferenceServiceMock, times(1)).add(conferenceArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceServiceMock);

        Conference conferenceArgument = conferenceArgumentCaptor.getValue();
        assertThat(conferenceArgument.getId(), is(1L));
        assertThat(conferenceArgument.getName(), is(NAME));
        assertThat(conferenceArgument.getDescription(), is(DESCRIPTION));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptyConference_ShouldOccurNoInteractionsWanted() throws Exception {
        Conference conference = new Conference();

        mockMvc.perform(post("/api/conferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conference))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_NameAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(Conference.MAX_LENGTH_NAME + 1);
        Conference conference = new ConferenceBuilder()
                .name(name)
                .description(DESCRIPTION)
                .build();

        mockMvc.perform(post("/api/conferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conference))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceServiceMock);
    }

    @Test
    public void testGetDetails_ConferenceFound_ShouldReturnFoundConference() throws Exception {
        Conference found = new ConferenceBuilder()
                .id(1L)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        when(conferenceServiceMock.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/conferences/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));

        verify(conferenceServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(conferenceServiceMock);
    }

    @Test
    public void testGetDetails_ConferenceNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(conferenceServiceMock.findById(1L)).thenThrow(new ConferenceNotFoundException(""));

        mockMvc.perform(get("/api/conferences/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(conferenceServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(conferenceServiceMock);
    }

    @Test
    public void testUpdate_ConferenceFound_ShouldUpdateConferenceAndReturnIt() throws Exception {
        Conference updated = new ConferenceBuilder()
                .id(1L)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        when(conferenceServiceMock.update(any(Conference.class))).thenReturn(updated);

        mockMvc.perform(put("/api/conferences/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Conference updated successfully")))
                .andExpect(jsonPath("$.conference.id", is(1)))
                .andExpect(jsonPath("$.conference.name", is(NAME)))
                .andExpect(jsonPath("$.conference.description", is(DESCRIPTION)));

        ArgumentCaptor<Conference> conferenceArgumentCaptor = ArgumentCaptor.forClass(Conference.class);
        verify(conferenceServiceMock, times(1)).update(conferenceArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceServiceMock);

        Conference conferenceArgument = conferenceArgumentCaptor.getValue();
        assertThat(conferenceArgument.getId(), is(1L));
        assertThat(conferenceArgument.getName(), is(NAME));
        assertThat(conferenceArgument.getDescription(), is(DESCRIPTION));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptyConference_ShouldOccurNoInteractionsWanted() throws Exception {
        Conference conference = new ConferenceBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/conferences/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conference))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_NameAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(Conference.MAX_LENGTH_NAME + 1);
        Conference conference = new ConferenceBuilder()
                .id(1L)
                .name(name)
                .description(DESCRIPTION)
                .build();

        mockMvc.perform(put("/api/conferences/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conference))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceServiceMock);
    }

    @Test
    public void testUpdate_ConferenceNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Conference updated = new ConferenceBuilder()
                .id(3L)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        when(conferenceServiceMock.update(any(Conference.class))).thenThrow(new ConferenceNotFoundException(""));

        mockMvc.perform(put("/api/conferences/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Conference> conferenceArgumentCaptor = ArgumentCaptor.forClass(Conference.class);
        verify(conferenceServiceMock, times(1)).update(conferenceArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceServiceMock);

        Conference conferenceArgument = conferenceArgumentCaptor.getValue();
        assertThat(conferenceArgument.getId(), is(3L));
        assertThat(conferenceArgument.getName(), is(NAME));
        assertThat(conferenceArgument.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testDelete_ConferenceFound_ShouldDeleteConferenceAndReturnIt() throws Exception {
        Conference deleted = new ConferenceBuilder()
                .id(1L)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        when(conferenceServiceMock.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/conferences/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Conference deleted successfully")))
                .andExpect(jsonPath("$.conference.id", is(1)))
                .andExpect(jsonPath("$.conference.name", is(NAME)))
                .andExpect(jsonPath("$.conference.description", is(DESCRIPTION)));

        verify(conferenceServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(conferenceServiceMock);
    }

    @Test
    public void testDelete_ConferenceNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(conferenceServiceMock.deleteById(3L)).thenThrow(new ConferenceNotFoundException(""));

        mockMvc.perform(delete("/api/conferences/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(conferenceServiceMock, times(1)).deleteById(3L);
        verifyNoMoreInteractions(conferenceServiceMock);
    }
}
