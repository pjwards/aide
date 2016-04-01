package com.pjwards.aide.controller.api;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.builder.RoomBuilder;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.service.room.RoomService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class RoomControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomControllerTest.class);
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String DESCRIPTION = "description";

    private MockMvc mockMvc;

    @Autowired
    private RoomService roomService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(roomService);
    }

    @Test
    public void testGetAll_RoomsFound_ShoudReturnFoundRoom() throws Exception {
        Room first = new RoomBuilder()
                .id(1L)
                .name("name1")
                .location("location1")
                .description("description1")
                .build();
        Room second = new RoomBuilder()
                .id(2L)
                .name("name2")
                .location("location2")
                .description("description2")
                .build();

        when(roomService.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[0].location", is("location1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[1].location", is("location2")))
                .andExpect(jsonPath("$[1].description", is("description2")));

        verify(roomService, times(1)).findAll();
        verifyNoMoreInteractions(roomService);
    }

    @Test
    public void testCreate_NewRoom_ShouldAddRoomReturnAddedRoom() throws Exception {
        Room added = new RoomBuilder()
                .id(1L)
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        when(roomService.add(any(Room.class))).thenReturn(added);

        mockMvc.perform(post("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Room created successfully")))
                .andExpect(jsonPath("$.room.id", is(1)))
                .andExpect(jsonPath("$.room.name", is(NAME)))
                .andExpect(jsonPath("$.room.location", is(LOCATION)))
                .andExpect(jsonPath("$.room.description", is(DESCRIPTION)));

        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomService, times(1)).add(roomArgumentCaptor.capture());
        verifyNoMoreInteractions(roomService);

        Room roomArgument = roomArgumentCaptor.getValue();
        assertThat(roomArgument.getId(), is(1L));
        assertThat(roomArgument.getName(), is(NAME));
        assertThat(roomArgument.getDescription(), is(DESCRIPTION));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptyRoom_ShouldOccurNoInteractionsWanted() throws Exception {
        Room room = new Room();

        mockMvc.perform(post("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(roomService);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_NameAndLocationAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(Room.MAX_LENGTH_NAME + 1);
        String location = TestUtil.createStringWithLength(Room.MAX_LENGTH_LOCATION + 1);
        Room room = new RoomBuilder()
                .id(1L)
                .name(name)
                .location(location)
                .description(DESCRIPTION)
                .build();

        mockMvc.perform(post("/api/rooms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(roomService);
    }

    @Test
    public void testGetDetails_RoomFound_ShouldReturnFoundRoom() throws Exception {
        Room found = new RoomBuilder()
                .id(1L)
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        when(roomService.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/rooms/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.location", is(LOCATION)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));

        verify(roomService, times(1)).findById(1L);
        verifyNoMoreInteractions(roomService);
    }

    @Test
    public void testGetDetails_RoomNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(roomService.findById(1L)).thenThrow(new RoomNotFoundException(""));

        mockMvc.perform(get("/api/rooms/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(roomService, times(1)).findById(1L);
        verifyNoMoreInteractions(roomService);
    }

    @Test
    public void testUpdate_RoomFound_ShouldUpdateRoomAndReturnIt() throws Exception {
        Room updated = new RoomBuilder()
                .id(1L)
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        when(roomService.update(any(Room.class))).thenReturn(updated);

        mockMvc.perform(put("/api/rooms/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Room updated successfully")))
                .andExpect(jsonPath("$.room.id", is(1)))
                .andExpect(jsonPath("$.room.name", is(NAME)))
                .andExpect(jsonPath("$.room.location", is(LOCATION)))
                .andExpect(jsonPath("$.room.description", is(DESCRIPTION)));

        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomService, times(1)).update(roomArgumentCaptor.capture());
        verifyNoMoreInteractions(roomService);

        Room roomArgument = roomArgumentCaptor.getValue();
        assertThat(roomArgument.getId(), is(1L));
        assertThat(roomArgument.getName(), is(NAME));
        assertThat(roomArgument.getLocation(), is(LOCATION));
        assertThat(roomArgument.getDescription(), is(DESCRIPTION));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptyRoom_ShouldOccurNoInteractionsWanted() throws Exception {
        Room room = new RoomBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/rooms/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(roomService);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_NameAndLocationAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(Room.MAX_LENGTH_NAME + 1);
        String location = TestUtil.createStringWithLength(Room.MAX_LENGTH_LOCATION + 1);
        Room room = new RoomBuilder()
                .id(1L)
                .name(name)
                .location(name)
                .description(DESCRIPTION)
                .build();

        mockMvc.perform(put("/api/rooms/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(room))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(roomService);
    }

    @Test
    public void testUpdate_RoomNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Room updated = new RoomBuilder()
                .id(3L)
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        when(roomService.update(any(Room.class))).thenThrow(new RoomNotFoundException(""));

        mockMvc.perform(put("/api/rooms/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomService, times(1)).update(roomArgumentCaptor.capture());
        verifyNoMoreInteractions(roomService);

        Room roomArgument = roomArgumentCaptor.getValue();
        assertThat(roomArgument.getId(), is(3L));
        assertThat(roomArgument.getName(), is(NAME));
        assertThat(roomArgument.getLocation(), is(LOCATION));
        assertThat(roomArgument.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testDelete_RoomFound_ShouldDeleteRoomAndReturnIt() throws Exception {
        Room deleted = new RoomBuilder()
                .id(1L)
                .name(NAME)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();

        when(roomService.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/rooms/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Room deleted successfully")))
                .andExpect(jsonPath("$.room.id", is(1)))
                .andExpect(jsonPath("$.room.name", is(NAME)))
                .andExpect(jsonPath("$.room.location", is(LOCATION)))
                .andExpect(jsonPath("$.room.description", is(DESCRIPTION)));

        verify(roomService, times(1)).deleteById(1L);
        verifyNoMoreInteractions(roomService);
    }

    @Test
    public void testDelete_RoomNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(roomService.deleteById(3L)).thenThrow(new RoomNotFoundException(""));

        mockMvc.perform(delete("/api/rooms/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(roomService, times(1)).deleteById(3L);
        verifyNoMoreInteractions(roomService);
    }
}
