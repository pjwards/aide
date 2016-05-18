package com.pjwards.aide.controller.api;


import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.domain.Sponsor;
import com.pjwards.aide.domain.builder.SponsorBuilder;
import com.pjwards.aide.exception.SponsorNotFoundException;
import com.pjwards.aide.service.sponsor.SponsorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class SponsorControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SponsorControllerTest.class);
    private static final String SLUG = "peter";
    private static final String NAME = "jisung";
    private static final String URL = "www.google.com";
    private static final String DESCRIPTION = "hellomynameisjisungjeon";

    private MockMvc mockMvc;

//    @Qualifier("sponsorService")
    @Autowired
    private SponsorService sponsorServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(sponsorServiceMock);
    }

    @Test
    public void testGetAll_SponsorsFound_ShouldReturnFoundSponsor() throws Exception {
        Sponsor first = new SponsorBuilder()
                .id(1L)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        Sponsor second = new SponsorBuilder()
                .id(2L)
                .slug("slug2")
                .name("name2")
                .url("url2")
                .description("description2")
                .build();

        when(sponsorServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/sponsors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(NAME)))
                .andExpect(jsonPath("$[0].slug", is(SLUG)))
                .andExpect(jsonPath("$[0].url", is(URL)))
                .andExpect(jsonPath("$[0].description", is(DESCRIPTION)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[1].slug", is("slug2")))
                .andExpect(jsonPath("$[1].url", is("url2")))
                .andExpect(jsonPath("$[1].description", is("description2")));

        verify(sponsorServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(sponsorServiceMock);
    }

    @Test
    public void testCreate_NewSponsor_ShouldAddSponsorReturnAddedSponsor() throws Exception {
        Sponsor added = new SponsorBuilder()
                .id(1L)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        when(sponsorServiceMock.add(any(Sponsor.class))).thenReturn(added);

        mockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Sponsor created successfully")))
                .andExpect(jsonPath("$.sponsor.id", is(1)))
                .andExpect(jsonPath("$.sponsor.name", is(NAME)))
                .andExpect(jsonPath("$.sponsor.slug", is(SLUG)))
                .andExpect(jsonPath("$.sponsor.url", is(URL)))
                .andExpect(jsonPath("$.sponsor.description", is(DESCRIPTION)));

        ArgumentCaptor<Sponsor> sponsorArgumentCaptor = ArgumentCaptor.forClass(Sponsor.class);
        verify(sponsorServiceMock, times(1)).add(sponsorArgumentCaptor.capture());
        verifyNoMoreInteractions(sponsorServiceMock);

        Sponsor sponsorArgument = sponsorArgumentCaptor.getValue();
        assertThat(sponsorArgument.getId(), is(1L));
        assertThat(sponsorArgument.getName(), is(NAME));
        assertThat(sponsorArgument.getUrl(), is(URL));
        assertThat(sponsorArgument.getSlug(), is(SLUG));
        assertThat(sponsorArgument.getDescription(), is(DESCRIPTION));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptySponsor_ShouldOccurNoInteractionsWanted() throws Exception {
        Sponsor sponsor = new Sponsor();

        mockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(sponsorServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_NameAndSlugAndURLAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(Sponsor.MAX_LENGTH_STRING + 1);
        String slug = TestUtil.createStringWithLength(Sponsor.MAX_LENGTH_STRING + 1);
        String url = TestUtil.createStringWithLength(Sponsor.MAX_LENGTH_URL + 1);

        Sponsor sponsor = new SponsorBuilder()
                .id(1L)
                .slug(slug)
                .name(name)
                .url(url)
                .description(DESCRIPTION)
                .build();

        mockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(sponsorServiceMock);
    }

    @Test
    public void testGetDetails_SponsorFound_ShouldReturnFoundSponsor() throws Exception {
        Sponsor found = new SponsorBuilder()
                .id(1L)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        when(sponsorServiceMock.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/sponsors/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.slug", is(SLUG)))
                .andExpect(jsonPath("$.url", is(URL)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));

        verify(sponsorServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(sponsorServiceMock);
    }

    @Test
    public void testGetDetails_SponsorNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(sponsorServiceMock.findById(1L)).thenThrow(new SponsorNotFoundException(""));

        mockMvc.perform(get("/api/sponsors/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(sponsorServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(sponsorServiceMock);
    }

    @Test
    public void testUpdate_SponsorFound_ShouldUpdateSponsorAndReturnIt() throws Exception {
        Sponsor updated = new SponsorBuilder()
                .id(1L)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        when(sponsorServiceMock.update(any(Sponsor.class))).thenReturn(updated);

        mockMvc.perform(put("/api/sponsors/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Sponsor updated successfully")))
                .andExpect(jsonPath("$.sponsor.id", is(1)))
                .andExpect(jsonPath("$.sponsor.name", is(NAME)))
                .andExpect(jsonPath("$.sponsor.slug", is(SLUG)))
                .andExpect(jsonPath("$.sponsor.url", is(URL)))
                .andExpect(jsonPath("$.sponsor.description", is(DESCRIPTION)));

        ArgumentCaptor<Sponsor> sponsorArgumentCaptor = ArgumentCaptor.forClass(Sponsor.class);
        verify(sponsorServiceMock, times(1)).update(sponsorArgumentCaptor.capture());
        verifyNoMoreInteractions(sponsorServiceMock);

        Sponsor sponsorArgument = sponsorArgumentCaptor.getValue();
        assertThat(sponsorArgument.getId(), is(1L));
        assertThat(sponsorArgument.getName(), is(NAME));
        assertThat(sponsorArgument.getUrl(), is(URL));
        assertThat(sponsorArgument.getSlug(), is(SLUG));
        assertThat(sponsorArgument.getDescription(), is(DESCRIPTION));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptySponsor_ShouldOccurNoInteractionsWanted() throws Exception {
        Sponsor sponsor = new SponsorBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/sponsors/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(sponsorServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_NameAndSlugAndURLAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String name = TestUtil.createStringWithLength(Sponsor.MAX_LENGTH_STRING + 1);
        String slug = TestUtil.createStringWithLength(Sponsor.MAX_LENGTH_STRING + 1);
        String url = TestUtil.createStringWithLength(Sponsor.MAX_LENGTH_URL + 1);

        Sponsor sponsor = new SponsorBuilder()
                .id(1L)
                .slug(slug)
                .name(name)
                .url(url)
                .description(DESCRIPTION)
                .build();

        mockMvc.perform(put("/api/sponsors/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(sponsorServiceMock);
    }

    @Test
    public void testUpdate_SponsorNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Sponsor updated = new SponsorBuilder()
                .id(3L)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        when(sponsorServiceMock.update(any(Sponsor.class))).thenThrow(new SponsorNotFoundException(""));

        mockMvc.perform(put("/api/sponsors/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Sponsor> sponsorArgumentCaptor = ArgumentCaptor.forClass(Sponsor.class);
        verify(sponsorServiceMock, times(1)).update(sponsorArgumentCaptor.capture());
        verifyNoMoreInteractions(sponsorServiceMock);

        Sponsor sponsorArgument = sponsorArgumentCaptor.getValue();
        assertThat(sponsorArgument.getId(), is(3L));
        assertThat(sponsorArgument.getName(), is(NAME));
        assertThat(sponsorArgument.getUrl(), is(URL));
        assertThat(sponsorArgument.getSlug(), is(SLUG));
        assertThat(sponsorArgument.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testDelete_SponsorFound_ShouldDeleteSponsorAndReturnIt() throws Exception {
        Sponsor deleted = new SponsorBuilder()
                .id(1L)
                .slug(SLUG)
                .name(NAME)
                .url(URL)
                .description(DESCRIPTION)
                .build();

        when(sponsorServiceMock.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/sponsors/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Sponsor deleted successfully")))
                .andExpect(jsonPath("$.sponsor.id", is(1)))
                .andExpect(jsonPath("$.sponsor.name", is(NAME)))
                .andExpect(jsonPath("$.sponsor.slug", is(SLUG)))
                .andExpect(jsonPath("$.sponsor.url", is(URL)))
                .andExpect(jsonPath("$.sponsor.description", is(DESCRIPTION)));

        verify(sponsorServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(sponsorServiceMock);
    }

    @Test
    public void testDelete_SponsorNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(sponsorServiceMock.deleteById(3L)).thenThrow(new SponsorNotFoundException(""));

        mockMvc.perform(delete("/api/sponsors/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(sponsorServiceMock, times(1)).deleteById(3L);
        verifyNoMoreInteractions(sponsorServiceMock);
    }

}
