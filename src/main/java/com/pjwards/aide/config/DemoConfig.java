package com.pjwards.aide.config;

import com.pjwards.aide.domain.*;
import com.pjwards.aide.domain.enums.Charge;
import com.pjwards.aide.domain.enums.ContactType;
import com.pjwards.aide.domain.enums.ProgramType;
import com.pjwards.aide.domain.forms.SignUpForm;
import com.pjwards.aide.repository.*;
import com.pjwards.aide.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ComponentScan("com.pjwards.aide")
public class DemoConfig {
    @Bean
    public CommandLineRunner conferenceDemo(ConferenceRepository conferenceRepository,
                                            RoomRepository roomRepository,
                                            ProgramDateRepository programDateRepository,
                                            ProgramRepository programRepository,
                                            SessionRepository sessionRepository,
                                            ContactRepository contactRepository,
                                            UserService userService) {
        return (args) -> {
            Conference conference = conferenceRepository.save(new Conference.Builder("DEVIEW 2015", "DEVIEW 2015가 성황리에 끝났습니다.",
                    "<h2>excellence . sharing . growth</h2>\n" +
                            "\n" +
                            "다양한 분야에서 탄탄한 실력을 갖춘 국내외 IT기업 엔지니어들의 \n" +
                            "실전경험과 노하우가 담긴 컨텐츠를 공유합니다.\n" +
                            "\n").location("COEX Grand Ballroom, SEOUL").locationUrl("https://www.google.co.kr/maps/place/%EC%BD%94%EC%97%91%EC%8A%A4+%EC%BB%A8%EB%B2%A4%EC%85%98%EC%84%BC%ED%84%B0+%EA%B7%B8%EB%9E%9C%EB%93%9C%EB%B3%BC%EB%A3%B8/@37.5081321,127.0336615,14z/data=!4m5!1m2!2m1!1scoex!3m1!1s0x357ca46bcb9e129f:0xd6bf8dde518b69a4")
                    .latlan(37.513204, 127.058638).charge(Charge.CHARGED).price(100000).build());

            conferenceRepository.save(new Conference.Builder("PYCON KOREA 2014", "한국에서 열리는 첫 번째 파이콘", "한국에서 열리는 첫 번째 파이콘").build());

            SignUpForm signUpForm = new SignUpForm();
            signUpForm.setName("홍길동");
            signUpForm.setEmail("a@a.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            Set<User> userSet = new HashSet<>();
            userSet.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("description")
                            .setCompany("company")));

            Set<Contact> contacts = new HashSet<>();
            contacts.add(contactRepository.save(new Contact.Builder(ContactType.EMAIL, "abcde@abcde.com").conference(conference).build()));
            contacts.add(contactRepository.save(new Contact.Builder(ContactType.FACEBOOK, "http://www.facebook.com").conference(conference).build()));
            contacts.add(contactRepository.save(new Contact.Builder(ContactType.TWITTER, "www.twitter.com").conference(conference).build()));

            Room room1 = roomRepository.save(new Room.Builder("100호", "100", "100호").build());
            Room room2 = roomRepository.save(new Room.Builder("101호", "101", "101호").build());
            Room room3 = roomRepository.save(new Room.Builder("102호", "102", "102호").build());

            // Day 1
            ProgramDate programDate1 = programDateRepository.save(
                    new ProgramDate.Builder("Day 1", "2016-01-01").conference(conference).build());
            programRepository.save(new Program.Builder("참가등록", "참가등록", "08:40", "09:20")
                    .date(programDate1)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.REGISTER).build());

            programRepository.save(new Program.Builder("키노트", "키노트", "09:20", "09:40")
                    .videoUrl("www.google.com")
                    .date(programDate1)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.KEYNOTE).build());

            Program program1 = programRepository.save(new Program.Builder("세션1", "테스트", "10:00", "10:50")
                    .date(programDate1)
                    .speakerSet(userSet)
                    .programType(ProgramType.SESSION).build());

            Program program2 = programRepository.save(new Program.Builder("세션2", "테스트", "11:00", "11:50")
                    .date(programDate1)
                    .speakerSet(userSet)
                    .programType(ProgramType.SESSION).build());

            sessionRepository.save(new Session.Builder("강좌1", "테스트1")
                    .videoUrl("www.google.com")
                    .slideUrl("www.google.com")
                    .program(program1)
                    .room(room2)
                    .speakerSet(userSet).build());

            sessionRepository.save(new Session.Builder("강좌2", "테스트2")
                    .videoUrl("www.google.com")
                    .program(program1)
                    .room(room2)
                    .speakerSet(userSet).build());

            sessionRepository.save(new Session.Builder("강좌3", "테스트3")
                    .slideUrl("www.google.com")
                    .program(program2)
                    .room(room2)
                    .speakerSet(userSet).build());


            sessionRepository.save(new Session.Builder("강좌4", "테스트4")
                    .program(program2)
                    .room(room3)
                    .speakerSet(userSet).build());

            programRepository.save(new Program.Builder("점심시간", "점심시간", "12:00", "12:50")
                    .date(programDate1)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.LUNCH).build());

            // Day 2
            ProgramDate programDate2 = programDateRepository.save(
                    new ProgramDate.Builder("Day 2", "2016-01-02").conference(conference).build());

            programRepository.save(new Program.Builder("참가등록", "참가등록", "08:40", "09:20")
                    .date(programDate2)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.REGISTER).build());

            programRepository.save(new Program.Builder("키노트", "키노트", "09:20", "09:40")
                    .date(programDate2)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.KEYNOTE).build());

            Program program3 = programRepository.save(new Program.Builder("세션1", "테스트", "10:00", "10:50")
                    .date(programDate2)
                    .speakerSet(userSet)
                    .programType(ProgramType.SESSION).build());

            Program program4 = programRepository.save(new Program.Builder("세션2", "테스트", "11:00", "11:50")
                    .date(programDate2)
                    .speakerSet(userSet)
                    .programType(ProgramType.SESSION).build());

            sessionRepository.save(new Session.Builder("강좌1", "테스트1")
                    .program(program3)
                    .room(room2)
                    .speakerSet(userSet).build());

            sessionRepository.save(new Session.Builder("강좌2", "테스트2")
                    .program(program4)
                    .room(room2)
                    .speakerSet(userSet).build());

            sessionRepository.save(new Session.Builder("강좌3", "테스트3")
                    .program(program3)
                    .room(room2)
                    .speakerSet(userSet).build());

            sessionRepository.save(new Session.Builder("강좌4", "테스트4")
                    .program(program4)
                    .room(room3)
                    .speakerSet(userSet).build());

            programRepository.save(new Program.Builder("점심시간", "점심시간", "12:00", "12:50")
                    .date(programDate2)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.LUNCH).build());
        };
    }
}
