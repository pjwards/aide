package com.pjwards.aide.controller.api;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.config.TestConfig;
import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.domain.builder.ProgramDateBuilder;
import com.pjwards.aide.exception.ProgramDateNotFoundException;
import com.pjwards.aide.service.programdate.ProgramDateService;
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
public class ProgramDateControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateControllerTest.class);
    private static final String NAME = "name";
    private static final String DAY = "2016-01-01";

    private MockMvc mockMvc;

    @Qualifier("programDateService")
    @Autowired
    private ProgramDateService programDateServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(programDateServiceMock);
    }

    @Test
    public void testGetAll_ProgramDatesFound_ShoudReturnFoundProgramDate() throws Exception {
        ProgramDate first = new ProgramDateBuilder()
                .id(1L)
                .name("name1")
                .day("2016-01-01")
                .build();
        ProgramDate second = new ProgramDateBuilder()
                .id(2L)
                .name("name2")
                .day("2016-01-02")
                .build();

        when(programDateServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/program-dates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[0].day", is(TestUtil.convertUTCDateToGMTString(first.getDay()))))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[1].day", is(TestUtil.convertUTCDateToGMTString(second.getDay()))));

        verify(programDateServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(programDateServiceMock);
    }

    @Test
    public void testCreate_NewProgramDate_ShouldAddProgramDateReturnAddedProgramDate() throws Exception {
        ProgramDate added = new ProgramDateBuilder()
                .id(1L)
                .name(NAME)
                .day(DAY)
                .build();

        when(programDateServiceMock.add(any(ProgramDate.class))).thenReturn(added);

        mockMvc.perform(post("/api/program-dates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Program date created successfully")))
                .andExpect(jsonPath("$.programDate.id", is(1)))
                .andExpect(jsonPath("$.programDate.name", is(NAME)))
                .andExpect(jsonPath("$.programDate.day", is(TestUtil.convertUTCDateToGMTString(added.getDay()))));

        ArgumentCaptor<ProgramDate> programDateArgumentCaptor = ArgumentCaptor.forClass(ProgramDate.class);
        verify(programDateServiceMock, times(1)).add(programDateArgumentCaptor.capture());
        verifyNoMoreInteractions(programDateServiceMock);

        ProgramDate programDateArgument = programDateArgumentCaptor.getValue();
        assertThat(programDateArgument.getId(), is(1L));
        assertThat(programDateArgument.getName(), is(added.getName()));
        assertThat(programDateArgument.getDay(), is(added.getDay()));
    }

    @Test(expected = JsonMappingException.class)
    public void testCreate_EmptyProgramDate_ShouldOccurNoInteractionsWanted() throws Exception {
        ProgramDate programDate = new ProgramDate();

        mockMvc.perform(post("/api/program-dates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programDate))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(programDateServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_StringAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(ProgramDate.MAX_LENGTH_NAME + 1);

        ProgramDate programDate = new ProgramDateBuilder()
                .name(name)
                .day(DAY)
                .build();

        mockMvc.perform(post("/api/program-dates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programDate))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(programDateServiceMock);
    }

    @Test
    public void testGetDetails_ProgramDateFound_ShouldReturnFoundProgramDate() throws Exception {
        ProgramDate found = new ProgramDateBuilder()
                .id(1L)
                .name(NAME)
                .day(DAY)
                .build();
        ;

        when(programDateServiceMock.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/program-dates/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.day", is(TestUtil.convertUTCDateToGMTString(found.getDay()))));

        verify(programDateServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(programDateServiceMock);
    }

    @Test
    public void testGetDetails_ProgramDateNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(programDateServiceMock.findById(1L)).thenThrow(new ProgramDateNotFoundException(""));

        mockMvc.perform(get("/api/program-dates/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(programDateServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(programDateServiceMock);
    }

    @Test
    public void testUpdate_ProgramDateFound_ShouldUpdateProgramDateAndReturnIt() throws Exception {
        ProgramDate updated = new ProgramDateBuilder()
                .id(1L)
                .name(NAME)
                .day(DAY)
                .build();

        when(programDateServiceMock.update(any(ProgramDate.class))).thenReturn(updated);

        mockMvc.perform(put("/api/program-dates/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Program date updated successfully")))
                .andExpect(jsonPath("$.programDate.id", is(1)))
                .andExpect(jsonPath("$.programDate.name", is(NAME)))
                .andExpect(jsonPath("$.programDate.day", is(TestUtil.convertUTCDateToGMTString(updated.getDay()))));

        ArgumentCaptor<ProgramDate> programDateArgumentCaptor = ArgumentCaptor.forClass(ProgramDate.class);
        verify(programDateServiceMock, times(1)).update(programDateArgumentCaptor.capture());
        verifyNoMoreInteractions(programDateServiceMock);

        ProgramDate programDateArgument = programDateArgumentCaptor.getValue();
        assertThat(programDateArgument.getId(), is(1L));
        assertThat(programDateArgument.getName(), is(updated.getName()));
        assertThat(programDateArgument.getDay(), is(updated.getDay()));
    }

    @Test(expected = JsonMappingException.class)
    public void testUpdate_EmptyProgramDate_ShouldOccurNoInteractionsWanted() throws Exception {
        ProgramDate programDate = new ProgramDateBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/program-dates/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programDate))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(programDateServiceMock);
    }

    @Test
    public void testUpdate_ProgramDateNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        ProgramDate updated = new ProgramDateBuilder()
                .id(3L)
                .name(NAME)
                .day(DAY)
                .build();
        when(programDateServiceMock.update(any(ProgramDate.class))).thenThrow(new ProgramDateNotFoundException(""));

        mockMvc.perform(put("/api/program-dates/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<ProgramDate> programDateArgumentCaptor = ArgumentCaptor.forClass(ProgramDate.class);
        verify(programDateServiceMock, times(1)).update(programDateArgumentCaptor.capture());
        verifyNoMoreInteractions(programDateServiceMock);

        ProgramDate programDateArgument = programDateArgumentCaptor.getValue();
        assertThat(programDateArgument.getId(), is(3L));
        assertThat(programDateArgument.getName(), is(updated.getName()));
        assertThat(programDateArgument.getDay(), is(updated.getDay()));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_StringAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(ProgramDate.MAX_LENGTH_NAME + 1);

        ProgramDate programDate = new ProgramDateBuilder()
                .id(1L)
                .name(name)
                .day(DAY)
                .build();

        mockMvc.perform(put("/api/program-dates/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programDate))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(programDateServiceMock);
    }

    @Test
    public void testDelete_ProgramDateFound_ShouldDeleteProgramDateAndReturnIt() throws Exception {
        ProgramDate deleted = new ProgramDateBuilder()
                .id(1L)
                .name(NAME)
                .day(DAY)
                .build();

        when(programDateServiceMock.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/program-dates/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Program date deleted successfully")))
                .andExpect(jsonPath("$.programDate.id", is(1)))
                .andExpect(jsonPath("$.programDate.name", is(NAME)))
                .andExpect(jsonPath("$.programDate.day", is(TestUtil.convertUTCDateToGMTString(deleted.getDay()))));

        verify(programDateServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(programDateServiceMock);
    }

    @Test
    public void testDelete_ProgramDateNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(programDateServiceMock.deleteById(3L)).thenThrow(new ProgramDateNotFoundException(""));

        mockMvc.perform(delete("/api/program-dates/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(programDateServiceMock, times(1)).deleteById(3L);
        verifyNoMoreInteractions(programDateServiceMock);
    }
}
