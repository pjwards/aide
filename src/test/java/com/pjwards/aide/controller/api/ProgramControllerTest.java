package com.pjwards.aide.controller.api;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Program;
import com.pjwards.aide.domain.builder.ProgramBuilder;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.service.program.ProgramService;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class ProgramControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramControllerTest.class);
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final long TWO_HOUR = 2 * 60 * 60 * 1000;
    private static final Date BEGIN = new Date(System.currentTimeMillis());
    private static final Date END = new Date(System.currentTimeMillis() + TWO_HOUR);

    private MockMvc mockMvc;

    @Autowired
    private ProgramService programService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(programService);
    }

    @Test
    public void testGetAll_ProgramsFound_ShoudReturnFoundProgram() throws Exception {
        Date BEGIN_1 = new Date(System.currentTimeMillis());
        Date END_1 = new Date(System.currentTimeMillis() + TWO_HOUR);
        Date BEGIN_2 = new Date(System.currentTimeMillis() + TWO_HOUR);
        Date END_2 = new Date(System.currentTimeMillis() + TWO_HOUR * 2);

        Program first = new ProgramBuilder()
                .id(1L)
                .title("title1")
                .description("description1")
                .begin(BEGIN_1)
                .end(END_1)
                .build();
        Program second = new ProgramBuilder()
                .id(2L)
                .title("title2")
                .description("description2")
                .begin(BEGIN_2)
                .end(END_2)
                .build();

        when(programService.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/programs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[0].begin", is(TestUtil.convertUTCDateToGMTString(BEGIN_1))))
                .andExpect(jsonPath("$[0].end", is(TestUtil.convertUTCDateToGMTString(END_1))))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("title2")))
                .andExpect(jsonPath("$[1].description", is("description2")))
                .andExpect(jsonPath("$[1].begin", is(TestUtil.convertUTCDateToGMTString(BEGIN_2))))
                .andExpect(jsonPath("$[1].end", is(TestUtil.convertUTCDateToGMTString(END_2))));

        verify(programService, times(1)).findAll();
        verifyNoMoreInteractions(programService);
    }

    @Test
    public void testCreate_NewProgram_ShouldAddProgramReturnAddedProgram() throws Exception {
        Program added = new ProgramBuilder()
                .id(1L)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programService.add(any(Program.class))).thenReturn(added);

        mockMvc.perform(post("/api/programs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Program created successfully")))
                .andExpect(jsonPath("$.program.id", is(1)))
                .andExpect(jsonPath("$.program.title", is(TITLE)))
                .andExpect(jsonPath("$.program.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.program.begin", is(TestUtil.convertUTCDateToGMTString(BEGIN))))
                .andExpect(jsonPath("$.program.end", is(TestUtil.convertUTCDateToGMTString(END))));

        ArgumentCaptor<Program> programArgumentCaptor = ArgumentCaptor.forClass(Program.class);
        verify(programService, times(1)).add(programArgumentCaptor.capture());
        verifyNoMoreInteractions(programService);

        Program programArgument = programArgumentCaptor.getValue();
        assertThat(programArgument.getId(), is(1L));
        assertThat(programArgument.getTitle(), is(TITLE));
        assertThat(programArgument.getDescription(), is(DESCRIPTION));
        assertTrue("Begin dates aren't close enough to each other!",
                abs(programArgument.getBegin().getTime() - BEGIN.getTime()) < 1000);
        assertTrue("End dates aren't close enough to each other!",
                abs(programArgument.getEnd().getTime() - END.getTime()) < 1000);
    }

    @Test(expected = JsonMappingException.class)
    public void testCreate_EmptyProgram_ShouldOccurNoInteractionsWanted() throws Exception {
        Program program = new Program();

        mockMvc.perform(post("/api/programs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(program))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(programService);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_TitleAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String title = TestUtil.createStringWithLength(Program.MAX_LENGTH_TITLE + 1);
        Program program = new ProgramBuilder()
                .title(title)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        mockMvc.perform(post("/api/programs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(program))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(programService);
    }

    @Test
    public void testGetDetails_ProgramFound_ShouldReturnFoundProgram() throws Exception {
        Program found = new ProgramBuilder()
                .id(1L)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programService.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/programs/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(TITLE)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.begin", is(TestUtil.convertUTCDateToGMTString(BEGIN))))
                .andExpect(jsonPath("$.end", is(TestUtil.convertUTCDateToGMTString(END))));

        verify(programService, times(1)).findById(1L);
        verifyNoMoreInteractions(programService);
    }

    @Test
    public void testGetDetails_ProgramNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(programService.findById(1L)).thenThrow(new ProgramNotFoundException(""));

        mockMvc.perform(get("/api/programs/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(programService, times(1)).findById(1L);
        verifyNoMoreInteractions(programService);
    }

    @Test
    public void testUpdate_ProgramFound_ShouldUpdateProgramAndReturnIt() throws Exception {
        Program updated = new ProgramBuilder()
                .id(1L)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programService.update(any(Program.class))).thenReturn(updated);

        mockMvc.perform(put("/api/programs/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Program updated successfully")))
                .andExpect(jsonPath("$.program.id", is(1)))
                .andExpect(jsonPath("$.program.title", is(TITLE)))
                .andExpect(jsonPath("$.program.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.program.begin", is(TestUtil.convertUTCDateToGMTString(BEGIN))))
                .andExpect(jsonPath("$.program.end", is(TestUtil.convertUTCDateToGMTString(END))));

        ArgumentCaptor<Program> programArgumentCaptor = ArgumentCaptor.forClass(Program.class);
        verify(programService, times(1)).update(programArgumentCaptor.capture());
        verifyNoMoreInteractions(programService);

        Program programArgument = programArgumentCaptor.getValue();
        assertThat(programArgument.getId(), is(1L));
        assertThat(programArgument.getTitle(), is(TITLE));
        assertThat(programArgument.getDescription(), is(DESCRIPTION));
        assertTrue("Begin dates aren't close enough to each other!",
                abs(programArgument.getBegin().getTime() - BEGIN.getTime()) < 1000);
        assertTrue("End dates aren't close enough to each other!",
                abs(programArgument.getEnd().getTime() - END.getTime()) < 1000);
    }

    @Test(expected = JsonMappingException.class)
    public void testUpdate_EmptyProgram_ShouldOccurNoInteractionsWanted() throws Exception {
        Program program = new ProgramBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/programs/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(program))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(programService);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_TitleAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String title = TestUtil.createStringWithLength(Program.MAX_LENGTH_TITLE + 1);
        Program program = new ProgramBuilder()
                .id(1L)
                .title(title)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        mockMvc.perform(put("/api/programs/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(program))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(programService);
    }

    @Test
    public void testUpdate_ProgramNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Program updated = new ProgramBuilder()
                .id(3L)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programService.update(any(Program.class))).thenThrow(new ProgramNotFoundException(""));

        mockMvc.perform(put("/api/programs/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Program> programArgumentCaptor = ArgumentCaptor.forClass(Program.class);
        verify(programService, times(1)).update(programArgumentCaptor.capture());
        verifyNoMoreInteractions(programService);

        Program programArgument = programArgumentCaptor.getValue();
        assertThat(programArgument.getId(), is(3L));
        assertThat(programArgument.getTitle(), is(TITLE));
        assertThat(programArgument.getDescription(), is(DESCRIPTION));
        assertTrue("Begin dates aren't close enough to each other!",
                abs(programArgument.getBegin().getTime() - BEGIN.getTime()) < 1000);
        assertTrue("End dates aren't close enough to each other!",
                abs(programArgument.getEnd().getTime() - END.getTime()) < 1000);
    }

    @Test
    public void testDelete_ProgramFound_ShouldDeleteProgramAndReturnIt() throws Exception {
        Program deleted = new ProgramBuilder()
                .id(1L)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programService.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/programs/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Program deleted successfully")))
                .andExpect(jsonPath("$.program.id", is(1)))
                .andExpect(jsonPath("$.program.title", is(TITLE)))
                .andExpect(jsonPath("$.program.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.program.begin", is(TestUtil.convertUTCDateToGMTString(BEGIN))))
                .andExpect(jsonPath("$.program.end", is(TestUtil.convertUTCDateToGMTString(END))));

        verify(programService, times(1)).deleteById(1L);
        verifyNoMoreInteractions(programService);
    }

    @Test
    public void testDelete_ProgramNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(programService.deleteById(3L)).thenThrow(new ProgramNotFoundException(""));

        mockMvc.perform(delete("/api/programs/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(programService, times(1)).deleteById(3L);
        verifyNoMoreInteractions(programService);
    }
}
