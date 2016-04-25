package com.pjwards.aide.service.program;

import com.pjwards.aide.domain.Program;
import com.pjwards.aide.domain.builder.ProgramBuilder;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;
import com.pjwards.aide.repository.ProgramRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ProgramServiceImplTest {

    private static final Long ID = 1L;
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String BEGIN = "09:00";
    private static final String END = "10:00";
    private static final String UPDATED_TITLE = "updated title";
    private static final String UPDATED_DESCRIPTION = "updated description";
    private static final String UPDATED_BEGIN = "10:00";
    private static final String UPDATED_END = "11:00";


    private ProgramRepository programRepositoryMock;
    private ProgramService programService;

    @Before
    public void setup() {
        programRepositoryMock = mock(ProgramRepository.class);
        programService = new ProgramServiceImpl(programRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfProgram() {
        List<Program> models = new ArrayList<>();
        when(programRepositoryMock.findAll()).thenReturn(models);

        List<Program> actual = programService.findAll();

        verify(programRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(programRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewProgram_ShouldSaveProgram() throws WrongInputDateException {
        Program program = new ProgramBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        programService.add(program);

        ArgumentCaptor<Program> programArgumentCaptor = ArgumentCaptor.forClass(Program.class);
        verify(programRepositoryMock, times(1)).save(programArgumentCaptor.capture());
        verifyNoMoreInteractions(programRepositoryMock);

        Program model = programArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getTitle(), is(program.getTitle()));
        assertThat(model.getDescription(), is(program.getDescription()));
        assertThat(model.getBegin(), is(program.getBegin()));
        assertThat(model.getEnd(), is(program.getEnd()));
    }

    @Test(expected = WrongInputDateException.class)
    public void testAdd_WrongInputDate_ShouldThrowException() throws ProgramNotFoundException, WrongInputDateException {
        Program program = new ProgramBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(END)
                .end(BEGIN)
                .build();

        programService.add(program);

        ArgumentCaptor<Program> programArgumentCaptor = ArgumentCaptor.forClass(Program.class);
        verify(programRepositoryMock, times(1)).save(programArgumentCaptor.capture());
        verifyNoMoreInteractions(programRepositoryMock);
    }

    @Test
    public void testFindById_ProgramFound_ShouldReturnFoundProgram() throws ProgramNotFoundException {
        Program model = new ProgramBuilder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programRepositoryMock.findOne(ID)).thenReturn(model);

        Program actual = programService.findById(ID);

        verify(programRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(programRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ProgramNotFoundException.class)
    public void testFindById_ProgramNotFound_ShouldThrowException() throws ProgramNotFoundException {
        when(programRepositoryMock.findOne(ID)).thenReturn(null);

        programService.findById(ID);

        verify(programRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(programRepositoryMock);
    }

    @Test
    public void testUpdate_ProgramFound_ShouldUpdateProgram() throws ProgramNotFoundException, WrongInputDateException {
        Program updated = new ProgramBuilder()
                .id(ID)
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .begin(UPDATED_BEGIN)
                .end(UPDATED_END)
                .build();
        Program model = new ProgramBuilder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programRepositoryMock.findOne(updated.getId())).thenReturn(model);

        programService.update(updated);

        verify(programRepositoryMock, times(1)).findOne(model.getId());
        verify(programRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(programRepositoryMock);

        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getTitle(), is(updated.getTitle()));
        assertThat(model.getDescription(), is(updated.getDescription()));
        assertThat(model.getBegin(), is(updated.getBegin()));
        assertThat(model.getEnd(), is(updated.getEnd()));
    }

    @Test(expected = ProgramNotFoundException.class)
    public void testUpdate_ProgramNotFound_ShouldThrowException() throws ProgramNotFoundException, WrongInputDateException {
        Program updated = new ProgramBuilder()
                .id(ID)
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .begin(UPDATED_BEGIN)
                .end(UPDATED_END)
                .build();

        when(programRepositoryMock.findOne(updated.getId())).thenReturn(null);

        programService.update(updated);

        verify(programRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(programRepositoryMock);
    }

    @Test(expected = WrongInputDateException.class)
    public void testUpdate_WrongInputDate_ShouldThrowException() throws ProgramNotFoundException, WrongInputDateException {
        Program updated = new ProgramBuilder()
                .id(ID)
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .begin(UPDATED_END)
                .end(UPDATED_BEGIN)
                .build();

        Program model = new ProgramBuilder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programRepositoryMock.findOne(updated.getId())).thenReturn(model);

        programService.update(updated);

        verify(programRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(programRepositoryMock);
    }

    @Test
    public void testDeleteById_ProgramFound_ShouldDeleteProgramAndReturnIt() throws ProgramNotFoundException {
        Program model = new ProgramBuilder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .begin(BEGIN)
                .end(END)
                .build();

        when(programRepositoryMock.findOne(ID)).thenReturn(model);

        Program actual = programService.deleteById(ID);

        verify(programRepositoryMock, times(1)).findOne(ID);
        verify(programRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(programRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ProgramNotFoundException.class)
    public void testDeleteById_ProgramNotFound_ShouldThrowException() throws ProgramNotFoundException {
        when(programRepositoryMock.findOne(ID)).thenReturn(null);

        programService.deleteById(ID);

        verify(programRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(programRepositoryMock);
    }
}

