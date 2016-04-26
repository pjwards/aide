package com.pjwards.aide.service.contact;

import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.domain.builder.ContactBuilder;
import com.pjwards.aide.domain.enums.ContactType;
import com.pjwards.aide.exception.ContactNotFoundException;
import com.pjwards.aide.repository.ContactRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ContactServiceImplTest {

    private static final Long ID = 1L;
    private static final ContactType TYPE = ContactType.EMAIL;
    private static final String URL = "contact url";
    private static final ContactType UPDATED_TYPE = ContactType.FACEBOOK;
    private static final String UPDATED_URL = "updated contact url";

    private ContactRepository contactRepositoryMock;
    private ContactService contactService;

    @Before
    public void setup() {
        contactRepositoryMock = mock(ContactRepository.class);
        contactService = new ContactServiceImpl(contactRepositoryMock);
    }

    @Test
    public void testFindAll_ShouldReturnListOfContact() {
        List<Contact> models = new ArrayList<>();
        when(contactRepositoryMock.findAll()).thenReturn(models);

        List<Contact> actual = contactService.findAll();

        verify(contactRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(contactRepositoryMock);

        assertThat(actual, is(models));
    }

    @Test
    public void testAdd_NewContact_ShouldSaveContact() {
        Contact contact = new ContactBuilder()
                .type(TYPE)
                .url(URL)
                .build();

        contactService.add(contact);

        ArgumentCaptor<Contact> contactArgumentCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(contactRepositoryMock, times(1)).save(contactArgumentCaptor.capture());
        verifyNoMoreInteractions(contactRepositoryMock);

        Contact model = contactArgumentCaptor.getValue();

        assertNull(model.getId());
        assertThat(model.getType(), is(contact.getType()));
        assertThat(model.getUrl(), is(contact.getUrl()));
    }

    @Test
    public void testFindById_ContactFound_ShouldReturnFoundContact() throws ContactNotFoundException {
        Contact model = new ContactBuilder()
                .id(ID)
                .type(TYPE)
                .url(URL)
                .build();

        when(contactRepositoryMock.findOne(ID)).thenReturn(model);

        Contact actual = contactService.findById(ID);

        verify(contactRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(contactRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ContactNotFoundException.class)
    public void testFindById_ContactNotFound_ShouldThrowException() throws ContactNotFoundException {
        when(contactRepositoryMock.findOne(ID)).thenReturn(null);

        contactService.findById(ID);

        verify(contactRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(contactRepositoryMock);
    }

    @Test
    public void testUpdate_ContactFound_ShouldUpdateContact() throws ContactNotFoundException {
        Contact updated = new ContactBuilder()
                .id(ID)
                .type(UPDATED_TYPE)
                .url(UPDATED_URL)
                .build();
        Contact model = new ContactBuilder()
                .id(ID)
                .type(TYPE)
                .url(URL)
                .build();

        when(contactRepositoryMock.findOne(updated.getId())).thenReturn(model);

        contactService.update(updated);

        verify(contactRepositoryMock, times(1)).findOne(model.getId());
        verify(contactRepositoryMock, times(1)).save(model);
        verifyNoMoreInteractions(contactRepositoryMock);

        assertThat(model.getId(), is(updated.getId()));
        assertThat(model.getType(), is(updated.getType()));
        assertThat(model.getUrl(), is(updated.getUrl()));
    }

    @Test(expected = ContactNotFoundException.class)
    public void testUpdate_ContactNotFound_ShouldThrowException() throws ContactNotFoundException {
        Contact updated = new ContactBuilder()
                .id(ID)
                .type(UPDATED_TYPE)
                .url(UPDATED_URL)
                .build();

        when(contactRepositoryMock.findOne(updated.getId())).thenReturn(null);

        contactService.update(updated);

        verify(contactRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(contactRepositoryMock);
    }

    @Test
    public void testDeleteById_ContactFound_ShouldDeleteContactAndReturnIt() throws ContactNotFoundException {
        Contact model = new ContactBuilder()
                .id(ID)
                .type(TYPE)
                .url(URL)
                .build();

        when(contactRepositoryMock.findOne(ID)).thenReturn(model);

        Contact actual = contactService.deleteById(ID);

        verify(contactRepositoryMock, times(1)).findOne(ID);
        verify(contactRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(contactRepositoryMock);

        assertThat(actual, is(model));
    }

    @Test(expected = ContactNotFoundException.class)
    public void testDeleteById_ContactNotFound_ShouldThrowException() throws ContactNotFoundException {
        when(contactRepositoryMock.findOne(ID)).thenReturn(null);

        contactService.deleteById(ID);

        verify(contactRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(contactRepositoryMock);
    }
}
