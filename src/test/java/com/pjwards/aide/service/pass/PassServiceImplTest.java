package com.pjwards.aide.service.pass;

import com.pjwards.aide.domain.Pass;
import com.pjwards.aide.domain.builder.PassBuilder;
import com.pjwards.aide.exception.PassNotFoundException;
import com.pjwards.aide.repository.PassRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class PassServiceImplTest {

    private static final Long ID = 1L;
    private static final String TAG = "tag";
    private static final String UPDATED_TAG = "updated tag";

    private PassRepository passRepositoryMock;
    private PassService passService;

    @Before
    public void setup() {
        passRepositoryMock = mock(PassRepository.class);
        passService = new PassServiceImpl(passRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfPass() {
        List<Pass> models = new ArrayList<>();
        when(passRepositoryMock.findAll()).thenReturn(models);

        List<Pass> actual = passService.findAll();

        verify(passRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(passRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewPass_ShouldSavePass() {
        Pass pass = new PassBuilder()
                .tag(TAG)
                .build();

        passService.add(pass);

        ArgumentCaptor<Pass> passArgumentCaptor = ArgumentCaptor.forClass(Pass.class);
        verify(passRepositoryMock, times(1)).save(passArgumentCaptor.capture());
        verifyNoMoreInteractions(passRepositoryMock);

        Pass model = passArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getTag(), is(pass.getTag()));
    }

    @Test
    public void testFindById_PassFound_ShouldReturnFoundPass() throws PassNotFoundException {
        Pass model = new PassBuilder()
                .id(ID)
                .tag(TAG)
                .build();

        when(passRepositoryMock.findOne(ID)).thenReturn(model);

        Pass actual = passService.findById(ID);

        verify(passRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(passRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = PassNotFoundException.class)
    public void testFindById_PassNotFound_ShouldThrowException() throws PassNotFoundException {
        when(passRepositoryMock.findOne(ID)).thenReturn(null);

        passService.findById(ID);

        verify(passRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(passRepositoryMock);
    }

    @Test
    public void testUpdate_PassFound_ShouldUpdatePass() throws PassNotFoundException {
        Pass updated = new PassBuilder()
                .id(ID)
                .tag(UPDATED_TAG)
                .build();
        Pass model = new PassBuilder()
                .id(ID)
                .tag(TAG)
                .build();

        when(passRepositoryMock.findOne(updated.getId())).thenReturn(model);

        passService.update(updated);

        verify(passRepositoryMock, times(1)).findOne(model.getId());
        verify(passRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(passRepositoryMock);

        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getTag(), is(updated.getTag()));
    }

    @Test(expected = PassNotFoundException.class)
    public void testUpdate_PassNotFound_ShouldThrowException() throws PassNotFoundException {
        Pass updated = new PassBuilder()
                .id(ID)
                .tag(UPDATED_TAG)
                .build();

        when(passRepositoryMock.findOne(updated.getId())).thenReturn(null);

        passService.update(updated);

        verify(passRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(passRepositoryMock);
    }

    @Test
    public void testDeleteById_PassFound_ShouldDeletePassAndReturnIt() throws PassNotFoundException {
        Pass model = new PassBuilder()
                .id(ID)
                .tag(TAG)
                .build();

        when(passRepositoryMock.findOne(ID)).thenReturn(model);

        Pass actual = passService.deleteById(ID);

        verify(passRepositoryMock, times(1)).findOne(ID);
        verify(passRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(passRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = PassNotFoundException.class)
    public void testDeleteById_PassNotFound_ShouldThrowException() throws PassNotFoundException {
        when(passRepositoryMock.findOne(ID)).thenReturn(null);

        passService.deleteById(ID);

        verify(passRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(passRepositoryMock);
    }
}
