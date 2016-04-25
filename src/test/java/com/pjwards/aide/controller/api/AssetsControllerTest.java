package com.pjwards.aide.controller.api;


import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.builder.AssetsBuilder;
import com.pjwards.aide.exception.AssetsNotFoundException;
import com.pjwards.aide.service.assets.AssetsService;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class AssetsControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsControllerTest.class);
    private static final String FILENAME = "a.ppt";
    private static final String REAL_PATH = "file/filename";
    private static final Long FILE_SIZE = 20L;
    private static final Integer DOWNLOAD_COUNT = 122;

    private MockMvc mockMvc;

    @Qualifier("assetsService")
    @Autowired
    private AssetsService assetsServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(assetsServiceMock);
    }

    @Test
    public void testGetAll_AssetsFound_ShouldReturnFoundAssets() throws Exception {
        Assets first = new AssetsBuilder()
                .id(1L)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        Assets second = new AssetsBuilder()
                .id(2L)
                .fileName("filename")
                .fileSize(22L)
                .realPath("real_path")
                .downloadCount(22)
                .build();

        when(assetsServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/assets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].fileName", is(FILENAME)))
                .andExpect(jsonPath("$[0].fileSize", is(20)))
                .andExpect(jsonPath("$[0].realPath", is(REAL_PATH)))
                .andExpect(jsonPath("$[0].downloadCount", is(DOWNLOAD_COUNT)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].fileName", is("filename")))
                .andExpect(jsonPath("$[1].fileSize", is(22)))
                .andExpect(jsonPath("$[1].realPath", is("real_path")))
                .andExpect(jsonPath("$[1].downloadCount", is(22)));

        verify(assetsServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(assetsServiceMock);
    }

    @Test
    public void testCreate_NewAssets_ShouldAddAssetsReturnAddedAssets() throws Exception {
        Assets added = new AssetsBuilder()
                .id(1L)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        when(assetsServiceMock.add(any(Assets.class))).thenReturn(added);

        mockMvc.perform(post("/api/assets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Assets created successfully")))
                .andExpect(jsonPath("$.assets.id", is(1)))
                .andExpect(jsonPath("$.assets.fileName", is(FILENAME)))
                .andExpect(jsonPath("$.assets.fileSize", is(20)))
                .andExpect(jsonPath("$.assets.realPath", is(REAL_PATH)))
                .andExpect(jsonPath("$.assets.downloadCount", is(DOWNLOAD_COUNT)));

        ArgumentCaptor<Assets> assetsArgumentCaptor = ArgumentCaptor.forClass(Assets.class);
        verify(assetsServiceMock, times(1)).add(assetsArgumentCaptor.capture());
        verifyNoMoreInteractions(assetsServiceMock);

        Assets assetsArgument = assetsArgumentCaptor.getValue();
        assertThat(assetsArgument.getId(), is(1L));
        assertThat(assetsArgument.getFileName(), is(FILENAME));
        assertThat(assetsArgument.getFileSize(), is(FILE_SIZE));
        assertThat(assetsArgument.getRealPath(), is(REAL_PATH));
        assertThat(assetsArgument.getDownloadCount(), is(DOWNLOAD_COUNT));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptyAssets_ShouldOccurNoInteractionsWanted() throws Exception {
        Assets assets = new Assets();

        mockMvc.perform(post("/api/assets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assets))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(assetsServiceMock);
    }

    @Test
    public void testGetDetails_AssetsFound_ShouldReturnFoundAssets() throws Exception {
        Assets found = new AssetsBuilder()
                .id(1L)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        when(assetsServiceMock.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/assets/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fileName", is(FILENAME)))
                .andExpect(jsonPath("$.fileSize", is(20)))
                .andExpect(jsonPath("$.realPath", is(REAL_PATH)))
                .andExpect(jsonPath("$.downloadCount", is(DOWNLOAD_COUNT)));

        verify(assetsServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(assetsServiceMock);
    }

    @Test
    public void testGetDetails_AssetsNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(assetsServiceMock.findById(1L)).thenThrow(new AssetsNotFoundException(""));

        mockMvc.perform(get("/api/assets/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(assetsServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(assetsServiceMock);
    }

    @Test
    public void testUpdate_AssetsFound_ShouldUpdateAssetsAndReturnIt() throws Exception {
        Assets updated = new AssetsBuilder()
                .id(1L)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        when(assetsServiceMock.update(any(Assets.class))).thenReturn(updated);

        mockMvc.perform(put("/api/assets/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Assets updated successfully")))
                .andExpect(jsonPath("$.assets.id", is(1)))
                .andExpect(jsonPath("$.assets.fileName", is(FILENAME)))
                .andExpect(jsonPath("$.assets.fileSize", is(20)))
                .andExpect(jsonPath("$.assets.realPath", is(REAL_PATH)))
                .andExpect(jsonPath("$.assets.downloadCount", is(DOWNLOAD_COUNT)));

        ArgumentCaptor<Assets> assetsArgumentCaptor = ArgumentCaptor.forClass(Assets.class);
        verify(assetsServiceMock, times(1)).update(assetsArgumentCaptor.capture());
        verifyNoMoreInteractions(assetsServiceMock);

        Assets assetsArgument = assetsArgumentCaptor.getValue();
        assertThat(assetsArgument.getId(), is(1L));
        assertThat(assetsArgument.getFileName(), is(FILENAME));
        assertThat(assetsArgument.getFileSize(), is(FILE_SIZE));
        assertThat(assetsArgument.getRealPath(), is(REAL_PATH));
        assertThat(assetsArgument.getDownloadCount(), is(DOWNLOAD_COUNT));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptyAssets_ShouldOccurNoInteractionsWanted() throws Exception {
        Assets assets = new AssetsBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/assets/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assets))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(assetsServiceMock);
    }

    @Test
    public void testUpdate_AssetsNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Assets updated = new AssetsBuilder()
                .id(3L)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        when(assetsServiceMock.update(any(Assets.class))).thenThrow(new AssetsNotFoundException(""));

        mockMvc.perform(put("/api/assets/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Assets> assetsArgumentCaptor = ArgumentCaptor.forClass(Assets.class);
        verify(assetsServiceMock, times(1)).update(assetsArgumentCaptor.capture());
        verifyNoMoreInteractions(assetsServiceMock);

        Assets assetsArgument = assetsArgumentCaptor.getValue();
        assertThat(assetsArgument.getId(), is(3L));
        assertThat(assetsArgument.getFileName(), is(FILENAME));
        assertThat(assetsArgument.getFileSize(), is(FILE_SIZE));
        assertThat(assetsArgument.getRealPath(), is(REAL_PATH));
        assertThat(assetsArgument.getDownloadCount(), is(DOWNLOAD_COUNT));
    }

    @Test
    public void testDelete_AssetsFound_ShouldDeleteAssetsAndReturnIt() throws Exception {
        Assets deleted = new AssetsBuilder()
                .id(1L)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        when(assetsServiceMock.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/assets/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Assets deleted successfully")))
                .andExpect(jsonPath("$.assets.id", is(1)))
                .andExpect(jsonPath("$.assets.fileName", is(FILENAME)))
                .andExpect(jsonPath("$.assets.fileSize", is(20)))
                .andExpect(jsonPath("$.assets.realPath", is(REAL_PATH)))
                .andExpect(jsonPath("$.assets.downloadCount", is(DOWNLOAD_COUNT)));

        verify(assetsServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(assetsServiceMock);
    }

    @Test
    public void testDelete_AssetsNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(assetsServiceMock.deleteById(3L)).thenThrow(new AssetsNotFoundException(""));

        mockMvc.perform(delete("/api/assets/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(assetsServiceMock, times(1)).deleteById(3L);
        verifyNoMoreInteractions(assetsServiceMock);
    }

}
