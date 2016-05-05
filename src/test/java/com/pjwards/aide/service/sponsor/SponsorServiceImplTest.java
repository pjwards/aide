package com.pjwards.aide.service.sponsor;


import com.pjwards.aide.domain.Sponsor;
import com.pjwards.aide.domain.builder.SponsorBuilder;
import com.pjwards.aide.exception.SponsorNotFoundException;
import com.pjwards.aide.repository.SponsorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class SponsorServiceImplTest {
    private static final Long ID = 1L;
    private static final String SLUG = "peter";
    private static final String NAME = "jisung";
    private static final String URL = "www.google.com";
    private static final String DESCRIPTION = "hellomynameisjisungjeon";
    private static final String UPDATE_NAME = "seodong";
    private static final String UPDATE_SLUG = "sam";
    private static final String UPDATE_URL= "www.facebook.com";
    private static final String UPDATE_DESCRIPTION = "hellomynameisseodong";

    private SponsorRepository sponsorRepositoryMock;
    private SponsorService sponsorService;

    @Before
    public void setup(){
        sponsorRepositoryMock = mock(SponsorRepository.class);
        sponsorService = new SponsorServiceImpl(sponsorRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfSponsor(){
        List<Sponsor> models = new ArrayList<>();
        when(sponsorRepositoryMock.findAll()).thenReturn(models);

        List<Sponsor> actual = sponsorService.findAll();

        verify(sponsorRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(sponsorRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewSponsor_ShouldSaveSponsor() {
        Sponsor sponsor = new SponsorBuilder()
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        sponsorService.add(sponsor);

        ArgumentCaptor<Sponsor> sponsorArgumentCaptor = ArgumentCaptor.forClass(Sponsor.class);
        verify(sponsorRepositoryMock, times(1)).save(sponsorArgumentCaptor.capture());
        verifyNoMoreInteractions(sponsorRepositoryMock);

        Sponsor model = sponsorArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getName(), is(NAME));
        assertThat(model.getSlug(), is(SLUG));
        assertThat(model.getUrl(), is(URL));
        assertThat(model.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testFindById_SponsorFound_ShouldReturnFoundSponsor() throws SponsorNotFoundException{
        Sponsor model = new SponsorBuilder()
                .id(ID)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        when(sponsorRepositoryMock.findOne(ID)).thenReturn(model);

        Sponsor actual = sponsorService.findById(ID);

        verify(sponsorRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(sponsorRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = SponsorNotFoundException.class)
    public void testFindById_SponsorNotFound_ShouldThrowException() throws SponsorNotFoundException{
        when(sponsorRepositoryMock.findOne(ID)).thenReturn(null);

        sponsorService.findById(ID);

        verify(sponsorRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(sponsorRepositoryMock);
    }

    @Test
    public void testUpdate_SponsorFound_ShouldUpdateSponsor() throws SponsorNotFoundException{
        Sponsor updated = new SponsorBuilder()
                .id(ID)
                .slug(UPDATE_SLUG)
                .name(UPDATE_NAME)
                .url(UPDATE_URL)
                .description(UPDATE_DESCRIPTION)
                .build();

        Sponsor model = new SponsorBuilder()
                .id(ID)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        when(sponsorRepositoryMock.findOne(updated.getId())).thenReturn(model);

        sponsorService.update(updated);

        verify(sponsorRepositoryMock, times(1)).findOne(model.getId());
        verify(sponsorRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(sponsorRepositoryMock);


        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getName(), is(updated.getName()));
        assertThat(model.getSlug(), is(updated.getSlug()));
        assertThat(model.getUrl(), is(updated.getUrl()));
        assertThat(model.getDescription(), is(updated.getDescription()));
    }

    @Test(expected = SponsorNotFoundException.class)
    public void testUpdate_SponsorNotFound_ShouldThrowException() throws SponsorNotFoundException{
        Sponsor updated = new SponsorBuilder()
                .id(ID)
                .slug(UPDATE_SLUG)
                .name(UPDATE_NAME)
                .url(UPDATE_URL)
                .description(UPDATE_DESCRIPTION)
                .build();

        when(sponsorRepositoryMock.findOne(updated.getId())).thenReturn(null);

        sponsorService.update(updated);

        verify(sponsorRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(sponsorRepositoryMock);
    }

    @Test
    public void testDeleteById_SponsorFound_ShouldDeleteSponsorAndReturnIt() throws SponsorNotFoundException{
        Sponsor model = new SponsorBuilder()
                .id(ID)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        when(sponsorRepositoryMock.findOne(ID)).thenReturn(model);

        Sponsor actual = sponsorService.deleteById(ID);

        verify(sponsorRepositoryMock, times(1)).findOne(ID);
        verify(sponsorRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(sponsorRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = SponsorNotFoundException.class)
    public void testDeleteById_SponsorNotFound_ShouldThrowException() throws SponsorNotFoundException{
        when(sponsorRepositoryMock.findOne(ID)).thenReturn(null);

        sponsorService.deleteById(ID);

        verify(sponsorRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(sponsorRepositoryMock);
    }
}
