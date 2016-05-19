package com.pjwards.aide.service.session;

import com.pjwards.aide.domain.Session;
import com.pjwards.aide.domain.builder.SessionBuilder;
import com.pjwards.aide.exception.SessionNotFoundException;
import com.pjwards.aide.exception.WrongInputDateException;
import com.pjwards.aide.repository.ProgramRepository;
import com.pjwards.aide.repository.RoomRepository;
import com.pjwards.aide.repository.SessionRepository;
import com.pjwards.aide.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class SessionServiceImplTest {

    private static final Long ID = 1L;
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String UPDATED_TITLE = "updated title";
    private static final String UPDATED_DESCRIPTION = "updated description";


    private SessionRepository sessionRepositoryMock;
    private ProgramRepository programRepositoryMock;
    private RoomRepository roomRepositoryMock;
    private UserRepository userRepositoryMock;
    private SessionService sessionService;

    @Before
    public void setup() {
        sessionRepositoryMock = mock(SessionRepository.class);
        programRepositoryMock = mock(ProgramRepository.class);
        roomRepositoryMock = mock(RoomRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        sessionService = new SessionServiceImpl(sessionRepositoryMock, programRepositoryMock,
                roomRepositoryMock, userRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfSession() {
        List<Session> models = new ArrayList<>();
        when(sessionRepositoryMock.findAll()).thenReturn(models);

        List<Session> actual = sessionService.findAll();

        verify(sessionRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(sessionRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewSession_ShouldSaveSession() throws WrongInputDateException {
        Session session = new SessionBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        sessionService.add(session);

        ArgumentCaptor<Session> sessionArgumentCaptor = ArgumentCaptor.forClass(Session.class);
        verify(sessionRepositoryMock, times(1)).save(sessionArgumentCaptor.capture());
        verifyNoMoreInteractions(sessionRepositoryMock);

        Session model = sessionArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getTitle(), is(session.getTitle()));
        assertThat(model.getDescription(), is(session.getDescription()));
    }

    @Test
    public void testFindById_SessionFound_ShouldReturnFoundSession() throws SessionNotFoundException {
        Session model = new SessionBuilder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        when(sessionRepositoryMock.findOne(ID)).thenReturn(model);

        Session actual = sessionService.findById(ID);

        verify(sessionRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(sessionRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = SessionNotFoundException.class)
    public void testFindById_SessionNotFound_ShouldThrowException() throws SessionNotFoundException {
        when(sessionRepositoryMock.findOne(ID)).thenReturn(null);

        sessionService.findById(ID);

        verify(sessionRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(sessionRepositoryMock);
    }

    @Test
    public void testUpdate_SessionFound_ShouldUpdateSession() throws SessionNotFoundException, WrongInputDateException {
        Session updated = new SessionBuilder()
                .id(ID)
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .build();
        Session model = new SessionBuilder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        when(sessionRepositoryMock.findOne(updated.getId())).thenReturn(model);

        sessionService.update(updated);

        verify(sessionRepositoryMock, times(1)).findOne(model.getId());
        verify(sessionRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(sessionRepositoryMock);

        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getTitle(), is(updated.getTitle()));
        assertThat(model.getDescription(), is(updated.getDescription()));
    }

    @Test(expected = SessionNotFoundException.class)
    public void testUpdate_SessionNotFound_ShouldThrowException() throws SessionNotFoundException, WrongInputDateException {
        Session updated = new SessionBuilder()
                .id(ID)
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .build();

        when(sessionRepositoryMock.findOne(updated.getId())).thenReturn(null);

        sessionService.update(updated);

        verify(sessionRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(sessionRepositoryMock);
    }

    @Test
    public void testDeleteById_SessionFound_ShouldDeleteSessionAndReturnIt() throws SessionNotFoundException {
        Session model = new SessionBuilder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        when(sessionRepositoryMock.findOne(ID)).thenReturn(model);

        Session actual = sessionService.deleteById(ID);

        verify(sessionRepositoryMock, times(1)).findOne(ID);
        verify(sessionRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(sessionRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = SessionNotFoundException.class)
    public void testDeleteById_SessionNotFound_ShouldThrowException() throws SessionNotFoundException {
        when(sessionRepositoryMock.findOne(ID)).thenReturn(null);

        sessionService.deleteById(ID);

        verify(sessionRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(sessionRepositoryMock);
    }
}

