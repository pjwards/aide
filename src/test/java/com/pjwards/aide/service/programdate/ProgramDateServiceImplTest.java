package com.pjwards.aide.service.programdate;

import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.domain.builder.ProgramDateBuilder;
import com.pjwards.aide.exception.ProgramDateNotFoundException;
import com.pjwards.aide.repository.ProgramDateRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ProgramDateServiceImplTest {

    private static final Long ID = 1L;
    private static final String DAY = "2016-01-01";
    private static final String UPDATED_DAY = "2016-01-02";

    private ProgramDateRepository programDateRepositoryMock;
    private ProgramDateService programDateService;

    @Before
    public void setup() {
        programDateRepositoryMock = mock(ProgramDateRepository.class);
        programDateService = new ProgramDateServiceImpl(programDateRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfProgramDate() {
        List<ProgramDate> models = new ArrayList<>();
        when(programDateRepositoryMock.findAll()).thenReturn(models);

        List<ProgramDate> actual = programDateService.findAll();

        verify(programDateRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(programDateRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewProgramDate_ShouldSaveProgramDate() throws ParseException {
        ProgramDate programDate = new ProgramDateBuilder()
                .day(DAY)
                .build();

        programDateService.add(programDate);

        ArgumentCaptor<ProgramDate> programDateArgumentCaptor = ArgumentCaptor.forClass(ProgramDate.class);
        verify(programDateRepositoryMock, times(1)).save(programDateArgumentCaptor.capture());
        verifyNoMoreInteractions(programDateRepositoryMock);

        ProgramDate model = programDateArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getDay(), is(programDate.getDay()));
    }

    @Test
    public void testFindById_ProgramDateFound_ShouldReturnFoundProgramDate() throws ProgramDateNotFoundException, ParseException {
        ProgramDate model = new ProgramDateBuilder()
                .id(ID)
                .day(DAY)
                .build();

        when(programDateRepositoryMock.findOne(ID)).thenReturn(model);

        ProgramDate actual = programDateService.findById(ID);

        verify(programDateRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(programDateRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ProgramDateNotFoundException.class)
    public void testFindById_ProgramDateNotFound_ShouldThrowException() throws ProgramDateNotFoundException {
        when(programDateRepositoryMock.findOne(ID)).thenReturn(null);

        programDateService.findById(ID);

        verify(programDateRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(programDateRepositoryMock);
    }

    @Test
    public void testUpdate_ProgramDateFound_ShouldUpdateProgramDate() throws ProgramDateNotFoundException, ParseException {
        ProgramDate updated = new ProgramDateBuilder()
                .id(ID)
                .day(UPDATED_DAY)
                .build();
        ProgramDate model = new ProgramDateBuilder()
                .id(ID)
                .day(DAY)
                .build();

        when(programDateRepositoryMock.findOne(updated.getId())).thenReturn(model);

        programDateService.update(updated);

        verify(programDateRepositoryMock, times(1)).findOne(model.getId());
        verify(programDateRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(programDateRepositoryMock);

        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getDay(), is(updated.getDay()));
    }

    @Test(expected = ProgramDateNotFoundException.class)
    public void testUpdate_ProgramDateNotFound_ShouldThrowException() throws ProgramDateNotFoundException, ParseException {
        ProgramDate updated = new ProgramDateBuilder()
                .id(ID)
                .day(UPDATED_DAY)
                .build();

        when(programDateRepositoryMock.findOne(updated.getId())).thenReturn(null);

        programDateService.update(updated);

        verify(programDateRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(programDateRepositoryMock);
    }

    @Test
    public void testDeleteById_ProgramDateFound_ShouldDeleteProgramDateAndReturnIt() throws ProgramDateNotFoundException, ParseException {
        ProgramDate model = new ProgramDateBuilder()
                .id(ID)
                .day(DAY)
                .build();

        when(programDateRepositoryMock.findOne(ID)).thenReturn(model);

        ProgramDate actual = programDateService.deleteById(ID);

        verify(programDateRepositoryMock, times(1)).findOne(ID);
        verify(programDateRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(programDateRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ProgramDateNotFoundException.class)
    public void testDeleteById_ProgramDateNotFound_ShouldThrowException() throws ProgramDateNotFoundException {
        when(programDateRepositoryMock.findOne(ID)).thenReturn(null);

        programDateService.deleteById(ID);

        verify(programDateRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(programDateRepositoryMock);
    }
}
