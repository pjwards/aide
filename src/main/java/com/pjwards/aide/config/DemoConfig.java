package com.pjwards.aide.config;

import com.pjwards.aide.domain.*;
import com.pjwards.aide.domain.enums.ProgramType;
import com.pjwards.aide.repository.*;
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
                                            UserRepository userRepository,
                                            RoomRepository roomRepository,
                                            ProgramDateRepository programDateRepository,
                                            ProgramRepository programRepository) {
        return (args) -> {
            Conference conference = conferenceRepository.save(new Conference.Builder("DEVIEW 2015", "DEVIEW 2015가 성황리에 끝났습니다.",
                    "<h2>excellence . sharing . growth</h2>\n" +
                            "\n" +
                            "다양한 분야에서 탄탄한 실력을 갖춘 국내외 IT기업 엔지니어들의 \n" +
                            "실전경험과 노하우가 담긴 컨텐츠를 공유합니다.\n" +
                            "\n").location("COEX Grand Ballroom, SEOUL").locationUrl("https://www.google.co.kr/maps/place/%EC%BD%94%EC%97%91%EC%8A%A4+%EC%BB%A8%EB%B2%A4%EC%85%98%EC%84%BC%ED%84%B0+%EA%B7%B8%EB%9E%9C%EB%93%9C%EB%B3%BC%EB%A3%B8/@37.5081321,127.0336615,14z/data=!4m5!1m2!2m1!1scoex!3m1!1s0x357ca46bcb9e129f:0xd6bf8dde518b69a4")
                    .latlan(37.513204, 127.058638).build());

            Set<User> userSet = new HashSet<>();
            userSet.add(userRepository.save(new User.Builder("홍길동", "abcde@abcde.com", "abcdefg").build()));
            ProgramDate programDate = programDateRepository.save(
                    new ProgramDate.Builder("Day 1", "2016-01-01").conference(conference).build());
            Room room1 = roomRepository.save(new Room.Builder("100호", "100", "100호").conference(conference).build());
            Room room2 = roomRepository.save(new Room.Builder("101호", "101", "101호").conference(conference).build());
            Room room3 = roomRepository.save(new Room.Builder("102호", "102", "102호").conference(conference).build());

            programRepository.save(new Program.Builder("참가등록", "참가등록", "08:40", "09:20")
                    .conference(conference)
                    .date(programDate)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.REGISTER).build());

            programRepository.save(new Program.Builder("키노트", "키노트", "09:20", "09:40")
                    .conference(conference)
                    .date(programDate)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.KEYNOTE).build());

            programRepository.save(new Program.Builder("강좌1", "테스트1", "10:00", "10:50")
                    .conference(conference)
                    .date(programDate)
                    .room(room2)
                    .speakerSet(userSet)
                    .programType(ProgramType.SESSION).build());

            programRepository.save(new Program.Builder("강좌2", "테스트2", "10:00", "10:50")
                    .conference(conference)
                    .date(programDate)
                    .room(room3)
                    .speakerSet(userSet)
                    .programType(ProgramType.SESSION).build());

            programRepository.save(new Program.Builder("강좌3", "테스트3", "11:00", "11:50")
                    .conference(conference)
                    .date(programDate)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.SESSION).build());

            programRepository.save(new Program.Builder("강좌4", "테스트4", "11:00", "11:50")
                    .conference(conference)
                    .date(programDate)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.SESSION).build());

            programRepository.save(new Program.Builder("점심시간", "점심시간", "12:00", "12:50")
                    .conference(conference)
                    .date(programDate)
                    .room(room1)
                    .speakerSet(userSet)
                    .programType(ProgramType.LUNCH).build());
        };
    }
}
