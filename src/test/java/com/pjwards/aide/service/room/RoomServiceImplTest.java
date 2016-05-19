package com.pjwards.aide.service.room;

import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.builder.RoomBuilder;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.repository.ConferenceRepository;
import com.pjwards.aide.repository.RoomRepository;
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

public class RoomServiceImplTest {

    private static final Long ID = 1L;
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String DESCRIPTION = "description";
    private static final String UPDATED_NAME = "updated name";
    private static final String UPDATED_LOCATION = "updated location";
    private static final String UPDATED_DESCRIPTION = "updated description";

    private RoomRepository roomRepositoryMock;
    private ConferenceRepository conferenceRepositoryMock;
    private UserRepository userRepositoryMock;
    private RoomService roomService;

    @Before
    public void setup() {
        roomRepositoryMock = mock(RoomRepository.class);
        conferenceRepositoryMock = mock(ConferenceRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        roomService = new RoomServiceImpl(roomRepositoryMock, conferenceRepositoryMock, userRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfRoom() {
        List<Room> models = new ArrayList<>();
        when(roomRepositoryMock.findAll()).thenReturn(models);

        List<Room> actual = roomService.findAll();

        verify(roomRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(roomRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewRoom_ShouldSaveRoom() {
        Room room = new RoomBuilder()
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        roomService.add(room);

        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomRepositoryMock, times(1)).save(roomArgumentCaptor.capture());
        verifyNoMoreInteractions(roomRepositoryMock);

        Room model = roomArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getName(), is(room.getName()));
        assertThat(model.getDescription(), is(room.getDescription()));
    }

    @Test
    public void testFindById_RoomFound_ShouldReturnFoundRoom() throws RoomNotFoundException {
        Room model = new RoomBuilder()
                .id(ID)
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        when(roomRepositoryMock.findOne(ID)).thenReturn(model);

        Room actual = roomService.findById(ID);

        verify(roomRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(roomRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = RoomNotFoundException.class)
    public void testFindById_RoomNotFound_ShouldThrowException() throws RoomNotFoundException {
        when(roomRepositoryMock.findOne(ID)).thenReturn(null);

        roomService.findById(ID);

        verify(roomRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(roomRepositoryMock);
    }

    @Test
    public void testUpdate_RoomFound_ShouldUpdateRoom() throws RoomNotFoundException {
        Room updated = new RoomBuilder()
                .id(ID)
                .name(UPDATED_NAME)
                .location(UPDATED_LOCATION)
                .description(UPDATED_DESCRIPTION)
                .build();
        Room model = new RoomBuilder()
                .id(ID)
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        when(roomRepositoryMock.findOne(updated.getId())).thenReturn(model);

        roomService.update(updated);

        verify(roomRepositoryMock, times(1)).findOne(model.getId());
        verify(roomRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(roomRepositoryMock);

        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getName(), is(updated.getName()));
        assertThat(model.getDescription(), is(updated.getDescription()));
    }

    @Test(expected = RoomNotFoundException.class)
    public void testUpdate_RoomNotFound_ShouldThrowException() throws RoomNotFoundException {
        Room updated = new RoomBuilder()
                .id(ID)
                .name(UPDATED_NAME)
                .location(UPDATED_LOCATION)
                .description(UPDATED_DESCRIPTION)
                .build();

        when(roomRepositoryMock.findOne(updated.getId())).thenReturn(null);

        roomService.update(updated);

        verify(roomRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(roomRepositoryMock);
    }

    @Test
    public void testDeleteById_RoomFound_ShouldDeleteRoomAndReturnIt() throws RoomNotFoundException {
        Room model = new RoomBuilder()
                .id(ID)
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        when(roomRepositoryMock.findOne(ID)).thenReturn(model);

        Room actual = roomService.deleteById(ID);

        verify(roomRepositoryMock, times(1)).findOne(ID);
        verify(roomRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(roomRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = RoomNotFoundException.class)
    public void testDeleteById_RoomNotFound_ShouldThrowException() throws RoomNotFoundException {
        when(roomRepositoryMock.findOne(ID)).thenReturn(null);

        roomService.deleteById(ID);

        verify(roomRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(roomRepositoryMock);
    }
}
