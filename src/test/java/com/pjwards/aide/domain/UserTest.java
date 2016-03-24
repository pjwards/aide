package com.pjwards.aide.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User user;
    private Date date;

    @Before
    public void setup(){
        user = new User();
        date = new Date();
        user.setId(1L).setName("jisung").setEmail("a@a.com").setPassword("4194105091094")
                .setCreatedDate(date).setLastDate(date).setCompany("google");
    }

    @Test
    public void testUser() throws Exception{
        assertEquals(user.getCompany(),"google");
        assertEquals(user.getCreatedDate(),date);
        assertEquals(user.getEmail(),"a@a.com");
        assertEquals(user.getId(),(Long)1L);
        assertEquals(user.getLastDate(),date);
        assertEquals(user.getName(),"jisung");
        assertEquals(user.getPassword(),"4194105091094");

    }
}
