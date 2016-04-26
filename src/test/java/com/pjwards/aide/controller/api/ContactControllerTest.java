package com.pjwards.aide.controller.api;

import com.pjwards.aide.config.ApplicationConfig;
import com.pjwards.aide.config.TestConfig;
import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.domain.builder.ContactBuilder;
import com.pjwards.aide.domain.enums.ContactType;
import com.pjwards.aide.exception.ContactNotFoundException;
import com.pjwards.aide.service.contact.ContactService;
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
public class ContactControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactControllerTest.class);
    private static final ContactType TYPE = ContactType.EMAIL;
    private static final String URL = "contact url";

    private MockMvc mockMvc;

    @Qualifier("contactService")
    @Autowired
    private ContactService contactServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.reset(contactServiceMock);
    }

    @Test
    public void testGetAll_ContactsFound_ShoudReturnFoundContact() throws Exception {
        Contact first = new ContactBuilder()
                .id(1L)
                .type(ContactType.EMAIL)
                .url("contact1")
                .build();
        Contact second = new ContactBuilder()
                .id(2L)
                .type(ContactType.FACEBOOK)
                .url("contact2")
                .build();

        when(contactServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].type", is(ContactType.EMAIL.toString())))
                .andExpect(jsonPath("$[0].url", is("contact1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].type", is(ContactType.FACEBOOK.toString())))
                .andExpect(jsonPath("$[1].url", is("contact2")));

        verify(contactServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(contactServiceMock);
    }

    @Test
    public void testCreate_NewContact_ShouldAddContactReturnAddedContact() throws Exception {
        Contact added = new ContactBuilder()
                .id(1L)
                .type(TYPE)
                .url(URL)
                .build();

        when(contactServiceMock.add(any(Contact.class))).thenReturn(added);

        mockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(added))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Contact created successfully")))
                .andExpect(jsonPath("$.contact.id", is(1)))
                .andExpect(jsonPath("$.contact.type", is(TYPE.toString())))
                .andExpect(jsonPath("$.contact.url", is(URL)));

        ArgumentCaptor<Contact> contactArgumentCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(contactServiceMock, times(1)).add(contactArgumentCaptor.capture());
        verifyNoMoreInteractions(contactServiceMock);

        Contact contactArgument = contactArgumentCaptor.getValue();
        assertThat(contactArgument.getId(), is(1L));
        assertThat(contactArgument.getType(), is(TYPE));
        assertThat(contactArgument.getUrl(), is(URL));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_EmptyContact_ShouldOccurNoInteractionsWanted() throws Exception {
        Contact contact = new Contact();

        mockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(contactServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testCreate_StringAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String url = TestUtil.createStringWithLength(Contact.MAX_LENGTH_URL + 1);

        Contact contact = new ContactBuilder()
                .type(TYPE)
                .url(url)
                .build();

        mockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(contactServiceMock);
    }

    @Test
    public void testGetDetails_ContactFound_ShouldReturnFoundContact() throws Exception {
        Contact found = new ContactBuilder()
                .id(1L)
                .type(TYPE)
                .url(URL)
                .build();

        when(contactServiceMock.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/api/contacts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is(TYPE.toString())))
                .andExpect(jsonPath("$.url", is(URL)));

        verify(contactServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(contactServiceMock);
    }

    @Test
    public void testGetDetails_ContactNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(contactServiceMock.findById(1L)).thenThrow(new ContactNotFoundException(""));

        mockMvc.perform(get("/api/contacts/{id}", 1L))
                .andExpect(status().isBadRequest());

        verify(contactServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(contactServiceMock);
    }

    @Test
    public void testUpdate_ContactFound_ShouldUpdateContactAndReturnIt() throws Exception {
        Contact updated = new ContactBuilder()
                .id(1L)
                .type(TYPE)
                .url(URL)
                .build();

        when(contactServiceMock.update(any(Contact.class))).thenReturn(updated);

        mockMvc.perform(put("/api/contacts/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Contact updated successfully")))
                .andExpect(jsonPath("$.contact.id", is(1)))
                .andExpect(jsonPath("$.contact.type", is(TYPE.toString())))
                .andExpect(jsonPath("$.contact.url", is(URL)));

        ArgumentCaptor<Contact> contactArgumentCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(contactServiceMock, times(1)).update(contactArgumentCaptor.capture());
        verifyNoMoreInteractions(contactServiceMock);

        Contact contactArgument = contactArgumentCaptor.getValue();
        assertThat(contactArgument.getId(), is(1L));
        assertThat(contactArgument.getType(), is(TYPE));
        assertThat(contactArgument.getUrl(), is(URL));
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_EmptyContact_ShouldOccurNoInteractionsWanted() throws Exception {
        Contact contact = new ContactBuilder()
                .id(1L)
                .build();

        mockMvc.perform(put("/api/contacts/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(contactServiceMock);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testUpdate_StringAreTooLong_ShouldOccurNoInteractionsWanted() throws Exception {
        String url = TestUtil.createStringWithLength(Contact.MAX_LENGTH_URL + 1);

        Contact contact = new ContactBuilder()
                .id(1L)
                .type(TYPE)
                .url(url)
                .build();

        mockMvc.perform(put("/api/contacts/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        verifyZeroInteractions(contactServiceMock);
    }

    @Test
    public void testUpdate_ContactNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        Contact updated = new ContactBuilder()
                .id(3L)
                .type(TYPE)
                .url(URL)
                .build();

        when(contactServiceMock.update(any(Contact.class))).thenThrow(new ContactNotFoundException(""));

        mockMvc.perform(put("/api/contacts/{id}", 3L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updated))
        )
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Contact> contactArgumentCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(contactServiceMock, times(1)).update(contactArgumentCaptor.capture());
        verifyNoMoreInteractions(contactServiceMock);

        Contact contactArgument = contactArgumentCaptor.getValue();
        assertThat(contactArgument.getId(), is(3L));
        assertThat(contactArgument.getType(), is(TYPE));
        assertThat(contactArgument.getUrl(), is(URL));
    }

    @Test
    public void testDelete_ContactFound_ShouldDeleteContactAndReturnIt() throws Exception {
        Contact deleted = new ContactBuilder()
                .id(1L)
                .type(TYPE)
                .url(URL)
                .build();

        when(contactServiceMock.deleteById(1L)).thenReturn(deleted);

        mockMvc.perform(delete("/api/contacts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("Contact deleted successfully")))
                .andExpect(jsonPath("$.contact.id", is(1)))
                .andExpect(jsonPath("$.contact.type", is(TYPE.toString())))
                .andExpect(jsonPath("$.contact.url", is(URL)));

        verify(contactServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(contactServiceMock);
    }

    @Test
    public void testDelete_ContactNotFound_ShouldReturnHttpStatusCode400() throws Exception {
        when(contactServiceMock.deleteById(3L)).thenThrow(new ContactNotFoundException(""));

        mockMvc.perform(delete("/api/contacts/{id}", 3L))
                .andExpect(status().isBadRequest());

        verify(contactServiceMock, times(1)).deleteById(3L);
        verifyNoMoreInteractions(contactServiceMock);
    }
}
