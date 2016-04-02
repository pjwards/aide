package com.pjwards.aide.service.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.builder.ConferenceBuilder;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.repository.ConferenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ConferenceServiceImplTest {

    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String UPDATED_NAME = "updated name";
    private static final String UPDATED_DESCRIPTION = "updated description";

    private ConferenceRepository conferenceRepositoryMock;
    private ConferenceService conferenceService;

    @Before
    public void setup() {
        conferenceRepositoryMock = mock(ConferenceRepository.class);
        conferenceService = new ConferenceServiceImpl(conferenceRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfConference() {
        List<Conference> models = new ArrayList<>();
        when(conferenceRepositoryMock.findAll()).thenReturn(models);

        List<Conference> actual = conferenceService.findAll();

        verify(conferenceRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(conferenceRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewConference_ShouldSaveConference() {
        Conference conference = new ConferenceBuilder()
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        conferenceService.add(conference);

        ArgumentCaptor<Conference> conferenceArgumentCaptor = ArgumentCaptor.forClass(Conference.class);
        verify(conferenceRepositoryMock, times(1)).save(conferenceArgumentCaptor.capture());
        verifyNoMoreInteractions(conferenceRepositoryMock);

        Conference model = conferenceArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getName(), is(conference.getName()));
        assertThat(model.getDescription(), is(conference.getDescription()));
    }

    @Test
    public void testFindById_ConferenceFound_ShouldReturnFoundConference() throws ConferenceNotFoundException {
        Conference model = new ConferenceBuilder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        when(conferenceRepositoryMock.findOne(ID)).thenReturn(model);

        Conference actual = conferenceService.findById(ID);

        verify(conferenceRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(conferenceRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ConferenceNotFoundException.class)
    public void testFindById_ConferenceNotFound_ShouldThrowException() throws ConferenceNotFoundException {
        when(conferenceRepositoryMock.findOne(ID)).thenReturn(null);

        conferenceService.findById(ID);

        verify(conferenceRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(conferenceRepositoryMock);
    }

    @Test
    public void testUpdate_ConferenceFound_ShouldUpdateConference() throws ConferenceNotFoundException {
        Conference updated = new ConferenceBuilder()
                .id(ID)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .build();
        Conference model = new ConferenceBuilder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        when(conferenceRepositoryMock.findOne(updated.getId())).thenReturn(model);

        conferenceService.update(updated);

        verify(conferenceRepositoryMock, times(1)).findOne(model.getId());
        verify(conferenceRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(conferenceRepositoryMock);

        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getName(), is(updated.getName()));
        assertThat(model.getDescription(), is(updated.getDescription()));
    }

    @Test(expected = ConferenceNotFoundException.class)
    public void testUpdate_ConferenceNotFound_ShouldThrowException() throws ConferenceNotFoundException {
        Conference updated = new ConferenceBuilder()
                .id(ID)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .build();

        when(conferenceRepositoryMock.findOne(updated.getId())).thenReturn(null);

        conferenceService.update(updated);

        verify(conferenceRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(conferenceRepositoryMock);
    }

    @Test
    public void testDeleteById_ConferenceFound_ShouldDeleteConferenceAndReturnIt() throws ConferenceNotFoundException {
        Conference model = new ConferenceBuilder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .build();

        when(conferenceRepositoryMock.findOne(ID)).thenReturn(model);

        Conference actual = conferenceService.deleteById(ID);

        verify(conferenceRepositoryMock, times(1)).findOne(ID);
        verify(conferenceRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(conferenceRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ConferenceNotFoundException.class)
    public void testDeleteById_ConferenceNotFound_ShouldThrowException() throws ConferenceNotFoundException {
        when(conferenceRepositoryMock.findOne(ID)).thenReturn(null);

        conferenceService.deleteById(ID);

        verify(conferenceRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(conferenceRepositoryMock);
    }
}
