package com.pjwards.aide.service.Assets;


import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.builder.AssetsBuilder;
import com.pjwards.aide.exception.AssetsNotFoundException;
import com.pjwards.aide.repository.AssetsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AssetsServiceImplTest {
    private static final Long ID = 1L;
    private static final String FILENAME = "a.ppt";
    private static final String REAL_PATH = "file/filename";
    private static final Long FILE_SIZE = 20L;
    private static final Integer DOWNLOAD_COUNT = 122;
    private static final String UPDATE_FILENAME = "b.ppt";
    private static final String UPDATE_REAL_PATH = "file/filename/ad";
    private static final Long UPDATE_FILE_SIZE = 30L;
    private static final Integer UPDATE_DOWNLOAD_COUNT = 2;


    private AssetsRepository assetsRepositoryMock;
    private AssetsService assetsService;

    @Before
    public void setup(){
        assetsRepositoryMock = mock(AssetsRepository.class);
        assetsService = new AssetsServiceImpl(assetsRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfAssets(){
        List<Assets> models = new ArrayList<>();
        when(assetsRepositoryMock.findAll()).thenReturn(models);

        List<Assets> actual = assetsService.findAll();

        verify(assetsRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(assetsRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewAssets_ShouldSaveAssets() {
        Assets assets = new AssetsBuilder()
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        assetsService.add(assets);

        ArgumentCaptor<Assets> assetsArgumentCaptor = ArgumentCaptor.forClass(Assets.class);
        verify(assetsRepositoryMock, times(1)).save(assetsArgumentCaptor.capture());
        verifyNoMoreInteractions(assetsRepositoryMock);

        Assets model = assetsArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getFileName(), is(FILENAME));
        assertThat(model.getFileSize(), is(FILE_SIZE));
        assertThat(model.getRealPath(), is(REAL_PATH));
        assertThat(model.getDownloadCount(), is(DOWNLOAD_COUNT));
    }

    @Test
    public void testFindById_AssetsFound_ShouldReturnFoundAssets() throws AssetsNotFoundException{
        Assets model = new AssetsBuilder()
                .id(ID)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        when(assetsRepositoryMock.findOne(ID)).thenReturn(model);

        Assets actual = assetsService.findById(ID);

        verify(assetsRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(assetsRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = AssetsNotFoundException.class)
    public void testFindById_AssetsNotFound_ShouldThrowException() throws AssetsNotFoundException{
        when(assetsRepositoryMock.findOne(ID)).thenReturn(null);

        assetsService.findById(ID);

        verify(assetsRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(assetsRepositoryMock);
    }

    @Test
    public void testUpdate_AssetsFound_ShouldUpdateAssets() throws AssetsNotFoundException{
        Assets updated = new AssetsBuilder()
                .id(ID)
                .fileName(UPDATE_FILENAME)
                .fileSize(UPDATE_FILE_SIZE)
                .realPath(UPDATE_REAL_PATH)
                .downloadCount(UPDATE_DOWNLOAD_COUNT)
                .build();

        Assets model = new AssetsBuilder()
                .id(ID)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        when(assetsRepositoryMock.findOne(updated.getId())).thenReturn(model);

        assetsService.update(updated);

        verify(assetsRepositoryMock, times(1)).findOne(model.getId());
        verify(assetsRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(assetsRepositoryMock);


        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getFileName(), is(updated.getFileName()));
        assertThat(model.getFileSize(), is(updated.getFileSize()));
        assertThat(model.getRealPath(), is(updated.getRealPath()));
        assertThat(model.getDownloadCount(), is(updated.getDownloadCount()));
    }

    @Test(expected = AssetsNotFoundException.class)
    public void testUpdate_AssetsNotFound_ShouldThrowException() throws AssetsNotFoundException{
        Assets updated = new AssetsBuilder()
                .id(ID)
                .fileName(UPDATE_FILENAME)
                .fileSize(UPDATE_FILE_SIZE)
                .realPath(UPDATE_REAL_PATH)
                .downloadCount(UPDATE_DOWNLOAD_COUNT)
                .build();

        when(assetsRepositoryMock.findOne(updated.getId())).thenReturn(null);

        assetsService.update(updated);

        verify(assetsRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(assetsRepositoryMock);
    }

    @Test
    public void testDeleteById_AssetsFound_ShouldDeleteAssetsAndReturnIt() throws AssetsNotFoundException{
        Assets model = new AssetsBuilder()
                .id(ID)
                .fileName(FILENAME)
                .fileSize(FILE_SIZE)
                .realPath(REAL_PATH)
                .downloadCount(DOWNLOAD_COUNT)
                .build();

        when(assetsRepositoryMock.findOne(ID)).thenReturn(model);

        Assets actual = assetsService.deleteById(ID);

        verify(assetsRepositoryMock, times(1)).findOne(ID);
        verify(assetsRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(assetsRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = AssetsNotFoundException.class)
    public void testDeleteById_AssetsNotFound_ShouldThrowException() throws AssetsNotFoundException{
        when(assetsRepositoryMock.findOne(ID)).thenReturn(null);

        assetsService.deleteById(ID);

        verify(assetsRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(assetsRepositoryMock);
    }

}
