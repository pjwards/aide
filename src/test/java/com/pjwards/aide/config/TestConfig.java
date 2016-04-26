package com.pjwards.aide.config;

import com.pjwards.aide.service.assets.AssetsService;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.conferencerole.ConferenceRoleService;
import com.pjwards.aide.service.pass.PassService;
import com.pjwards.aide.service.program.ProgramService;
import com.pjwards.aide.service.programdate.ProgramDateService;
import com.pjwards.aide.service.room.RoomService;
import com.pjwards.aide.service.session.SessionService;
import com.pjwards.aide.service.sponsor.SponsorService;
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

    @Bean
    public SponsorService sponsorService() {
        return Mockito.mock(SponsorService.class);
    }

    @Bean
    public AssetsService assetsService() {
        return Mockito.mock(AssetsService.class);
    }

    @Bean
    public PassService passService() {
        return Mockito.mock(PassService.class);
    }

    @Bean
    public SessionService sessionService() {
        return Mockito.mock(SessionService.class);
    }
}
