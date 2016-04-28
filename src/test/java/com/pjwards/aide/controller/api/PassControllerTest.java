package com.pjwards.aide.controller.api;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.config.TestConfig;
import com.pjwards.aide.domain.Pass;
import com.pjwards.aide.domain.builder.PassBuilder;
import com.pjwards.aide.exception.PassNotFoundException;
import com.pjwards.aide.service.pass.PassService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class PassControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassControllerTest.class);
    private static final String TAG = "tag";

    private MockMvc mockMvc;

    @Qualifier("passService")
    @Autowired
    private PassService passServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(passServiceMock);
    }

    @Test
    public void testGetAll_PasssFound_ShoudReturnFoundPass() throws Exception {
        Pass first = new PassBuilder()
                .id(1L)
                .tag("tag1")
                .build();
        Pass second = new PassBuilder()
                .id(2L)
                .tag("tag2")
                .build();

        when(passServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/passes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].tag", is("tag1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].tag", is("tag2")));

        verify(passServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(passServiceMock);
    }

    @Test
    public void testCreate_NewPass_ShouldAddPassReturnAddedPass() throws Exception {
        Pass added = new PassBuilder()
                .id(1L)
                .tag(TAG)
                .build();

        when(passServiceMock.add(any(Pass.class))).thenReturn(added);

        mockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Pass created successfully")))
                .andExpect(jsonPath("$.pass.id", is(1)))
                .andExpect(jsonPath("$.pass.tag", is(TAG)));

        ArgumentCaptor<Pass> passArgumentCaptor = ArgumentCaptor.forClass(Pass.class);
        verify(passServiceMock, times(1)).add(passArgumentCaptor.capture());
        verifyNoMoreInteractions(passServiceMock);

        Pass passArgument = passArgumentCaptor.getValue();
        assertThat(passArgument.getId(), is(1L));
        assertThat(passArgument.getTag(), is(TAG));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptyPass_ShouldOccurNoInteractionsWanted() throws Exception {
        Pass pass = new Pass();

        mockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(passServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_StringAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String tag = TestUtil.createStringWithLength(Pass.MAX_LENGTH_TAG + 1);

        Pass pass = new PassBuilder()
                .tag(tag)
                .build();

        mockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(passServiceMock);
    }

    @Test
    public void testGetDetails_PassFound_ShouldReturnFoundPass() throws Exception {
        Pass found = new PassBuilder()
                .id(1L)
                .tag(TAG)
                .build();

        when(passServiceMock.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/passes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.tag", is(TAG)));

        verify(passServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(passServiceMock);
    }

    @Test
    public void testGetDetails_PassNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(passServiceMock.findById(1L)).thenThrow(new PassNotFoundException(""));

        mockMvc.perform(get("/api/passes/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(passServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(passServiceMock);
    }

    @Test
    public void testUpdate_PassFound_ShouldUpdatePassAndReturnIt() throws Exception {
        Pass updated = new PassBuilder()
                .id(1L)
                .tag(TAG)
                .build();

        when(passServiceMock.update(any(Pass.class))).thenReturn(updated);

        mockMvc.perform(put("/api/passes/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Pass updated successfully")))
                .andExpect(jsonPath("$.pass.id", is(1)))
                .andExpect(jsonPath("$.pass.tag", is(TAG)));

        ArgumentCaptor<Pass> passArgumentCaptor = ArgumentCaptor.forClass(Pass.class);
        verify(passServiceMock, times(1)).update(passArgumentCaptor.capture());
        verifyNoMoreInteractions(passServiceMock);

        Pass passArgument = passArgumentCaptor.getValue();
        assertThat(passArgument.getId(), is(1L));
        assertThat(passArgument.getTag(), is(TAG));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptyPass_ShouldOccurNoInteractionsWanted() throws Exception {
        Pass pass = new PassBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/passes/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(passServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_StringAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String tag = TestUtil.createStringWithLength(Pass.MAX_LENGTH_TAG + 1);

        Pass pass = new PassBuilder()
                .id(1L)
                .tag(tag)
                .build();

        mockMvc.perform(put("/api/passes/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(passServiceMock);
    }

    @Test
    public void testUpdate_PassNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Pass updated = new PassBuilder()
                .id(3L)
                .tag(TAG)
                .build();

        when(passServiceMock.update(any(Pass.class))).thenThrow(new PassNotFoundException(""));

        mockMvc.perform(put("/api/passes/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Pass> passArgumentCaptor = ArgumentCaptor.forClass(Pass.class);
        verify(passServiceMock, times(1)).update(passArgumentCaptor.capture());
        verifyNoMoreInteractions(passServiceMock);

        Pass passArgument = passArgumentCaptor.getValue();
        assertThat(passArgument.getId(), is(3L));
        assertThat(passArgument.getTag(), is(TAG));
    }

    @Test
    public void testDelete_PassFound_ShouldDeletePassAndReturnIt() throws Exception {
        Pass deleted = new PassBuilder()
                .id(1L)
                .tag(TAG)
                .build();

        when(passServiceMock.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/passes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Pass deleted successfully")))
                .andExpect(jsonPath("$.pass.id", is(1)))
                .andExpect(jsonPath("$.pass.tag", is(TAG)));

        verify(passServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(passServiceMock);
    }

    @Test
    public void testDelete_PassNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(passServiceMock.deleteById(3L)).thenThrow(new PassNotFoundException(""));

        mockMvc.perform(delete("/api/passes/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(passServiceMock, times(1)).deleteById(3L);
        verifyNoMoreInteractions(passServiceMock);
    }
}
