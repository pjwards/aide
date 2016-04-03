package com.pjwards.aide.config;

import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.conferencerole.ConferenceRoleService;
import com.pjwards.aide.service.program.ProgramService;
import com.pjwards.aide.service.programdate.ProgramDateService;
import com.pjwards.aide.service.room.RoomService;
import com.pjwards.aide.service.user.UserService;
import com.sun.deploy.config.DefaultConfig;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig extends DefaultConfig {

    @Bean
    public ConferenceService conferenceService() {
        return Mockito.mock(ConferenceService.class);
    }

    @Bean
    public RoomService roomService() {
        return Mockito.mock(RoomService.class);
    }

    @Bean
    public ProgramDateService programDateService() {
        return Mockito.mock(ProgramDateService.class);
    }

    @Bean
    public ProgramService programService() {
        return Mockito.mock(ProgramService.class);
    }

    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public ConferenceRoleService conferenceRoleService() {
        return Mockito.mock(ConferenceRoleService.class);
    }
}
