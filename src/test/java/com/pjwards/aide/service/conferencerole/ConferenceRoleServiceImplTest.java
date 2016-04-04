package com.pjwards.aide.service.conferencerole;


import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.domain.builder.ConferenceRoleBuilder;
import com.pjwards.aide.domain.enums.Role;
import com.pjwards.aide.exception.ConferenceRoleNotFoundException;
import com.pjwards.aide.repository.ConferenceRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ConferenceRoleServiceImplTest {
    private static final Long ID = 1L;
    private static final Role ROLE = Role.ADMIN;
    private static final Role UPDATE_ROLE = Role.USER;

    private ConferenceRoleRepository conferenceRoleRepositoryMock;
    private ConferenceRoleService conferenceRoleService;

    @Before
    public void setup(){
        conferenceRoleRepositoryMock = mock(ConferenceRoleRepository.class);
        conferenceRoleService = new ConferenceRoleServiceImpl(conferenceRoleRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfConferenceRole(){
        List<ConferenceRole> models = new ArrayList<>();
        when(conferenceRoleRepositoryMock.findAll()).thenReturn(models);

        List<ConferenceRole> actual = conferenceRoleService.findAll();

        verify(conferenceRoleRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(conferenceRoleRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewConferenceRole_ShouldSaveConferenceRole(){
        ConferenceRole conferenceRole = new ConferenceRoleBuilder()
                .conferenceRole(ROLE)
                .build();

        conferenceRoleService.add(conferenceRole);

        ArgumentCaptor<ConferenceRole> conferenceRoleArgumentCaptor = ArgumentCaptor.forClass(ConferenceRole.class);
        verify(conferenceRoleRepositoryMock, times(1)).save(conferenceRoleArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceRoleRepositoryMock);

        ConferenceRole model = conferenceRoleArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getRole(), is(conferenceRole.getRole()));
    }

    @Test
    public void testFindById_ConferenceRoleFound_ShouldReturnFoundConferenceRole() throws ConferenceRoleNotFoundException{
        ConferenceRole model = new ConferenceRoleBuilder()
                .conferenceRole(ROLE)
                .build();

        when(conferenceRoleRepositoryMock.findOne(ID)).thenReturn(model);

        ConferenceRole actual = conferenceRoleService.findById(ID);

        verify(conferenceRoleRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(conferenceRoleRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ConferenceRoleNotFoundException.class)
    public void testFindById_ConferenceRoleNotFound_ShouldThrowException() throws ConferenceRoleNotFoundException{
        when(conferenceRoleRepositoryMock.findOne(ID)).thenReturn(null);

        conferenceRoleService.findById(ID);

        verify(conferenceRoleRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(conferenceRoleRepositoryMock);
    }

    @Test
    public void testUpdate_ConferenceRoleFound_ShouldUpdateConferenceRole() throws ConferenceRoleNotFoundException{
        ConferenceRole updated = new ConferenceRoleBuilder()
                .id(ID)
                .conferenceRole(UPDATE_ROLE)
                .build();

        ConferenceRole model = new ConferenceRoleBuilder()
                .id(ID)
                .conferenceRole(ROLE)
                .build();

        when(conferenceRoleRepositoryMock.findOne(updated.getId())).thenReturn(model);

        conferenceRoleService.update(updated);

        verify(conferenceRoleRepositoryMock, times(1)).findOne(model.getId());
        verify(conferenceRoleRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(conferenceRoleRepositoryMock);


        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getRole(), is(updated.getRole()));
    }

    @Test(expected = ConferenceRoleNotFoundException.class)
    public void testUpdate_ConferenceRoleNotFound_ShouldThrowException() throws ConferenceRoleNotFoundException{
        ConferenceRole updated = new ConferenceRoleBuilder()
                .id(ID)
                .conferenceRole(UPDATE_ROLE)
                .build();

        when(conferenceRoleRepositoryMock.findOne(updated.getId())).thenReturn(null);

        conferenceRoleService.update(updated);

        verify(conferenceRoleRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(conferenceRoleRepositoryMock);
    }

    @Test
    public void testDeleteById_ConferenceRoleFound_ShouldDeleteConferenceRoleAndReturnIt() throws ConferenceRoleNotFoundException{
        ConferenceRole model = new ConferenceRoleBuilder()
                .id(ID)
                .conferenceRole(ROLE)
                .build();

        when(conferenceRoleRepositoryMock.findOne(ID)).thenReturn(model);

        ConferenceRole actual = conferenceRoleService.deleteById(ID);

        verify(conferenceRoleRepositoryMock, times(1)).findOne(ID);
        verify(conferenceRoleRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(conferenceRoleRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ConferenceRoleNotFoundException.class)
    public void testDeleteById_ConferenceRoleNotFound_ShouldThrowException() throws ConferenceRoleNotFoundException{
        when(conferenceRoleRepositoryMock.findOne(ID)).thenReturn(null);

        conferenceRoleService.deleteById(ID);

        verify(conferenceRoleRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(conferenceRoleRepositoryMock);
    }
}
