package com.pjwards.aide.controller.api;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.domain.builder.ConferenceRoleBuilder;
import com.pjwards.aide.domain.enums.Role;
import com.pjwards.aide.exception.ConferenceRoleNotFoundException;
import com.pjwards.aide.service.conferencerole.ConferenceRoleService;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class ConferenceRoleControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);
    private static final Role ROLE = Role.ADMIN;

    private MockMvc mockMvc;

    @Qualifier("conferenceRoleService")
    @Autowired
    private ConferenceRoleService conferenceRoleService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(conferenceRoleService);
    }

    @Test
    public void testGetAll_ConferenceRolesFound_ShouldReturnFoundConferenceRole() throws Exception {
        ConferenceRole first = new ConferenceRoleBuilder()
                .id(1L)
                .conferenceRole(ROLE)
                .build();
        ConferenceRole second = new ConferenceRoleBuilder()
                .id(2L)
                .conferenceRole(Role.HOST)
                .build();

        when(conferenceRoleService.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/conference-roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].role", is(ROLE.toString())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].role", is(Role.HOST.toString())));

        verify(conferenceRoleService, times(1)).findAll();
        verifyNoMoreInteractions(conferenceRoleService);
    }

    @Test
    public void testCreate_NewConferenceRole_ShouldAddConferenceRoleReturnAddedConferenceRole() throws Exception {
        ConferenceRole added = new ConferenceRoleBuilder()
                .id(1L)
                .conferenceRole(ROLE)
                .build();

        when(conferenceRoleService.add(any(ConferenceRole.class))).thenReturn(added);

        mockMvc.perform(post("/api/conference-roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("ConferenceRole created successfully")))
                .andExpect(jsonPath("$.conferenceRole.id", is(1)))
                .andExpect(jsonPath("$.conferenceRole.role", is(ROLE.toString())));

        ArgumentCaptor<ConferenceRole> conferenceRoleArgumentCaptor = ArgumentCaptor.forClass(ConferenceRole.class);
        verify(conferenceRoleService, times(1)).add(conferenceRoleArgumentCaptor.capture());
        System.out.println(conferenceRoleArgumentCaptor.getAllValues());
        verifyNoMoreInteractions(conferenceRoleService);

        ConferenceRole conferenceRoleArgument = conferenceRoleArgumentCaptor.getValue();
        assertThat(conferenceRoleArgument.getId(), is(1L));
        assertThat(conferenceRoleArgument.getRole(), is(ROLE));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptyConferenceRole_ShouldOccurNoInteractionsWanted() throws Exception {
        ConferenceRole conferenceRole = new ConferenceRole();

        mockMvc.perform(post("/api/conference-roles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conferenceRole))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceRoleService);
    }

    @Test
    public void testGetDetails_ConferenceRoleFound_ShouldReturnFoundConferenceRole() throws Exception {
        ConferenceRole found = new ConferenceRoleBuilder()
                .id(1L)
                .conferenceRole(ROLE)
                .build();

        when(conferenceRoleService.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/conference-roles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.role", is(ROLE.toString())));

        verify(conferenceRoleService, times(1)).findById(1L);
        verifyNoMoreInteractions(conferenceRoleService);
    }

    @Test
    public void testGetDetails_ConferenceRoleNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(conferenceRoleService.findById(1L)).thenThrow(new ConferenceRoleNotFoundException(""));

        mockMvc.perform(get("/api/conference-roles/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(conferenceRoleService, times(1)).findById(1L);
        verifyNoMoreInteractions(conferenceRoleService);
    }

    @Test
    public void testUpdate_ConferenceRoleFound_ShouldUpdateConferenceRoleAndReturnIt() throws Exception {
        ConferenceRole updated = new ConferenceRoleBuilder()
                .id(1L)
                .conferenceRole(ROLE)
                .build();

        when(conferenceRoleService.update(any(ConferenceRole.class))).thenReturn(updated);

        mockMvc.perform(put("/api/conference-roles/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("ConferenceRole updated successfully")))
                .andExpect(jsonPath("$.conferenceRole.id", is(1)))
                .andExpect(jsonPath("$.conferenceRole.role", is(ROLE.toString())));

        ArgumentCaptor<ConferenceRole> conferenceRoleArgumentCaptor = ArgumentCaptor.forClass(ConferenceRole.class);
        verify(conferenceRoleService, times(1)).update(conferenceRoleArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceRoleService);

        ConferenceRole conferenceRoleArgument = conferenceRoleArgumentCaptor.getValue();
        assertThat(conferenceRoleArgument.getId(), is(1L));
        assertThat(conferenceRoleArgument.getRole(), is(updated.getRole()));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptyConferenceRole_ShouldOccurNoInteractionsWanted() throws Exception {
        ConferenceRole conferenceRole = new ConferenceRoleBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/conference-roles/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conferenceRole))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(conferenceRoleService);
    }

    @Test
    public void testUpdate_ConferenceRoleNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        ConferenceRole updated = new ConferenceRoleBuilder()
                .id(1L)
                .conferenceRole(ROLE)
                .build();

        when(conferenceRoleService.update(any(ConferenceRole.class))).thenThrow(new ConferenceRoleNotFoundException(""));

        mockMvc.perform(put("/api/conference-roles/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<ConferenceRole> conferenceRoleArgumentCaptor = ArgumentCaptor.forClass(ConferenceRole.class);
        verify(conferenceRoleService, times(1)).update(conferenceRoleArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceRoleService);

        ConferenceRole conferenceRoleArgument = conferenceRoleArgumentCaptor.getValue();
        assertThat(conferenceRoleArgument.getId(), is(1L));
        assertThat(conferenceRoleArgument.getRole(), is(updated.getRole()));
    }

    @Test
    public void testDelete_ConferenceRoleFound_ShouldDeleteConferenceRoleAndReturnIt() throws Exception {
        ConferenceRole deleted = new ConferenceRoleBuilder()
                .id(1L)
                .conferenceRole(ROLE)
                .build();

        when(conferenceRoleService.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/conference-roles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("ConferenceRole deleted successfully")))
                .andExpect(jsonPath("$.conferenceRole.id", is(1)))
                .andExpect(jsonPath("$.conferenceRole.role", is(ROLE.toString())));

        verify(conferenceRoleService, times(1)).deleteById(1L);
        verifyNoMoreInteractions(conferenceRoleService);
    }

    @Test
    public void testDelete_ConferenceRoleNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(conferenceRoleService.deleteById(3L)).thenThrow(new ConferenceRoleNotFoundException(""));

        mockMvc.perform(delete("/api/conference-roles/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(conferenceRoleService, times(1)).deleteById(3L);
        verifyNoMoreInteractions(conferenceRoleService);
    }

}
