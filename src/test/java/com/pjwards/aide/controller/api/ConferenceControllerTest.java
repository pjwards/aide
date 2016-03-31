package com.pjwards.aide.controller.api;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Conference;
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
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class ConferenceControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceControllerTest.class);

    private MockMvc mockMvc;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(conferenceService);
    }

    @Test
    public void testGetAll_ConferencesFound_ShoudReturnFoundConference() throws Exception {
        Conference first = new Conference.Builder("name1", "description1").id(1L).build();
        Conference second = new Conference.Builder("name2", "description2").id(2L).build();

        when(conferenceService.findAll()).thenReturn(Arrays.asList(first, second));

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

        verify(conferenceService, times(1)).findAll();
        verifyNoMoreInteractions(conferenceService);
    }

    @Test
    public void testCreate_NewConference_ShouldAddConferenceReturnAddedConference() throws Exception {
        Conference added = new Conference.Builder("name", "description").id(1L).build();

        when(conferenceService.add(any(Conference.class))).thenReturn(added);

        mockMvc.perform(post("/api/conferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Conference created successfully")))
                .andExpect(jsonPath("$.conference.id", is(1)))
                .andExpect(jsonPath("$.conference.name", is("name")))
                .andExpect(jsonPath("$.conference.description", is("description")));

        ArgumentCaptor<Conference> conferenceArgumentCaptor = ArgumentCaptor.forClass(Conference.class);
        verify(conferenceService, times(1)).add(conferenceArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceService);

        Conference conferenceArgument = conferenceArgumentCaptor.getValue();
        assertThat(conferenceArgument.getId(), is(1L));
        assertThat(conferenceArgument.getName(), is("name"));
        assertThat(conferenceArgument.getDescription(), is("description"));
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

        verifyZeroInteractions(conferenceService);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_NameAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(Conference.MAX_LENGTH_NAME + 1);
        Conference conference = new Conference.Builder(name, "description").build();

        mockMvc.perform(post("/api/conferences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conference))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceService);
    }

    @Test
    public void testGetDetails_ConferenceFound_ShouldReturnFoundConference() throws Exception {
        Conference found = new Conference.Builder("name", "description").id(1L).build();

        when(conferenceService.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/conferences/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.description", is("description")));

        verify(conferenceService, times(1)).findById(1L);
        verifyNoMoreInteractions(conferenceService);
    }

    @Test
    public void testGetDetails_ConferenceNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(conferenceService.findById(1L)).thenThrow(new ConferenceNotFoundException(""));

        mockMvc.perform(get("/api/conferences/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(conferenceService, times(1)).findById(1L);
        verifyNoMoreInteractions(conferenceService);
    }

    @Test
    public void testUpdate_ConferenceFound_ShouldUpdateConferenceAndReturnIt() throws Exception {
        Conference updated = new Conference.Builder("name", "description").id(1L).build();

        when(conferenceService.update(any(Conference.class))).thenReturn(updated);

        mockMvc.perform(put("/api/conferences/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Conference updated successfully")))
                .andExpect(jsonPath("$.conference.id", is(1)))
                .andExpect(jsonPath("$.conference.name", is("name")))
                .andExpect(jsonPath("$.conference.description", is("description")));

        ArgumentCaptor<Conference> conferenceArgumentCaptor = ArgumentCaptor.forClass(Conference.class);
        verify(conferenceService, times(1)).update(conferenceArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceService);

        Conference conferenceArgument = conferenceArgumentCaptor.getValue();
        assertThat(conferenceArgument.getId(), is(1L));
        assertThat(conferenceArgument.getName(), is("name"));
        assertThat(conferenceArgument.getDescription(), is("description"));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptyConference_ShouldOccurNoInteractionsWanted() throws Exception {
        Conference conference = new Conference.Builder(null, null).id(1L).build();

        mockMvc.perform(put("/api/conferences/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conference))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceService);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_NameAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(Conference.MAX_LENGTH_NAME + 1);
        Conference conference = new Conference.Builder(name, "description").id(1L).build();

        mockMvc.perform(put("/api/conferences/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conference))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceService);
    }

    @Test
    public void testUpdate_ConferenceNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Conference updated = new Conference.Builder("name", "description").id(3L).build();

        when(conferenceService.update(any(Conference.class))).thenThrow(new ConferenceNotFoundException(""));

        mockMvc.perform(put("/api/conferences/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Conference> conferenceArgumentCaptor = ArgumentCaptor.forClass(Conference.class);
        verify(conferenceService, times(1)).update(conferenceArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceService);

        Conference conferenceArgument = conferenceArgumentCaptor.getValue();
        assertThat(conferenceArgument.getId(), is(3L));
        assertThat(conferenceArgument.getName(), is("name"));
        assertThat(conferenceArgument.getDescription(), is("description"));
    }

    @Test
    public void testDelete_ConferenceFound_ShouldDeleteConferenceAndReturnIt() throws Exception {
        Conference deleted = new Conference.Builder("name", "description").id(1L).build();

        when(conferenceService.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/conferences/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.conference.id", is(1)))
                .andExpect(jsonPath("$.conference.name", is("name")))
                .andExpect(jsonPath("$.conference.description", is("description")));

        verify(conferenceService, times(1)).deleteById(1L);
        verifyNoMoreInteractions(conferenceService);
    }

    @Test
    public void testDelete_ConferenceNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(conferenceService.deleteById(3L)).thenThrow(new ConferenceNotFoundException(""));

        mockMvc.perform(delete("/api/conferences/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(conferenceService, times(1)).deleteById(3L);
        verifyNoMoreInteractions(conferenceService);
    }
}
