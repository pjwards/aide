package com.pjwards.aide.config;

import com.pjwards.aide.domain.*;
import com.pjwards.aide.domain.enums.*;
import com.pjwards.aide.domain.forms.SignUpForm;
import com.pjwards.aide.repository.*;
import com.pjwards.aide.service.assets.AssetsService;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.conferencerole.ConferenceRoleService;
import com.pjwards.aide.service.contact.ContactService;
import com.pjwards.aide.service.message.MessageService;
import com.pjwards.aide.service.presence.PresenceService;
import com.pjwards.aide.service.program.ProgramService;
import com.pjwards.aide.service.programdate.ProgramDateService;
import com.pjwards.aide.service.room.RoomService;
import com.pjwards.aide.service.session.SessionService;
import com.pjwards.aide.service.sponsor.SponsorService;
import com.pjwards.aide.service.user.UserService;
import com.pjwards.aide.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.util.*;

@Configuration
@ComponentScan("com.pjwards.aide")
@PropertySource("classpath:file.properties")

public class DemoConfig {

    @Value("${file.sample.path}")
    private String filePath;

    @Bean
    public CommandLineRunner conferenceDemo(ConferenceService conferenceService,
                                            RoomService roomService,
                                            ProgramDateService programDateService,
                                            ProgramService programService,
                                            SessionService sessionService,
                                            ContactService contactService,
                                            MessageService messageService,
                                            AssetsService assetsService,
                                            SponsorService sponsorService,
                                            PresenceService presenceService,
                                            ConferenceRoleService conferenceRoleService,
                                            UserService userService,
                                            Utils utils) {
        return (args) -> {

            // PyCon 2015
            SignUpForm signUpForm = new SignUpForm();
            Random random = new Random();

            List<User> users = new ArrayList<>();
            Map<String, User> userMap = new HashMap<>();

            signUpForm.setName("ADMIN");
            signUpForm.setEmail("admin@pjwards.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            User host = userService.update(
                    userService.create(signUpForm)
                            .setDescription("Admin User")
                            .setCompany("Company")
                            .setRole(Role.ADMIN));

            signUpForm.setName("이안 루이스");
            signUpForm.setEmail("ian0932@google.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("Ian is a Developer Advocate on the Google Cloud Platform team working out of Tokyo. Ian loves Python and Go and helps run the two largest respective events in Japan, PyCon JP and GoCon. Ian is also interested in Docker and Kubernetes and hopes to help Google Cloud Platform users achieve their highest potential.")
                            .setCompany("Google")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("강대성");
            signUpForm.setEmail("dasung@google.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("록앤올(국민내비 김기사)에서 Python을 활용하여 교통정보 처리와 분석을 하고 있습니다.")
                            .setCompany("(주)록엔올")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("김명신");
            signUpForm.setEmail("myungkim.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("마이크로소프트 수석 에반젤리스트.개발자 대상의 다양한 컨퍼런스에 단골 발표자로 참가하고 있어서 쉽사리 만나볼 수 있는 쉬운 남자다. 최근에는 폴리글랏의 유행에 편승하여 이런저런 언어들을 다시 공부하고 있다. 내일부터 운동해야지 라는 말을 10년째 반복하고 있으며, 최근에는 30년간 공부한 걸 어디다가 써먹을 수 있을지 다시금 고민 중이다.")
                            .setCompany("Microsoft")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("김재석");
            signUpForm.setEmail("jcmung09@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("(주)스포카의 공동창업자이며 CTO로 일하고 있습니다. 태블릿기반 매장 멤버십 서비스인 도도 포인트를 만들고 있습니다. 2008년부터 파이썬을 적극적으로 쓰기 시작했고, 스포카 역시 창업시점부터 지금까지 파이썬을 주력 언어로 사용하고 있습니다.")
                            .setCompany("(주)스포카")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("배권한");
            signUpForm.setEmail("darjeelin@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("파이콘 한국을 준비하는 배권한입니다. 라인주식회사에서 DevOPS TF 일을 하고 있습니다.")
                            .setCompany("")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("배준현");
            signUpForm.setEmail("decunt@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("파이썬 초보")
                            .setCompany("")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("신정규");
            signUpForm.setEmail("inurets@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("블로그 플랫폼인 텍스트큐브의 프로젝트 메인테이너이자 오픈소스 커뮤니티인 TNF의 리더 겸 삽질러입니다. 복잡계 물리학과 데이터 기반의 뇌과학을 전공한 물리학자인데, 남들 눈에는 컴퓨터공학 전공으로 자주 오해받는 삶을 살아 왔습니다. 핏이 맞는 일을 찾다가 관심사에 적당한 일이 보이지 않아 래블업이라는 스타트업 형태로 일을 직접 함께 만들고 있습니다. 몇몇 회사들과 특이하고 재미있는 일들을 한 경험이 있습니다. 주된 관심사는 마음에 들지 않는 구조를 바닥부터 바꾸는 일입니다. ")
                            .setCompany("(주)니들웍스")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("오영택");
            signUpForm.setEmail("yindong@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("카이스트 전산 동아리 SPARCS로 활동하면서 전산 분야에 입문하였습니다. 현재 데브시스터즈에서 서버개발팀으로, 모바일 게임 쿠키런의 라이브 서비스 및 서버 인프라를 맡고 있습니다.")
                            .setCompany("데브시스터즈")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("이창욱");
            signUpForm.setEmail("clee@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("컴퓨터 과학을 전공하고 시스템 관리자로 시작해서 스타트업 창업을 거쳐 어느새 잡케가 되어버린 개발자입니다. 지금은 파이썬으로 정착하고 스타트업에서 개발을 하고 있습니다.")
                            .setCompany("")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("이희승");
            signUpForm.setEmail("trustin@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("이희승은 자바 가상 머신 기반의 대표적 네트워크 어플리케이션 프레임워크인 네티 프로젝트와아파치 미나 프로젝트를 창시한 소프트웨어 엔지니어입니다. 연세대학교 1학년에 재학중이던 1999년 말, (주)아레오 커뮤니케이션즈 (現 (주)스탠다드 네트웍스)에서 자바 프로그래밍 언어를 이용한 5개 이동통신사와의 단문 메시지 전송을 국내 최초로 상용화한 이래, 분산화된 대용량 단문 메시지 전송 게이트웨이, RPC 서버와 같은 고성능 네트워크 어플리케이션을 꾸준히 개발해 왔습니다. 아레오 커뮤니케이션즈, 첫눈, non, 레드햇, 트위터를 거쳐 현재는 라인플러스에서 근무하며 곧 공개될 비동기 RPC 라이브러리와 분산 서비스 설정/상태 저장소를 팀원들과 함께 개발하고 있습니다. 일상과 업무, 가족과 나 사이에 끝없이 번뇌하며 가족의 소중함을 절감하는 한 사람의 인간입니다. ")
                            .setCompany("라인플러스")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("김경훈");
            signUpForm.setEmail("kyunghoon@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("UNIST 자연과학부 수리과학과에 재학 중인 석박통합과정 학생.<br>자연을 모방한 알고리즘에 관심이 많으며, 현재는 네트워크적 사고(Network Thinking)를 하기 위해 노력하고 있다.")
                            .setCompany("")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("김기환");
            signUpForm.setEmail("wbkifun@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("김기환 (물리학 전공, (재)한국형수치예보모델개발사업단 근무)")
                            .setCompany("한국형수치예보모델개발사업단")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("김도형");
            signUpForm.setEmail("dohhyoung@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("증권사 퀀트로 근무하며 파생상품 프라이싱 엔진 개발, 헷지 트레이딩, HFT(High-Frequency Trading) 어플리케이션 개발을 하였으며 현재는 트레이드 인포매틱스라는 스타트업을 세우고 펀드 대량주문의 최적 집행(optimal execution) 및 거래비용분석(TCA: Transaction Cost Analysis)을 위한 플랫폼을 개발하고 있읍니다. ")
                            .setCompany("트레이드 인포매틱스")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("김영근");
            signUpForm.setEmail("scari909@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("김영근(http://younggun.kim)은 애플 BASIC으로 프로그래밍을 처음 시작했고, 그때부터 지금까지 고수들의 등을 쳐다보며 느리게 따라가고 있습니다.<br>저수준의 리눅스 시스템 프로그래밍에서부터 미들웨어, 웹, 앱까지 다양한 분야를 거쳐 현재는 스마트스터디의 개발자 그룹인 DISTRICT9에서 Badass Alien으로 근무 중입니다.<br>몇 권의 IT 전문서를 번역했으며, 2014년부터 파이콘 한국을 만들어 가고 있는 사람 중 한 명입니다. 여가 시간에는 프로글래머(http://proglamour.io)라는 이름으로 잉여력을 발산 중입니다.")
                            .setCompany("DISTRICT9")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("김준기");
            signUpForm.setEmail("achimnol@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("전공은 네트워크 시스템 연구로, GPU 기반 고속 패킷 처리 프레임워크를 개발했습니다. 하지만 오래 전부터 텍스트큐브 등 오픈소스 활동을 통해 항상 더 나은 방식으로 사람들이 공유하고 협업할 수 있는 무언가를 만들고 싶었습니다. 특히 대학원 생활을 하면서 '논문이 과학·공학적 연구 과정과 그 결과물을 공유하는 가장 좋은 방법인가?', '저널과 학회가 학계 내외부 인터랙션에 충분한 도구인가?', '내가 전산학을 배우고 연구해온 과정이 과연 기존의 학교라는 틀과 어떻게 맞거나 맞지 않을까?' 라는 물음을 가지게 되었고, 이에 대한 답을 찾아보고자 래블업이라는 스타트업에 합류하였습니다.<br> 공학적으로는 자바스크립트 프론트엔드부터 커널 드라이버 수준의 백엔드를 모두 아우르며 시스템 성능을 높이고 적절한 추상화를 통해 확장성과 재사용성을 높이는 일에 관심이 많습니다.")
                            .setCompany("래블업")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("김현호");
            signUpForm.setEmail("hyunho@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("UST에 재학중이고, ETRI 자동통역연구실에서 자연어처리 관련 연구를 하고 있습니다.그리고 팀포퐁의 \"대한민국 정치의 모든 것(http://pokr.kr)\" android app을 준비 중에 있습니다.<br>기계학습과 자연어처리에 관심을 가지고 있으며,최근, 딥러닝을 자연어처리에 적용하려는 딥러닝 응용분야에 대해 연구를 하고 있습니다.딥러닝에 대한 이론내용과 실험을 통해 얻은 경험을 공유하고자 합니다.")
                            .setCompany("팀포퐁")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("김형관");
            signUpForm.setEmail("hiraikaen@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("LG Innotek에서 모바일 카메라를 개발하고 있습니다.")
                            .setCompany("LG Innotek")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("박은정");
            signUpForm.setEmail("lucypark@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("박은정은 서울대학교에서 데이터마이닝을 전공하고 있으며, 마케팅, 반도체, 영화, 정치 등의 영역에서 데이터 분석 프로젝트를 진행했다.<br>기술을 이용해 지식의 장벽을 낮추는 것에 관심이 있어, 여가 시간에는 팀포퐁에서 \"대한민국 정치의 모든 것(http://pokr.kr)\"을 만들며 입법 정보의 확산에 노력을 기울이고 있다.")
                            .setCompany("팀포퐁")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("박중석");
            signUpForm.setEmail("jspark@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("한국마이크로소프트 기술에반젤리스트. 국내 개발자들에게 필요한 기술의 가치를 알리고, 잘 적용토록 하는 일을 맡고 있습니다.")
                            .setCompany("Microsoft")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("박현우");
            signUpForm.setEmail("lqez1954@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("베이직으로 프로그래밍을 시작하여 하이텔 게임 제작 동호회(GMA)에서 취미로 게임을 만들다, 동호회원의 권유로 게임 업계로 들어가 회사에서의 프로그래밍이 세상의 전부인양 10년간 우물안 개구리로 살았습니다. 이후, 스마트스터디를 공동 창업하고, 파이썬을 본격적으로 사용하게 되며 더 넓은 세상에 눈을 뜨게 되었습니다")
                            .setCompany("(주)스마트스터디")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("서상현");
            signUpForm.setEmail("sanxiyn@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("프로그래밍 언어의 설계와 구현에 관심이 많습니다. PyPy와 IronPython 개발에 참여했습니다. 삼성전자 소프트웨어센터에서 Rust 개발했고 지금은 네이버랩에서 자바스크립트 엔진 관련 개발을 하고 있습니다")
                            .setCompany("NaverLabs")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("안병욱");
            signUpForm.setEmail("erichoon@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("SKPlanet 안 병욱매니저입니다. Platform Architecture팀에서 technical architecture 역할을 했었고, 지금은 SKPlanet 개발자센터 리뉴얼 작업에 참여하고 있습니다. Devops 에 관심이 많고, AWS등의 클라우드 플랫폼과 Docker등을 꾸준히 공부하고 있습니다.  ")
                            .setCompany("SKPlanet")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("유재명");
            signUpForm.setEmail("aichupanda@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("기계가 모든 걸 다해주면 사람은 놀고 먹는 세상을 꿈꾸는 인지과학자. 2011년 데이터 분석과 교육을 전문으로 하는 (주)퀀트랩을 설립하여 현대자동차, LG U+, SK 플래닛, NC소프트 등 기업들의 데이터를 분석하고 교육하는 작업을 해왔다. ")
                            .setCompany("(주)퀀트랩")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("이호성");
            signUpForm.setEmail("hosung@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("이호성입니다. Enswers 에서 DevOps팀을 맡고 있습니다. 80% 이상의 코드를 파이썬으로 짜고 있습니다. 사내 개발자들을 위한 제품들을 주로 만들고 있으며, 개발 조직의 효율성을 높이는 것에 많은 관심이 있습니다.<br>반갑습니다!")
                            .setCompany("Enswers")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("이흥섭");
            signUpForm.setEmail("sublee@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("게임개발자 이흥섭입니다. 넥슨 코리아 왓 스튜디오에서 <야생의 땅: 듀랑고>를 개발하고 있습니다. TrueSkill, Hangulize 등 오픈소스 활동도 하고 있습니다.")
                            .setCompany("Nexon")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("임덕규");
            signUpForm.setEmail("hong18s@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("파이썬에 빠져 사는 무늬만 임베디드 개발자인 임덕규 입니다. 시각디자인 전공하고 디자이너로 먹고 살다가 늦은 나이에 프로그래머로 전직하여 고군분투하고 있습니다. 민간기상업체 '케이웨더'라는 곳에서 기상장비 개발과 윈도우 응용프로그램을 파이썬으로 만들어었습니다.")
                            .setCompany("(주)케이웨더")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("장혜식");
            signUpForm.setEmail("perky@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("생명체가 자라나는 과정에 호기심이 많은 계산생물학자. 과학, 배움, 데이터, 소통에 관심이 많다. 최근엔 기계학습으로 리보핵산이라는 생명물질에서 수명줄 같은 역할을 하는 '꼬리'의 길이를 재는 TAIL-seq 기술을 개발했다.")
                            .setCompany("데브시스터즈")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("정민영");
            signUpForm.setEmail("kkung12@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("소프트웨어와 서비스를 통해서 세상을 바꾸는게 꿈인 보통의 개발자. 꿈을 이루기 위해 10여년간 스타트업 계통에 몸담고 있고, 현재도 광고기반의 무료 라디오 서비스 \"비트\"를 만드는 비트패킹컴퍼니에서 CTO로 하루하루 삽을 뜨고 있습니다. <br>요즘은 Web Scale서비스 개발 전반과 Real Time Stream Processing같은 재밌는 부분에 관심을 기울이고 있고, IaaS에도 관심이 많아, AWS 한국사용자 그룹을 운영하고 있습니다.")
                            .setCompany("비트패킹컴퍼니")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("정윤원");
            signUpForm.setEmail("youknowone@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("•공개SW센터 오픈프론티어랩<br>•프로그래밍 입문서 \"깐깐하게 배우는 파이썬\" 번역<br>•OS X/iOS 한글 입력기 \"구름\" http://gureum.io")
                            .setCompany("구름네트웍스")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("최규민");
            signUpForm.setEmail("goforre@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("처음 10여 년 동안의 밥벌이였던 network 분석/보안 분야의 주 종목을 버리고, 불현듯 새로움을 갈망하여 아프리카TV에 들어가서 빅데이터 플랫폼, 실시간데이터 처리, 추천 시스템을 개발을 담당하고 있습니다. <br>요즘은 \"측정할 수 없다면 개선할 수 없다\" 라는 명언을 마음에 새기고 최소한 직접 만드는 기능들은 측정 가능하도록 하여 개선해 나가는 데이터 기반 인재가 되려고 하는 개발자입니다.")
                            .setCompany("아프리카TV")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("하용호");
            signUpForm.setEmail("youngkom@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("하용호라고 합니다. 파이썬 빠돌이입니다. 데이터를 좋아합니다. 대형 클러스터를 좋아합니다. 분산처리 사랑합니다. 병렬처리 애정합니다. 머신러닝, 패턴인식 꿈에 나옵니다. 발표자료들이 많이 알려지는 바람에 말하는 사람같아 보이지만, 6살때부터 코딩을 해온 공대공대한 사람입니다.<br> TmaxSoft와 KTH를 거쳐 SKTelecom에서 근무했습니다. 얼마 전에 뛰쳐나와 NUMBERWORKS 라는 회사를 만들었습니다. 동료 데이터 과학자들이랑 작당해서 만들었습니다. 데이터와 머신러닝에 기반해 고객사의 매출을 올릴 수 있다면 뭐든지 자동화 해버리는 회사입니다. 등따시고 배부르게 살다가 창업해서 세상에 나왔더니 춥습니다. 호오호오.")
                            .setCompany("NUMBERWORKS")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("하재승");
            signUpForm.setEmail("ipknfl@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("오픈소스와 프로그래밍 교육에 관심있는 개발자입니다.현재 넥슨에서 기존 게임들의 유산들을 튼튼하게 다듬고 기술 기반을 다지기 위한 작업을 담당하고 있습니다.<br>C++11이 나오면서 천지개벽이 일어났으나 제대로 쓰는 사람이 적어 슬픈 C++ 매니아입니다.")
                            .setCompany("Nexon")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("로버트 예로우쉑");
            signUpForm.setEmail("robertu@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("Robert is a pragmatic developer focused on getting things done. ")
                            .setCompany("Company")
                            .setRole(Role.SPEAKER)));


            signUpForm.setName("타카유키 시미즈카와");
            signUpForm.setEmail("shimizukawa@gmail.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("•Sphinx co-maintainer since 2011<br>•\"Let's start Sphinx\" Japanese ebook author<br>•Sphinx-users.jp account<br>•PyCon JP organizer")
                            .setCompany("Company")
                            .setRole(Role.SPEAKER)));

            Conference conference = conferenceService.add(new Conference.Builder("PyCon KOREA 2015", "파이콘, 다같이, 성공", "<h1>행사개요</h1><div>파이콘은 세계 각국의 파이썬 프로그래밍 언어 커뮤니티에서 주관하는 비영리 컨퍼런스입니다.</div><div><br></div><div>한국에서는 처음으로 열린 파이콘 한국 2014를 시작으로 파이콘 한국 준비위원회는 건강한 국내 파이썬 생태계에 지속적인 보탬이 되고자, 커뮤니티 멤버들의 자발적인 봉사로 운영되고 있습니다.작년보다 성숙된, 보다 알찬 모습으로 열릴 ‘파이콘 한국 2015’를 통해 새로운 기술과 정보를 공유하고 참석자들이 서로 교류할 수 있는 행사가 되기를 희망합니다.</div><div><br></div><ul><li>일정 : 2015년 8월 29일(토) ~ 30일(일)</li><li>장소 : 상암동 누리꿈스퀘어</li><li>인원 : 약 700명 예상</li><li>주최 : 파이콘 한국 준비 위원회</li><li>대상 : 국내외 파이썬 개발자 혹은 관심이 있는 분이라면 누구나</li></ul>")
                    .host(host)
                    .location("누리꿈 스퀘어")
                    .locationUrl("https://goo.gl/maps/ByS2d")
                    .latlan(37.579726, 126.8902)
                    .status(Status.CLOSED)
                    .charge(Charge.CHARGED)
                    .price(10000)
                    .build()
                    .setParticipants(new HashSet<>(users)));

            for (int i = 1; i < 6; i++) {
                File file = new File(filePath + "pycon/main/jumboback" + i + ".jpg");
                assetsService.add(utils.fileSaveHelper(file, host, "/img/").setConference(conference));
            }

            conferenceRoleService.add(new ConferenceRole().setUser(host).setConference(conference).setConferenceRole(Role.HOST));
            presenceService.add(new Presence().setUser(host).setConference(conference).setPresenceCheck(Check.PRESENCE));

            for (User user : users) {
                userMap.put(user.getName(), user);
                conferenceRoleService.add(new ConferenceRole().setUser(user).setConference(conference).setConferenceRole(Role.SPEAKER));
                presenceService.add(new Presence().setUser(user).setConference(conference).setPresenceCheck(Check.ABSENCE));
                File file = new File(filePath + "pycon/profile/" + user.getName() + ".jpg");
                if (file != null)
                    utils.profileSaveHelper(file, user);
            }

            contactService.add(new Contact.Builder(ContactType.GITHUB, "https://github.com/pythonkr/pyconkr-2015").conference(conference).build());
            contactService.add(new Contact.Builder(ContactType.FACEBOOK, "https://www.facebook.com/pyconkorea").conference(conference).build());
            contactService.add(new Contact.Builder(ContactType.TWITTER, "https://twitter.com/pyconkr").conference(conference).build());

            Set<User> managers = new HashSet<>();
            managers.add(host);

            Room room1 = roomService.add(new Room.Builder("국제회의실", "3층 국제회의실", "").conference(conference).build());
            room1.setManagerSet(managers);
            room1.setParticipants(managers);
            roomService.update(room1);

            Room room2 = roomService.add(new Room.Builder("대회의실", "4층 대회의실", "").conference(conference).build());
            Room room3 = roomService.add(new Room.Builder("중회의실 1", "3층 중회의실 1", "").conference(conference).build());

            Set<Sponsor> sponsors = new HashSet<>();
            sponsors.add(sponsorService.add(new Sponsor.Builder("microsoft", "Microsoft", 1).url("http://www.microsoft.com/ko-kr/default.aspx").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("google", "Google Korea", 2).url("https://developers.google.com").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("devsisters", "DEVSISTERS", 3).url("http://www.devsisters.com/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("spoqa", "Spoqa", 4).url("http://www.spoqa.com/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("beatpacking", "The Bearpacking Company", 5).url("http://beatpacking.com").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("nipa", "정보통신산업진흥원", 6).url("http://nipa.kr/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("opentechnet", "Open Technet", 7).url("http://www.oss.kr/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("psf", "파이썬 소프트웨어 재단", 8).url("https://www.python.org/psf/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("smartstudy", "스마트스터디", 9).url("http://www.smartstudy.co.kr/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("skplanet", "SK planet", 10).url("http://readme.skplanet.com").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("yogiyo", "요기요", 11).url("https://www.yogiyo.co.kr/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("ifunfactory", "아이펀팩토리", 12).url("http://www.ifunfactory.com/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("akamai", "아카마이 코리아", 13).url("http://www.akamai.co.kr").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("line", "라인 주식회사", 14).url("http://linepluscorp.com/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("jetbrains", "JetBrain", 15).url("https://www.jetbrains.com/pycharm").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("cdnetworks", "씨디네트웍스", 16).url("https://www.pycon.kr/2015/about/sponsor/cdnetworks").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("hanbit", "한빛미디어", 17).url("http://www.hanbit.co.kr/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("insight", "도서출판 인사이트", 18).url("http://insightbook.co.kr/").conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("olc", "OLC(OpenLearningCommunity)", 19).url("http://olc.oss.kr/main.jsp").conferences(conference).build()));

            for (Sponsor sponsor : sponsors) {
                File file = new File(filePath + "pycon/sponsor/" + sponsor.getSlug() + ".png");
                sponsorService.update(sponsor.setAssets(utils.fileSaveHelper(file, sponsor, "/img/")));
            }

            messageService.add(new Message.Builder("Test message 1", host, room1).build());
            messageService.add(new Message.Builder("Test message 2", host, room1).build());
            messageService.add(new Message.Builder("Test message 3", host, room1).build());
            messageService.add(new Message.Builder("Test message 4", host, room1).build());

            // Day 1
            ProgramDate programDate1 = programDateService.add(
                    new ProgramDate.Builder("2015-08-29 (토요일)", "2015-08-29").conference(conference).build());

            List<Program> programs = new ArrayList<>();
            Set<User> speakers = new HashSet<>();

            programs.add(programService.add(new Program.Builder("참가등록", "참가등록", "09:00", "09:45")
                    .date(programDate1)
                    .room(room1)
                    .programType(ProgramType.REGISTER).build()));

            speakers = new HashSet<>();
            speakers.add(userMap.get("배권한"));
            programs.add(programService.add(new Program.Builder("개회사", "개회사", "09:45", "10:00")
                    .date(programDate1)
                    .room(room1)
                    .speakerSet(speakers)
                    .programType(ProgramType.REGISTER).build()));

            speakers = new HashSet<>();
            speakers.add(userMap.get("서상현"));
            programs.add(programService.add(new Program.Builder("파이썬: 내다보기", "프로그래밍 언어는 프로그래머와 컴퓨터 사이의 사용자 인터페이스로 기능합니다. 사용자 인터페이스로서의 파이썬 프로그래밍 언어의 미래를 고민해 봅니다.", "10:00", "11:00")
                    .date(programDate1)
                    .room(room1)
                    .speakerSet(speakers)
                    .programType(ProgramType.KEYNOTE).build()));

            programs.add(programService.add(new Program.Builder("프로그램 1 (11:00-12:00)", "국제 회의실 : python 테스트 시작하기 <br>대회의실 : 연구자 및 교육자들을 위한 파이썬 기반의 계산 및 분석 플랫폼 설계  <br>중회의실1 : Docker를 이용하여 Python 개발 환경을 빠르게 구성하고, 백앤드 서비스를 탐색하는 기술, 실행 환경   ", "11:00", "12:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            speakers = new HashSet<>();
            speakers.add(userMap.get("김영근"));
            programs.add(programService.add(new Program.Builder("writing fast code", "빠르게 동작하는 코드를 작성하기 위한 원리를 알아봅니다.처리하는 일의 양을 줄여서 속도를 개선하는 방법(코드 최적화)과 같은 시간에 처리하는 일의 양을 늘려서 속도를 개선하는 방법(병렬화) 중 전자에 대해서 살펴봅니다. 컴퓨터와 파이썬의 내부 동작 원리를 가볍게 살펴본 후, dis 모듈과 각종 프로파일러(cProfile, line_profiler, profiling)를 이용하여 코드의 병목 지점을 찾아 이를 개선하는 과정을 알아봅니다.", "12:00", "13:00")
                    .date(programDate1)
                    .room(room1)
                    .speakerSet(speakers)
                    .slideUrl("http://www.slideshare.net/scarinet/writing-fast-code-kr")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/ekckPlDLf4Se0k\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/scarinet/writing-fast-code-kr\" title=\"Writing Fast Code (KR)\" target=\"_blank\">Writing Fast Code (KR)</a> </strong> from <strong><a href=\"//www.slideshare.net/scarinet\" target=\"_blank\">Younggun Kim</a></strong> </div>>")
                    .programType(ProgramType.KEYNOTE).build()));

            programs.add(programService.add(new Program.Builder("프로그램 2 (13:00-14:00)", "대회의실 : 추천 시스템이 word2vec를 만났을때  <br>중회의실1 : NetworkX를 이용한 네트워크 링크 예측 ", "13:00", "14:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 3 (14:00-15:00)", "국제 회의실 : 파이썬 메모리 이모저모 <br>대회의실 : Python on Azure <br> ", "14:00", "15:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 4 (15:00-16:00)", "국제 회의실 : 파이썬 기반의 대규모 알고리즘 트레이딩 시스템 소개 <br>대회의실 : Sphinx autodoc: automated API documentation <br>중회의실1 : Simulation on Optical Image Stabilizer using Python", "15:00", "16:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 5 (16:00-17:00)", "국제 회의실 : Character Encoding in Python <br>대회의실 : 유연한 모바일 게임 운영을 위한 Git 기반 패치 시스템 ", "16:00", "17:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 6 (17:00-18:00)", "국제 회의실 : 한국어와 NLTK, Gensim의 만남  <br>대회의실 : Profiling - 실시간 대화식 프로파일러  <br>중회의실1 : 업무에서 빠르게 만들어 사용하는 PyQt 프로그래밍 ", "17:00", "18:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("라이트닝 토크", "1.배권한 -  프로그래머가 이사하는 법<br>2.배준현 - 서버 개발자가 되기 전에 알았으면 좋았을 것들<br>3.김재석 - 한국에서 소프트웨어 카펜트리 1년<br>4.강대성 - JWT(Json Web Token) 간단한 소개 ", "18:00", "19:00")
                    .date(programDate1)
                    .room(room1)
                    .programType(ProgramType.KEYNOTE).build()));

            speakers = new HashSet<>();
            speakers.add(userMap.get("이호성"));
            sessionService.add(new Session.Builder("Python 테스트 시작하기", "개발자들은 항상 테스트를 작성합니다. (그렇지 않다면 해야 합니다.) 하지만 좋은 테스트를 작성하는 것 뿐 아니라 프로젝트의 완료까지 테스트를 지속하는 것도 쉽지 않은 일입니다. 다음 내용들을 소개해 테스트에 익숙하지 않은 초보 개발자분들께 가이드라인을 제시하고자 합니다.<br>•테스트가 왜 필요할까요? 어떤 테스트가 필요할까요?<br>•좋은 테스트란 무엇인가요?<br>•unittest 소개 및 활용<br>•테스트 관련 툴 소개<br>•제가 하고 있는 테스팅 과정 소개<br>위 내용들을 신입 후배와 페어 프로그래밍을 하는 기분으로 소개하려 합니다")
                    .slideUrl("http://www.slideshare.net/hosunglee948/python-52222334")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/6yCtMhArSrM3ed\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/hosunglee948/python-52222334\" title=\"Python 테스트 시작하기\" target=\"_blank\">Python 테스트 시작하기</a> </strong> from <strong><a href=\"//www.slideshare.net/hosunglee948\" target=\"_blank\">Hosung Lee</a></strong> </div>>")
                    .program(programs.get(3))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("김준기"));
            speakers.add(userMap.get("신정규"));
            sessionService.add(new Session.Builder("연구자 및 교육자들을 위한 파이썬 기반의 계산 및 분석 플랫폼 설계", "현대 과학 연구에는 컴퓨터를 이용한 계산 및 분석 작업이 필수적입니다. 그러나 거대 스케일의 계산 및 분석 작업을 수행할 경우 컴퓨팅 리소스의 적절한 관리 및 확장 용이성을 확보하는 것은 많은 리소스를 필요로 합니다. 우리는 컴퓨터 계산 작업 및 분석 작업을 표준화하고 클라우드에서 처리하는 파이썬3 기반의 오픈소스 플랫폼을 설계 및 개발하고 있습니다. 또한 이 플랫폼 위에서 돌아가는 교육 / 연구 플랫폼을 함께 설계하고 있습니다. <br>저희는 지난 2개월동안 이 과정을 통해 우리가 배운 것들을 공유하고자 합니다. 구체적으로는 개발 중인 플랫폼 구조, 설계 과정의 경험 및 python 3 기반의 플랫폼 개발시 주의할 점들에 대해 이야기하고, 그와 함께 이런 지뢰밭을 걷게 만든 '원동력' 에 대해 함께 이야기할 수 있는 자리가 되었으면 합니다")
                    .slideUrl("http://www.slideshare.net/inureyes/pycon-kr-2015")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/7UAryzib6cA0o\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/inureyes/pycon-kr-2015\" title=\"연구자 및 교육자를 위한 계산 및 분석 플랫폼 설계 - PyCon KR 2015\" target=\"_blank\">연구자 및 교육자를 위한 계산 및 분석 플랫폼 설계 - PyCon KR 2015</a> </strong> from <strong><a href=\"//www.slideshare.net/inureyes\" target=\"_blank\">Jeongkyu Shin</a></strong> </div>")
                    .program(programs.get(3))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("안병욱"));
            sessionService.add(new Session.Builder("Docker를 이용하여 Python 개발 환경을 빠르게 구성하고, 백앤드 서비스를 탐색하는 기술, 실행 환경", "최근에는 Docker와 같이 가상화 환경으로 개발 환경을 많이 사용하고 있다. Docker와 Vagrant같은 컨테이너 기술을 이용하기도 하지만, Google AppEngine, AWS Cloud등의 Cloud 서비스에서 개발 환경을 구성하여 사용하고 있다. 이 세션에서는 Django와  Docker를 사용한 샘플 프로젝트를 소개하려고 한다. 샘플 프로젝트의 아키텍처와 사용한 기술들도 설명한다. REST API의 성능을 테스트 하는 방법을 보여주고, 끝으로 Docker는 개발 환경을 구성하는데 가장 빠르고, 쉬운 방법임을 공유한다.")
                    .slideUrl("http://www.slideshare.net/EricAhn/py-conkr-20150829dockerpython")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/1MLgmzD9hbOUgs\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/EricAhn/py-conkr-20150829dockerpython\" title=\"Py conkr 20150829_docker-python\" target=\"_blank\">Py conkr 20150829_docker-python</a> </strong> from <strong><a href=\"//www.slideshare.net/EricAhn\" target=\"_blank\">Eric Ahn</a></strong> </div>")
                    .program(programs.get(3))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("최규민"));
            sessionService.add(new Session.Builder("추천시스템이 word2vec을 만났을때 ", " 제가 추천 시스템을 알아 가면서 많이 공감을 하는 말이 있습니다. <br> 'Reasonable Recommendation은 만들기는 어렵지 않으나 Great Recommendation으로 개선하는것을 정말  어렵다. 이  Reasonable과 Great의 차이는 비지니스으로  엄청나다.' Netflix Prize가 끝난 직후 Netflix의 엔지니어가 한 말인데 계속 맘에 맴돌고 있습니다. <br> 그럼 여기서 Great한 추천 시스템에 대하여 이야기를 하느냐? 아닙니다. !!!   저도 Great한 추천시스템이 무엇인지 모릅니다. <br> 하지만 Reasonable한 추천 시스템(Collaboration Filtering)부터 Word2vec을 이용한 색다른 추천 시스템이 구현되는 과정을 살펴 봄으로써, Great한 추천시스템을 들기 위해 함께 고민하는 자리가 되었으면 합니다.<br>주요 내용은 다음과 같습니다.<br>  •협업 필터링 기법으로 영화 별점 예측하기<br>•흥미 진진했던 Netflix Prize 이야기 <br>•Word2vec에 대하여 알아 보자 <br>•Word2vec으로 영화 추천 시스템 만들어 보기<br>•Netflix처럼 Word2vec으로 micro-genre를 만들기<br>")
                    .slideUrl("http://www.slideshare.net/ssuser2fe594/2015-py-con-word2vec")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/DwL2lp2nMx8R3Q\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/ssuser2fe594/2015-py-con-word2vec\" title=\"2015 py con word2vec이 추천시스템을 만났을때 \" target=\"_blank\">2015 py con word2vec이 추천시스템을 만났을때 </a> </strong> from <strong><a href=\"//www.slideshare.net/ssuser2fe594\" target=\"_blank\">choi kyumin</a></strong> </div>")
                    .program(programs.get(5))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("김경훈"));
            sessionService.add(new Session.Builder("NetworkX를 이용한 네트워크 링크 예측 ", " 이번 발표는 NetworkX의 기초적인 내용을 알고 계신 것을 가정하고 진행하는 네트워크 분석 중급편입니다. 지난 발표에서 건의해 주셨던 내용에 대한 피드백을 먼저 말씀드리고, 네트워크의 링크를 예측하기 위해 Numpy, Pandas 등을 이용한 데이터 전처리 과정, 네트워크의 링크를 예측한다는 건 무엇인지, 네트워크를 ipython에서 자유롭게 그리기 위해 d3.js를 사용하는 데모 등을 소개해 드립니다.")
                    .slideUrl("http://www.slideshare.net/koorukuroo/20150829-pycon-networkx")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/agbxXUF4gvxxEh\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/koorukuroo/20150829-pycon-networkx\" title=\"[20150829, PyCon2015] NetworkX를 이용한 네트워크 링크 예측\" target=\"_blank\">[20150829, PyCon2015] NetworkX를 이용한 네트워크 링크 예측</a> </strong> from <strong><a href=\"//www.slideshare.net/koorukuroo\" target=\"_blank\">Kyunghoon Kim</a></strong> </div>")
                    .program(programs.get(5))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("배준현"));
            sessionService.add(new Session.Builder("파이썬 메모리 이모저모 ", "  다른 언어에 비해 메모리에 대해 신경 쓸 부분이 없다시피 한 파이썬이지만, 가끔씩 내부 메모리 처리에 대한 사전 지식이 없어 쉬운 길을 멀리 돌아가거나 예상치 못한 버그를 마주할 때가 있습니다. 이 발표에서는 파이썬이 내부적으로 메모리를 어떻게 관리하고 객체들이 메모리에 실제로 어떻게 저장되는지, GC는 어떻게 동작하는지, 파이썬에 객체와 메모리에 관련된 함수들과 모듈들은 어떤게 있는지, monkey patch 등의 코딩을한층 쉽게 만들어주는 메모리 관련 트릭들은 대표적으로 어떤 것들이 있는지 설명합니다.<br> 파이썬을 이제 막 시작한 초보자들과 어느정도 할 줄 아는 중급자들을 대상으로 합니다. 이미 파이썬을 잘 하시는 고급 사용자분들께는 발표가 지루하고 심심하실 수도 있으니 참고해주세요. C/C++ 프로그래밍을 해 보신 경험이 있으시면 이해가 더 쉬울 수도 있습니다..")
                    .slideUrl("https://speakerdeck.com/devunt/the-hitchhikers-guide-to-the-python-memory")
                    .slideEmbed("<script async class=\"speakerdeck-embed\" data-id=\"d9b05f6150b0490baf1ca3cd2876fbf5\" data-ratio=\"1.33333333333333\" src=\"//speakerdeck.com/assets/embed.js\"></script>")
                    .program(programs.get(6))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("박중석"));
            ;
            sessionService.add(new Session.Builder("Python on Azure", " 이 세션에서는 퍼블릭 클라우드로서의 Azure의 활용도를 알아보고, Python 을 Azure 에서 사용하는 방법을 살펴봅니다. 인프라스트럭처 뿐만 아니라 플랫폼 기반의 클라우드 환경에 Python 과 Django를 이용하여 만든 웹 사이트를 배포하는 방법과 Python으로 활용할 수 있는 Azure 서비스 들에 대해서도 알아 봅니다. ")
                    .slideUrl("http://www.slideshare.net/joongs/python-on-azure")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/1a3L4rkJtTAe5l\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/joongs/python-on-azure\" title=\"파이썬 온 애저(Python On Azure)\" target=\"_blank\">파이썬 온 애저(Python On Azure)</a> </strong> from <strong><a href=\"//www.slideshare.net/joongs\" target=\"_blank\">Joongsuk Park</a></strong> </div>")
                    .program(programs.get(6))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("김도형"));
            sessionService.add(new Session.Builder("파이썬 기반의 대규모 알고리즘 트레이딩 시스템 소개 ", " 본 발표에서는 증권사 업무 자동화 시스템 중의 하나인 최적 집행 알고리즘 매매 시스템(Optimal Execution Algorithmic Trading System)을 소개하고 파이썬을 이용한 구현 경험을 공유합니다.<br>최적 집행 알고리즘 매매란 펀드, 연금과 같은 기관 투자가의 대규모 주문을 특정한 알고리즘에 따라 나누어 실행시키는 매매 방법을 말합니다. 최적 집행 알고리즘은 주어진 주문량을 정해진 시간 내에 체결시키면서 시장충격을 최소화하거나 전체 평균 매매가격이 미리 설정된 벤치마크 가격을 추종하는 것을 목표로 구현됩니다.<br>최적 집행 알고리즘 매매 시스템은 대량의 주식을 대상으로 다수의 기관투자가의 주문을 실시간으로 수행하기 때문에 필연적으로 대규모 실시간 시스템이 됩니다. 본 발표에서는 주로 다양한 스케일의 대규모 데이터 처리와 구현 방법에 관해 이야기 합니다.")
                    .program(programs.get(7))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("타카유키 시미즈카와"));
            sessionService.add(new Session.Builder("Sphinx autodoc: automated API documentation ", "Sphinx can extract paragraphs from sphinx document and store them into gettext format translation catalog files. Gettext format translation catalog is easy to translate from one language to other languages. Also Sphinx support internationalization by using such catalog files. You can use your favorite editors or services to translate your sphinx docs. In this session, I'll explain 3 things; (1) entire process to translate sphinx docs. (2) automation mechanism for the process. (3) tips, tricks and traps for writing docs and translating.")
                    .slideUrl("http://www.slideshare.net/shimizukawa/sphinx-autodoc-automated-api-documentation-pyconkr-2015")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/jpHfvLI4uJFqWB\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/shimizukawa/sphinx-autodoc-automated-api-documentation-pyconkr-2015\" title=\"Sphinx autodoc - automated api documentation - PyCon.KR 2015\" target=\"_blank\">Sphinx autodoc - automated api documentation - PyCon.KR 2015</a> </strong> from <strong><a href=\"//www.slideshare.net/shimizukawa\" target=\"_blank\">Takayuki Shimizukawa</a></strong> </div>")
                    .program(programs.get(7))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("김형관"));
            sessionService.add(new Session.Builder("Simulation on Optical Image Stabilizer using Python ", "본 세션에서는 Python의 control library를 이용하여 모바일 카메라에 탑재되는 광학식 손떨림 보정기능(OIS)을 구현해 봅니다. 손떨림 보정의 동작 원리에 대하여 간단히 설명한 후, 전달 함수의 설계, 딜레이 설정, 게인 및 각종 필터 설계, 피드백 연결을 통하여 시스템을 만듭니다. OIS는 비교적 기초적인 구조의 feedback system이며, 대부분 라이브러리화 되있기에 python으로 쉽게 구현 할 수 있습니다. 완성된 시뮬레이션 기능을 이용한 현업에서의 활용 방안과, 구현에 있어서 시행착오들을 공유하려 합니다.")
                    .program(programs.get(7))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("강대성"));
            sessionService.add(new Session.Builder("Character Encoding in Python ", "이 발표는 Character Encoding 개념을 소개하고, Python을 사용하면서 경험했던 인코딩 사례들을 공유하는 내용으로 진행합니다. 또한 Encoding 문제를 피하기 위한 방법을 제시합니다.")
                    .slideUrl("http://www.slideshare.net/daesung7kang/character-encoding-in-python")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/mePUI90fSaCIY9\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/daesung7kang/character-encoding-in-python\" title=\"Character Encoding in python\" target=\"_blank\">Character Encoding in python</a> </strong> from <strong><a href=\"//www.slideshare.net/daesung7kang\" target=\"_blank\">daesung7kang</a></strong> </div>")
                    .program(programs.get(8))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("오영택"));
            sessionService.add(new Session.Builder("유연한 모바일 게임 운영을 위한 Git 기반 패치 시스템 ", "2년이 넘는 기간 동안 많은 사랑을 받아온 모바일 게임 쿠키런. 그 오랜 기간의 운영 뒤에는 게임 리소스의 관리, 테스트 및 배포 단계에 이르기까지전 과정을 아우르는 유연한 패치 시스템이 중요한 역할을 하였습니다.<br>모바일 게임 패치 시스템에는 중요한 요구사항들이 있습니다.우선, 모바일 기기의 한정적인 자원으로 게임 리소스를 효율적으로 받아야 합니다.또한, 리소스의 관리 및 라이브로의 배포 프로세스가 편리해야 합니다.<br>쿠키런의 게임 패치 시스템은 파이썬으로 작성되었으며, bottle, pygit2 등을 이용하고 있습니다.상기 요구사항들을 만족시키기 위해 현재의 시스템을 어떻게 구성했는지와 함께,시스템 구축 및 실제 서비스 과정에서의 경험을 공유하고자 합니다.")
                    .slideUrl("http://www.slideshare.net/youngtaekoh31/python-git")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/2ONvOa3Ak7r3Tm\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/youngtaekoh31/python-git\" title=\"Python과 Git으로 만드는 모바일 게임 패치 시스템\" target=\"_blank\">Python과 Git으로 만드는 모바일 게임 패치 시스템</a> </strong> from <strong><a href=\"//www.slideshare.net/youngtaekoh31\" target=\"_blank\">Youngtaek Oh</a></strong> </div>")
                    .program(programs.get(8))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("박은정"));
            sessionService.add(new Session.Builder("한국어와 NLTK, Gensim의 만남 ", "KoNLPy(http://konlpy.org)를 쓰면 이제 파이썬으로도 형태소 분석을 하거나 워드클라우드를 그릴 수 있다던데, 이거 말고 또 할 수 있는건 없나요?<br>있습니다!<br> 파이썬의 유명한 자연어 처리 패키지 NLTK(http://nltk.org)를 활용하면 문서 안의 내용을 빠르게 탐색하거나 요약할 수 있고, 토픽 모델링을 지원하는 패키지 Gensim(http://radimrehurek.com/gensim/)을 사용하면 여러 문장이나 문서에 내재되어 있는 규칙, 또는 토픽들을 찾아낼 수 있다.<br> 이 발표에서는 먼저 bag-of-words, document embedding 등 컴퓨터가 문서를 잘 분석할 수 있게 하는 다양한 텍스트의 표현 방법에 대해 살펴본 후, KoNLPy, NLTK, Gensim 등의 라이브러리를 실제 한국어 문서들에 적용해본다.")
                    .slideUrl("http://www.slideshare.net/lucypark/nltk-gensim")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/tK1KvXalJvmugr\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/lucypark/nltk-gensim\" title=\"한국어와 NLTK, Gensim의 만남\" target=\"_blank\">한국어와 NLTK, Gensim의 만남</a> </strong> from <strong><a href=\"//www.slideshare.net/lucypark\" target=\"_blank\">Eunjeong (Lucy) Park</a></strong> </div>")
                    .program(programs.get(9))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("이흥섭"));
            sessionService.add(new Session.Builder("Profiling - 실시간 대화식 프로파일러 ", "<야생의 땅: 듀랑고> 게임서버의 성능을 개선하기 위해 실시간 프로파일러와 대화식 인터페이스를 만들었습니다. 새로운 프로파일러를 만들기 전 검토했던 다른 프로파일러들을 소개하고, 왜, 그리고 어떻게 새로운 프로파일러를 만들게 되었는지, 또 사용법과 향후 개발 계획은 어떻게 되는지 공유합니다.")
                    .slideUrl("http://www.slideshare.net/sublee/profiling-52226374")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/npnx7XojhU8dhl\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/sublee/profiling-52226374\" title=\"Profiling - 실시간 대화식 프로파일러\" target=\"_blank\">Profiling - 실시간 대화식 프로파일러</a> </strong> from <strong><a href=\"//www.slideshare.net/sublee\" target=\"_blank\">Heungsub Lee</a></strong> </div>")
                    .program(programs.get(9))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("임덕규"));
            sessionService.add(new Session.Builder("업무에서 빠르게 만들어 사용하는 PyQt 프로그래밍 ", "PyQt는 C++의 크로스 플랫폼 프레임웍인 Qt를 Python에서 사용할 수 있도록 하는 프로젝트입니다.<br>이런 경우에 PyQt사용을 추천해 드리는 바입니다.<br>•자동화 처리를 위한 간단한 프로그램 작성시<br>•본 제품을 만들기 전, 프로토타입이 필요한 경우<br>•Python을 갓 배운 분들이 GUI 프로그래밍을 배우는 경우<br>발표 내용은 PyQt를 처음 접하시는 분들이 어려움 없이 시작하시기 위한 개념 정리 및 예제등을 준비하였으며, 평소에 이미 사용하고 계신 분들을 위해 발표자인 제가 겪고 고찰하며 해결한 부분을 같이 공유할 수 있는 시간이 될 수 있도록 준비 하였습니다.")
                    .slideUrl("http://www.slideshare.net/ravenkyu/pycon-2015-pyqt")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/AxyhM2XSTn4yZS\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/ravenkyu/pycon-2015-pyqt\" title=\"PyCon 2015 - 업무에서 빠르게 활용하는 PyQt\" target=\"_blank\">PyCon 2015 - 업무에서 빠르게 활용하는 PyQt</a> </strong> from <strong><a href=\"//www.slideshare.net/ravenkyu\" target=\"_blank\">덕규 임</a></strong> </div>")
                    .program(programs.get(9))
                    .room(room3)
                    .speakerSet(speakers).build());

            // Day 2
            ProgramDate programDate2 = programDateService.add(
                    new ProgramDate.Builder("2015-08-30 (일요일)", "2015-08-30").conference(conference).build());
            programs = new ArrayList<>();

            programs.add(programService.add(new Program.Builder("참가등록", "참가등록", "09:00", "09:45")
                    .date(programDate2)
                    .room(room1)
                    .programType(ProgramType.REGISTER).build()));

            speakers = new HashSet<>();
            speakers.add(userMap.get("김영근"));
            programs.add(programService.add(new Program.Builder("개회사", "개회사", "09:45", "10:00")
                    .date(programDate2)
                    .room(room1)
                    .speakerSet(speakers)
                    .programType(ProgramType.REGISTER).build()));

            speakers = new HashSet<>();
            speakers.add(userMap.get("이희승"));
            programs.add(programService.add(new Program.Builder("네티 13년", "네티 프로젝트가 2001년 12월 16일 첫 릴리즈된 이후 무려 13년이 지났습니다. 그간 있었던 여러 일들을 회고하며 오픈 소스 프로젝트가 성장해 나아간다는 것은 무엇인지, 또 그것이 프로젝트 참여자에게 어떤 의미를 갖는지 보여드리고자 합니다.", "10:00", "11:00")
                    .date(programDate2)
                    .room(room1)
                    .speakerSet(speakers)
                    .slideUrl("https://speakerdeck.com/trustin/netty-13nyeon")
                    .slideEmbed("<script async class=\"speakerdeck-embed\" data-id=\"8f1a48f0490f479d93704b039d08bd21\" data-ratio=\"1.33333333333333\" src=\"//speakerdeck.com/assets/embed.js\"></script>")
                    .programType(ProgramType.KEYNOTE).build()));

            programs.add(programService.add(new Program.Builder("프로그램 1 (11:00-12:00)", "국제 회의실 : Celery의 빛과 그림자 <br>대회의실 : Load testing  <br>중회의실1 : Python and test   ", "11:00", "12:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            speakers = new HashSet<>();
            speakers.add(userMap.get("장혜식"));
            programs.add(programService.add(new Program.Builder("탐색적으로 큰 데이터 분석하기: 파이프라인, 병렬화, 압축, 인덱싱 등에 대해 ", "어떤 정보가 숨어있는지 알 수 없는 큰 데이터를 분석할 때는 미리 생각할 수 없는 시나리오로 진행되는 경우가 많다. 이렇게 데이터 처리의 윤곽이 잡혀있지 않을 때엔 빨리 만들어 빨리 쓰고 버리는 일회용 스크립팅과, 어떤 경우에도 쉽게 적응할 수 있는 일반화된 프레임워크가 필요하다. 또한, 결과가 빨리 나오지 않으면 다음 분석 작업이 늦어지기 때문에, 적은 노력으로 속도를 끌어올릴 수 있는 병렬화 환경이 있어야 한다.이 발표에서는 분자생물학에서 많이 사용되는 데이터 분석 도구들을 예로 들어, 빠른 반복주기가 필요한 대규모 탐색적 데이터 분석 방법을 소개한다. 워크플로우 자동화 도구 snakemake 의 기본적인 설계 개념을 알아본 뒤, snakemake를 이용한 병렬화, 여러 텍스트 파일 병렬처리, 인덱싱 도구들의 기본적인 개념과 장단점, Julia로 계산이 많이 필요한 부분 가속하기 등을 예시를 통해 둘러보기로 한다.", "12:00", "13:00")
                    .date(programDate2)
                    .room(room1)
                    .speakerSet(speakers)
                    .slideUrl("http://www.slideshare.net/hyeshik/pycon-korea-2015")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/fdChTLYyKI8Ioc\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/hyeshik/pycon-korea-2015\" title=\"PyCon Korea 2015: 탐색적으로 큰 데이터 분석하기\" target=\"_blank\">PyCon Korea 2015: 탐색적으로 큰 데이터 분석하기</a> </strong> from <strong><a href=\"//www.slideshare.net/hyeshik\" target=\"_blank\">Hyeshik Chang</a></strong> </div>")
                    .programType(ProgramType.KEYNOTE).build()));

            programs.add(programService.add(new Program.Builder("프로그램 2 (13:00-14:00)", "대회의실 : 파이썬을 이용한 새 프로그래밍 언어 '약속'의 개발 <br>중회의실1 : PyPy/RPython으로 20배 빨라지는 JIT 아희 인터프리터 ", "13:00", "14:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 3 (14:00-15:00)", "국제 회의실 :오늘 당장 딥러닝 실험하기<br>대회의실 : 도도와 파이썬: 좋은 선택과 나쁜 선택 <br> ", "14:00", "15:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 4 (15:00-16:00)", "국제 회의실 : Pay-thon: 2015 파이썬 사용자 조사 결과 분석 <br>대회의실 : Python의 계산성능 향상을 위해 Fortran, C, CUDA-C, OpenCL-C 와 연동하기<br>중회의실1 : Introduction to Kivy", "15:00", "16:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 5 (16:00-17:00)", "국제 회의실 :  R vs. Python: 누가, 언제, 왜   <br>대회의실 : Building a Scalable Python gRPC Service using Kubernetes  ", "16:00", "17:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 6 (17:00-18:00)", "국제 회의실 : Python 개발을 위한 최상의 무료 개발 도구 Visual Studio와 Visual Studio Code  <br>대회의실 :Django in Production", "17:00", "18:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("라이트닝 토크", "1.신정규 - PyCon의 철학과 다양한 파이콘 참석 후기.<br>2.오영택 - C++에서 닷넷으로 그리고 자바, 파이썬. 파이썬이 내게 가져다 준 것들.<br>3.이창욱 - 네이버 블로그를 백업해주는 도구인 exitnaver와, exitnaver<br>4.이희승 - 개밥먹기 : 커뮤니티의 개발과 운영 ", "18:00", "19:00")
                    .date(programDate2)
                    .room(room1)
                    .programType(ProgramType.KEYNOTE).build()));

            speakers = new HashSet<>();
            speakers.add(userMap.get("정민영"));
            sessionService.add(new Session.Builder("Celery의 빛과 그림자 ", "서비스를 만들면서 피할 수 없는 주제 중 한가지가 바로 비동기 처리입니다. 무겁고 오래 걸리는 일에 대한 처리뿐 아니라, 주기적으로 수행해야 하는 일까지 대부분 서비스에 반드시 라고 할 만큼 겪게 되는 문제죠. Python을 쓰는 우리에게는 물론 싱싱하고 훌륭한 해법인 Celery가 있습니다. 요구되는 거의 모든 기능을 제공할 뿐만 아니라, 유연하게 설계되어 있고 관리툴 같은 부가 기능까지, 비동기에 관련된 모든 부분을 책임져주죠.<br>하지만 Celery에 이런 빛과 같은 아름다움만 존재하는 것은 아닙니다. 싱싱한 채소를 맛있게 먹기 위해서는 몇 가지 공부가 필요한 것처럼, 때로는 Celery의 의아스러운 점을 잘 다루고, 우리의 서비스에 맞게 이용하기 위해서는 몇 가지 알아야 할 점이 있습니다. 지난 1년여간 최대 1만 건/초의 요청을 Celery로 처리하면서 제가 얻은 경험을 나누고자 합니다.")
                    .slideUrl("http://www.slideshare.net/kkungkkung/celery-52212762")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/m0r0qRrfxlNc8r\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/kkungkkung/celery-52212762\" title=\"Celery의 빛과 그림자\" target=\"_blank\">Celery의 빛과 그림자</a> </strong> from <strong><a href=\"//www.slideshare.net/kkungkkung\" target=\"_blank\">Minyoung Jeong</a></strong> </div>")
                    .program(programs.get(3))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("로버트 예로우쉑"));
            sessionService.add(new Session.Builder("Load testing ", "Load testing using Locust.io will teach you how to start load testing using simple examples and give you the reasons and possible optimizations for your application.")
                    .slideUrl("https://github.com/robertjerovsek/pycon-kr-2015/blob/master/slides.pdf")
                    .program(programs.get(3))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("배권한"));
            sessionService.add(new Session.Builder("Python and test", "파이썬에서 테스트가 필요한 이유, 테스트 방법 그리고 TDD 에 대하여 이야기합니다.")
                    .slideUrl("https://speakerdeck.com/darjeeling/pycon-kr-2015-python-and-test")
                    .slideEmbed("<script async class=\"speakerdeck-embed\" data-id=\"b1afe6af8ed942478e022a82ea971229\" data-ratio=\"1.33333333333333\" src=\"//speakerdeck.com/assets/embed.js\"></script>")
                    .program(programs.get(3))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("하재승"));
            sessionService.add(new Session.Builder("파이썬을 이용한 새 프로그래밍 언어 \"약속\"의 개발 ", "파이썬을 이용한 새 프로그래밍 언어 약속의 개발 <br>요즘 프로그래머가 아닌 사람들을 위한 프로그래밍 교육에 대한 관심이 높아지고 있습니다. 주로 스크래치를 이용한 간단한 퍼즐을 해결하는 것이나 아두이노를 연계하는 등 여러 가지 방식이 제안되고 있습니다.<br>약속 언어는 한글 프로그래밍 언어로, 처음 프로그래밍을 접하는 사람이 큰 어려움 없이 프로그래밍에 적응하고 간단한 도구들을 만들어 쓸 수 있도록 쉽게 디자인된 언어입니다.<br>이 발표에서는 <br>•약속 언어를 소개하고, <br>•디자인 결정에 대해 설명하며, <br>•실제로 파이썬 (및 다른 도구)을 이용하여 어떻게 구현하였는지<br>설명합니다.")
                    .slideUrl("https://docs.google.com/presentation/d/1d3X4r-U-2L2e2UgWLxLgc_CO0Af3RNqMS3qic3t7iAE")
                    .program(programs.get(5))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("정윤원"));
            sessionService.add(new Session.Builder("PyPy/RPython으로 20배 빨라지는 JIT 아희 인터프리터 ", "PyPy/RPython으로 20배 빨라지는 JIT 아희 인터프리터PyPy가 CPython보다 몇 배씩이나 빠르다는 이야기, 써보며 체감하는 분도 많고 소문은 더 많이 나 있습니다. PyPy에서 인터프리터를 구현하기 위해 사용하는 기술을 살펴보고,같은 방법으로 아희 인터프리터를 구현해 보며 그 편리함과 뛰어난 성능을 살펴 봅시다.")
                    .slideUrl("http://www.slideshare.net/YunWonJeong/pypyrpython-20-jit")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/221IHOKNl0I8fI\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/YunWonJeong/pypyrpython-20-jit\" title=\"알파희 - PyPy/RPython으로 20배 빨라지는 아희 JIT 인터프리터\" target=\"_blank\">알파희 - PyPy/RPython으로 20배 빨라지는 아희 JIT 인터프리터</a> </strong> from <strong><a href=\"//www.slideshare.net/YunWonJeong\" target=\"_blank\">YunWon Jeong</a></strong> </div>")
                    .program(programs.get(5))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("김현호"));
            sessionService.add(new Session.Builder("오늘 당장 딥러닝 실험하기 ", "최근 IT업계에서는 딥러닝에 대한 큰 활약이 이슈화 되고 있습니다.이와 같은 트렌드에 따라가고 싶지만 선형대수 등의 수학적 배경지식 습득부터 시작하여, 딥러닝의 원리와 주요 개념들을 이해 후에 실험을 시도하기에는 많은 시간과 노력이 필요합니다.<br> 그러나 기존의 유용한 딥러닝 오픈소스를 활용한다면 어렵지 않게 딥러닝을 맛볼 수 있습니다. 본 발표는 수학적인 설명을 최대한 배제하고, 오픈소스 툴인 theano, pylearn2를 활용한 예제에 대해 설명하려고 합니다. 추가로 필요할 코드들도 소개하려고 합니다.그리고 word2vec 를 활용하여, 자연어 처리에 딥러닝을 적용하는 사례를 다루려고 합니다.<br> 주제가 학문적이고 이론적이기 때문에 발표가 부담되지만, 최대한 개념적으로 설명하여 실험을 쉽게 따라 할 수 있도록 돕고자 합니다.오픈소스 툴들의 문서화가 잘 되어있지만, 저 또한 처음 접했을 때는 어려움이 있었기 때문에 딥러닝을 시작해보려는 분들에게 도움이 될 듯합니다.컴퓨터가 딥러닝하는 동안 틈틈이 이론공부 하시면 좋겠네요.")
                    .slideUrl("http://www.slideshare.net/hyunhogim7/pycon-2015-52714341")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/MXutvsaVy4oM1N\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/hyunhogim7/pycon-2015-52714341\" title=\"[Pycon 2015] 오늘 당장 딥러닝 실험하기 제출용\" target=\"_blank\">[Pycon 2015] 오늘 당장 딥러닝 실험하기 제출용</a> </strong> from <strong><a href=\"//www.slideshare.net/hyunhogim7\" target=\"_blank\">Dennis Kim</a></strong> </div>")
                    .program(programs.get(6))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("김재석"));
            sessionService.add(new Session.Builder("도도와 파이썬: 좋은 선택과 나쁜 선택 ", "도도 포인트는 태블릿 기반 매장 멤버십 서비스로, 태블릿에 전화번호를 입력하는 것만으로 간편히 포인트 적립을 하고 다양한 리워드를 받을 수 있습니다. 현재 2,500여개 매장에서 400만명의 사용자가 도도 포인트를 이용중이며, 최근 누적 적립 횟수가 1,000만건을 돌파하며 빠르게 성장중입니다.<br>도도 포인트는 처음부터 파이썬 기반으로 제작하였습니다. Flask, SQLAlchemy 조합을 기반으로 프로토타입부터 지금까지 개발을 이어가고 있으며 현재 약 20여명의 개발자가 도도 포인트 개발에 참여하고 있습니다.<br>본 발표에서는 3년 이상의 기간동안 도도 포인트를 개발하면서 했던 많은 기술적인 선택들을 회고합니다. 어떤 선택은 적합한 타이밍이었으며, 어떤 선택은 너무 이른 선택이었고, 어떤 선택은 아예 잘못된 결정이었습니다. 특히 저희에게 큰 영향을 끼친 대표적인 선택들을 공유함으로써, 다음과 같은 주제에 관심있는 분들께 좋은 참고가 될 것으로 기대합니다.")
                    .slideUrl("http://www.slideshare.net/shinvee/ss-52215845")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/n51JwzJdfqsHXN\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/shinvee/ss-52215845\" title=\"도도와 파이썬: 좋은 선택과 나쁜 선택\" target=\"_blank\">도도와 파이썬: 좋은 선택과 나쁜 선택</a> </strong> from <strong><a href=\"//www.slideshare.net/shinvee\" target=\"_blank\">Jc Kim</a></strong> </div>")
                    .program(programs.get(6))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("유재명"));
            sessionService.add(new Session.Builder("Pay-thon: 2015 파이썬 사용자 조사 결과 분석", "facebook에서 실시간으로 받은 사용자 조사 결과를 발표합니다. ")
                    .slideUrl("http://nbviewer.jupyter.org/gist/euphoris/5b451790dd1dd7b5f7f1")
                    .program(programs.get(7))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("김기환"));
            ;
            sessionService.add(new Session.Builder("Python의 계산성능 향상을 위해 Fortran, C, CUDA-C, OpenCL-C 와 연동하기 ", "Python은 과학 계산 분야에서도 이미 널리 사용되고 있습니다. numpy와 scipy 기반으로 만들어진 많은 모듈들이 휼륭한 생태계를 이루고 있기 때문입니다. 그러나 극한의 계산 성능을 요구하는 분야(HPC, High Performance Computing)에서는 여전히 C와 Fortran으로만으로 짜여진 코드들이 선호되고 있습니다. 이런 분야에서 Python에 대한 일반적인 견해는 전처리/후처리에는 유용하지만 메인 코드에 적용하기에는 느리다라는 것입니다. <br>이번 발표에서는 HPC 분야에서도 Python의 유용함을 보여줍니다. 계산이 집중된 부분만을 Fortran, C로 구현하여 Python 메인 코드에 접합하면, Python의 장점은 충분히 활용하면서도 계산 성능에 큰 손해는 보지 않을 수 있습니다. 게다가 CUDA-C, OpenCL-C와 연동하면 GPU, MIC와 같은 가속 프로세서들도 비교적 쉽게 활용할 수 있습니다. 이번 발표에서는 간단한 시뮬레이션 코드를 예제로 사용하여 Python 코드로부터 시작하여 Fortran, C, CUDA-C, OpenCL-C 등을 단계적으로 접합해 나가는 것을 보여줄 것입니다.")
                    .slideUrl("http://www.slideshare.net/KiHwanKim7/python-fortran-c-cudac-openclc")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/3qGrFERc7IVebT\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/KiHwanKim7/python-fortran-c-cudac-openclc\" title=\"Python의 계산성능 향상을 위해 Fortran, C, CUDA-C, OpenCL-C 코드들과 연동하기\" target=\"_blank\">Python의 계산성능 향상을 위해 Fortran, C, CUDA-C, OpenCL-C 코드들과 연동하기</a> </strong> from <strong><a href=\"//www.slideshare.net/KiHwanKim7\" target=\"_blank\">Ki-Hwan Kim</a></strong> </div>")
                    .program(programs.get(7))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("이창욱"));
            sessionService.add(new Session.Builder("Introduction to Kivy ", "Kivy는 OpenGL기반으로 만들어진 파이썬 NUI 프레임워크입니다. Kivy는 빠른 프로토타이핑과 크로스 플랫폼 애플리케이션 개발을 가능하게 합니다. <br>본 세션에서는 1.9.x로 버전업 하면서 더욱 쓸만해진 Kivy를 소개하고 간단한 앱을 만들어 보면서 Kivy 애플리케이션의 구성과 kv language 부터 패키징까지 다루어보고자 합니다. ")
                    .slideUrl("http://slides.com/cleett/introduction-to-kivy#/")
                    .slideEmbed("<iframe src=\"//slides.com/cleett/introduction-to-kivy/embed\" width=\"576\" height=\"420\" scrolling=\"no\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>")
                    .program(programs.get(7))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("유재명"));
            sessionService.add(new Session.Builder("R vs. Python: 누가, 언제, 왜 ", "데이터 분석에 쓰이는 대표적인 프로그래밍 언어로 R과 Python이 있습니다. 이 발표에서는 두 언어의 장단점을 비교해보고 Python이 더 나은 선택일 수 있는 조건과 상황을 소개합니다. 또한 Python을 이용한 데이터 분석을 할 때 알아두어야 할 점들을 살펴봅니다.")
                    .program(programs.get(8))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("이안 루이스"));
            sessionService.add(new Session.Builder("Building a Scalable Python gRPC Service using Kubernetes ", "gRPC is a new high performance, open source, general RPC framework that uses the HTTP/2 standard. The gRPC framework allows you to create small scalable services that fit well into a service oriented architecture. Kubernetes is a cluster manager that allows you to create services that can be scaled easily. Service oriented architecture is a key part of how many companies, like Google, operate their infrastructure to provide robust, and highly available services that operate at a consistently high level of performance for end users.")
                    .slideUrl("https://speakerdeck.com/ianlewis/building-a-scalable-python-grpc-service-using-kubernetes-at-pycon-korea")
                    .slideEmbed("<script async class=\"speakerdeck-embed\" data-id=\"a850e1b6ebce42deaf0248edbb669d04\" data-ratio=\"1.77777777777778\" src=\"//speakerdeck.com/assets/embed.js\"></script>")
                    .program(programs.get(8))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("김명신"));
            sessionService.add(new Session.Builder("Python 개발을 위한 최상의 무료 개발 도구 Visual Studio와 Visual Studio Code ", "마이크로소프트의 Visual Studio는 자타가 공헌하는 세계 최고의 개발도구입니다. Python 개발자는 이제 Python Tools for Visual Studio(PTVS)를 설치하여 Visual Studio를 최고의 Python 개발도구로 확장 할 수 있습니다. PTVS는 CPython, IronPython을 가리지 않고, 편집, 브라우징, 인털레센스 기능을 제공하고, Python/C++이 혼재되어 있는 환경이나 Linux, Mac OS 환경에서도 디버깅을 할 수 있습니다.<br>본 세션에서는 PTVS의 설치로부터 고급 디버깅 시나리오까지를 살펴보고, Windows/Linux/Mac OS를 모두 지원하는 Cross Platform 편집도구인 Visual Studio Code에 대해서도 간략하 살펴봅니다.")
                    .program(programs.get(9))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(userMap.get("박현우"));
            sessionService.add(new Session.Builder("Django in Production ", "하나의 거대한 프로젝트로 개발할 것인가?<br>여러 개의 프로젝트와 앱으로 쪼개서 개발할 것인가?<br>서버를 직접 조립해서 IDC에 입고하고, 회선을 계약해서 서비스할 것인가?<br>IaaS / PaaS 등 클라우드 서비스를 이용하여 서비스할 것인가?<br>5년간 Django 기반의 모바일 서비스를 운영하며 겪은 각종 실패 사례를 공유해 봅니다.")
                    .slideUrl("http://www.slideshare.net/lqez/django-in-production")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/ArQAAo7xTT46n0\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/lqez/django-in-production\" title=\"Django in Production\" target=\"_blank\">Django in Production</a> </strong> from <strong><a href=\"//www.slideshare.net/lqez\" target=\"_blank\">Hyun-woo Park</a></strong> </div>")
                    .program(programs.get(9))
                    .room(room2)
                    .speakerSet(speakers).build());

            // Deview 2015

            users = new ArrayList<>();

            signUpForm.setName("윤흥채");
            signUpForm.setEmail("yhc9230@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("Naver Web technology product manager, who developed the cluster file system, NoSQL and the web browser engine..")
                            .setCompany("NAVER LABS")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("김승현");
            signUpForm.setEmail("runover0023@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("I am a backend Java developer. I was in charge of the asynchronous network library, framework and troubleshooting at Naver. I developed Pinpoint in order to automate and improve troubleshooting.")
                            .setCompany("Fancy")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("김현수");
            signUpForm.setEmail("kopoomo@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("I'm working on Webkit/Chromium at Open Source Consultancy Igalia. I participate in accelerated compositing for the WebKit Open Source project, and I am currently trying to make Threaded Compositor into WebKitGTK+’s standard Compositor.")
                            .setCompany("Feelingsunday CTO")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("김태섭");
            signUpForm.setEmail("taesupp22@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("I am working at the SK Planet data service development team, and the author of the book 'Java ORM standard JPA programming.' I’m very interested in domain driven design, and constantly studying and endeavoring for better codes.")
                            .setCompany("SK hynix")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("박원우");
            signUpForm.setEmail("woo5840@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("Started as a founding member of Entry Research Institute (ex Entry Korea) have designed and constructed Entry’s service base.")
                            .setCompany("Entry")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("이미진");
            signUpForm.setEmail("aapleee@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("I am working on host (android base) and device (flash memory) structure analysis.")
                            .setCompany("SK Planet")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("김준수");
            signUpForm.setEmail("xoosoo99@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("I am currently trying to become a developer who makes software that pleases users; currently I am happily growing at Riot Games. ")
                            .setCompany("NAVER")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("송원빈");
            signUpForm.setEmail("cardxx0@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("Full stack engineer, working with Android, iOS and web. Manager of GDG Korea Android and CTO of Feelingsunday.")
                            .setCompany("GurumNetworks")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("신상호");
            signUpForm.setEmail("sangho13@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("I am participating in browser development at NAVER LABS, and I am really interested in open source activities.")
                            .setCompany("loplat")
                            .setRole(Role.SPEAKER)));

            signUpForm.setName("황수빈");
            signUpForm.setEmail("subinhwang85@naver.com");
            signUpForm.setPassword("1234567");
            signUpForm.setPasswordRepeated("1234567");
            users.add(userService.update(
                    userService.create(signUpForm)
                            .setDescription("I’m interested in designing and building programing language. I participated in Rust programming’s language compiler development. ")
                            .setCompany("TreasureData")
                            .setRole(Role.SPEAKER)));

            conference = conferenceService.add(new Conference.Builder("DEVIEW 2015", "DEVIEW 2015가 성황리에 끝났습니다.",
                    "<h2>excellence . sharing . growth</h2><br>" +
                            "<br>" +
                            "다양한 분야에서 탄탄한 실력을 갖춘 국내외 IT기업 엔지니어들의 <br>" +
                            "실전경험과 노하우가 담긴 컨텐츠를 공유합니다.<br>" +
                            "<br>")
                    .location("COEX Grand Ballroom, SEOUL")
                    .locationUrl("https://www.google.co.kr/maps/place/%EC%BD%94%EC%97%91%EC%8A%A4+%EC%BB%A8%EB%B2%A4%EC%85%98%EC%84%BC%ED%84%B0+%EA%B7%B8%EB%9E%9C%EB%93%9C%EB%B3%BC%EB%A3%B8/@37.5081321,127.0336615,14z/data=!4m5!1m2!2m1!1scoex!3m1!1s0x357ca46bcb9e129f:0xd6bf8dde518b69a4")
                    .latlan(37.513204, 127.058638)
                    .status(Status.CLOSED)
                    .host(host)
                    .build()
                    .setParticipants(new HashSet<>(users)));

            for (int i = 1; i < 4; i++) {
                File file = new File(filePath + "deview/main/deview" + i + ".jpg");
                assetsService.add(utils.fileSaveHelper(file, host, "/img/").setConference(conference));
            }

            contactService.add(new Contact.Builder(ContactType.GITHUB, "https://github.com/pythonkr/pyconkr-2015").conference(conference).build());
            contactService.add(new Contact.Builder(ContactType.FACEBOOK, "https://www.facebook.com/pyconkorea").conference(conference).build());
            contactService.add(new Contact.Builder(ContactType.TWITTER, "https://twitter.com/pyconkr").conference(conference).build());

            room1 = roomService.add(new Room.Builder("1호실", "1호실", "").conference(conference).build());
            room1.setManagerSet(managers);
            room1.setParticipants(managers);
            roomService.update(room1);

            room2 = roomService.add(new Room.Builder("2호실", "2호실", "").conference(conference).build());
            room3 = roomService.add(new Room.Builder("3호실", "3호실", "").conference(conference).build());
            Room room4 = roomService.add(new Room.Builder("4호실", "4호실", "").conference(conference).build());

            sponsors = new HashSet<>();
            sponsors.add(sponsorService.add(new Sponsor.Builder("XSinc", "XSinc", 1).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("Atlassian", "Atlassian", 2).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("BBB", "BBB", 3).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("DAQRI", "DAQRI", 4).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("DaumKakao", "DaumKakao", 5).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("elastic", "elastic", 6).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("ERHEREUM", "ERHEREUM", 7).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("FANCY", "FANCY", 8).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("FeelingSunday", "FeelingSunday", 9).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("GAUDI", "GAUDI", 10).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("GOOGLE", "GOOGLE", 11).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("GurumNetworks", "GurumNetworks", 12).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("Hortonworks", "Hortonworks", 13).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("igalia", "igalia", 14).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("LGCNS", "LGCNS", 15).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("LINE", "LINE", 16).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("Loplat", "Loplat", 17).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("Naver", "Naver", 18).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("NaverLabs", "NaverLabs", 19).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("Netflx", "Netflx", 20).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("RIOT", "RIOT", 21).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("SKHynix", "SKHynix", 22).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("SKPlanet", "SKPlanet", 23).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("SeoulUniversity", "SeoulUniversity", 24).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("NFlabs", "NFlabs", 25).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("NumberWorks", "NumberWorks", 26).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("Pikicast", "Pikicast", 27).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("rainbow", "rainbow", 28).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("Realm", "Realm", 29).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("SolidWare", "SolidWare", 30).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("TreasureData", "TreasureData", 31).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("UNIST", "UNIST", 32).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("VUNO", "VUNO", 33).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("ZOYI", "ZOYI", 34).conferences(conference).build()));
            sponsors.add(sponsorService.add(new Sponsor.Builder("ENTRY", "ENTRY", 35).conferences(conference).build()));

            for (Sponsor sponsor : sponsors) {
                File file = new File(filePath + "deview/sponsor/" + sponsor.getSlug() + ".png");
                sponsorService.update(sponsor.setAssets(utils.fileSaveHelper(file, sponsor, "/img/")));
            }

            for (User user : users) {
                conferenceRoleService.add(new ConferenceRole().setUser(user).setConference(conference).setConferenceRole(Role.SPEAKER));
                presenceService.add(new Presence().setUser(user).setConference(conference).setPresenceCheck(Check.ABSENCE));
            }

            messageService.add(new Message.Builder("Test message 1", host, room1).build());
            messageService.add(new Message.Builder("Test message 2", host, room1).build());
            messageService.add(new Message.Builder("Test message 3", host, room1).build());
            messageService.add(new Message.Builder("Test message 4", host, room1).build());

            // Day 1
            programDate1 = programDateService.add(
                    new ProgramDate.Builder("Day 1", "2015-09-14").conference(conference).build());

            programs = new ArrayList<>();

            programs.add(programService.add(new Program.Builder("Registration", "Registration", "08:40", "09:20")
                    .date(programDate1)
                    .room(room1)
                    .programType(ProgramType.REGISTER).build()));

            programs.add(programService.add(new Program.Builder("Keynote · Chang Song NAVER CTO", "Keynote · Chang Song NAVER CTO", "09:20", "09:40")
                    .date(programDate1)
                    .room(room1)
                    .programType(ProgramType.KEYNOTE).build()));

            programs.add(programService.add(new Program.Builder("프로그램 1 (10:00-10:50)", "", "10:00", "10:50")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 2 (11:00-11:50)", "", "11:00", "11:50")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 3 (12:00-12:50)", "", "12:00", "12:50")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("Lunch", "Lunch", "12:50", "14:10")
                    .date(programDate1)
                    .room(room1)
                    .programType(ProgramType.LUNCH).build()));

            programs.add(programService.add(new Program.Builder("프로그램 4 (14:10-15:00)", "", "14:10", "15:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 5 (15:10-16:00)", "", "15:10", "16:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 6 (16:10-17:00)", "", "16:10", "17:00")
                    .date(programDate1)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("BOF", "BOF", "17:15", "18:30")
                    .date(programDate1)
                    .room(room1)
                    .programType(ProgramType.BOF).build()));

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Looking inside Naver FXToon", "We’re going to introduce the architecture and technology used in Naver FXToon. We will go into detail about what we have tried for ‘non-developer’ writers using our authoring tool, along with the definition of FXToon's genre and the effects we provide. We hope it will be helpful for hybrid-developers who are responsible for both planning and developing the project. In this session we will share about the all the effect types and how we have implemented them for the scroll-type FXToons. And also we'll explain about FXToon viewer's page and layer structure.")
                    .slideUrl("http://www.slideshare.net/deview/111-52720751")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/akWyJqPWSGr1UY\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/111-52720751\" title=\"[111] 네이버효과툰어떻게만들어졌나\" target=\"_blank\">[111] 네이버효과툰어떻게만들어졌나</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=35B8A72DD833B489B8A0D3B32D2F617EC61B&outKey=V1299d502e9b3c47bbc4fcd6e857318eed4cffd85be8521631b9acd6e857318eed4cf&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(2))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Actual Swift Programming", "It’s been a year since Apple introduced Swift, and it’s been about two months since they announced Swift 2.0. I will share the important functions of this language, which I experienced over the past year introducing Swift to apps already in existence, creating new apps with Swift, and making extension and watch apps with Swift, along with the changes they may bring to code. This session will not lightly introduce Swift. Rather, I will take the code iOS engineers use every day as examples, such as view, model, cell, view controller, etc to explain good and bad habits. I will also speak about the extent to which you should use the options, along with which functions you should use to create good code. Finally, I will cover monads and their use, and partially explain how functions like exception handling and pattern matching have changed in Swift 2.0.")
                    .slideUrl("http://www.slideshare.net/deview/112-52720795/1")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/qMwTATbo0uUVDd\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/112-52720795\" title=\"[112] 실전 스위프트 프로그래밍\" target=\"_blank\">[112] 실전 스위프트 프로그래밍</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=37D53C61BDF8D729D7574B37063FA7B92941&outKey=V12481938ae0b05a7c293e300a9b7110b6dd1d79dfd85a4fe0a9fe300a9b7110b6dd1&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(2))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Developing Android Libraries: Lessons from Realm", "In this talk I will try to show you what building Realm for Java taught us. You will learn about the the best practices my team and I came up while building Realm. We will discuss API design, CI techniques and performance considerations trying to provide you with the right tools for the job.")
                    .slideUrl("http://www.slideshare.net/deview/113-lessons-from-realm")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/uQTh4FGsFDSkpY\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/113-lessons-from-realm\" title=\"[113] lessons from realm\" target=\"_blank\">[113] lessons from realm</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=C34708B675D09AE7B992021DC9E8493B2099&outKey=V1240d33516b4119e727c0a6dd4ad13278429df6fe794f09d7dab0a6dd4ad13278429&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(2))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("DRC-HUBO: Technical Review", "- Robot Platform: HUBO<br>- Real-time OS & Framework<br>- Control Strategy<br>- DRC Finals")
                    .slideUrl("http://www.slideshare.net/deview/114drc-hubo-technical-review")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/LYX8jjkmsW0RAb\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/114drc-hubo-technical-review\" title=\"[114] DRC hubo technical review\" target=\"_blank\">[114] DRC hubo technical review</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=E1CA0FB7704F72D3E7E83E983EBC0574F90F&outKey=V124714345bd7096a3121a6403e5e5e19177c02832706c1b7b3e1a6403e5e5e19177c&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(2))
                    .room(room4)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Looking inside Naver FXToon", "We’re going to introduce the architecture and technology used in Naver FXToon. We will go into detail about what we have tried for ‘non-developer’ writers using our authoring tool, along with the definition of FXToon's genre and the effects we provide. We hope it will be helpful for hybrid-developers who are responsible for both planning and developing the project. In this session we will share about the all the effect types and how we have implemented them for the scroll-type FXToons. And also we'll explain about FXToon viewer's page and layer structure.")
                    .slideUrl("http://www.slideshare.net/deview/121-52734801")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/uQTKuHoHBPPE3P\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/121-52734801\" title=\"[121]네이버 효과툰 구현 이야기\" target=\"_blank\">[121]네이버 효과툰 구현 이야기</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=85BF6C4F6A1186EEE33959498E28E4722CA9&outKey=V125e7143a366eb9fdf89dba242c2c296791c8129df1248264a28dba242c2c296791c&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(3))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("LINE on Apple Watch", "I will talk about how LINE dealt with the Apple Watch. I plan to share first about Apple Watch and Apple Watch service was developed, then move on to discuss our experience of merging LINE messenger with wearable devices, along with various concerns and technological hurdles, and their subsequent solutions. I hope this session would be helpful for people who are intereted in the Apple Watch and wearable devices.")
                    .slideUrl("http://www.slideshare.net/deview/122-line-on-apple-watch")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/iPM5f6Cm3U64R\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/122-line-on-apple-watch\" title=\"[122] line on apple watch\" target=\"_blank\">[122] line on apple watch</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=9E158509B2DD92F557167EF4CF5EDA151A95&outKey=V12850b925433c90f827c65b65b63fc02169b99d99d8050de358765b65b63fc02169b&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(3))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Quality without QA", "At Atlassian, each QA supports 30 developers. Their role is not to manually check developer output, but instead to teach developers how to write quality, robust code during the development process. The secret is to bake quality assurance into every step of the workflow, rather than leaving testing to the end.In this session, I'll cover several techniques to shift your team towards developer-centric QA:<br>- cross-disciplinary \"feature kick-offs\" <br>- applying peer review, continuous integration and deployment to Git feature<br> branching workflows<br>- moving from manual testing to dogfooding and \"blitz testing\"<br>- retraining QA engineers as quality \"agents\" who educate, monitor and report, rather than manually test")
                    .slideUrl("http://www.slideshare.net/deview/123-quality-without-qa")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/iMoG2XYQk7mRN7\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/123-quality-without-qa\" title=\"[123] quality without qa\" target=\"_blank\">[123] quality without qa</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=730B154A2701906CF689F6CFF91B91F7AAEC&outKey=V121357686154e463de9a7622373fa885aea76ee0440cea5b23fd7622373fa885aea7&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(3))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("The Birth of the MIT Cheetah", "The MIT Cheetah is capable of running up to 22 km/h with a locomotion efficiency rivaling animals at the same scale. The talk will discuss the critical aspects of the high force leg actuation system design, the highly parallelized control system architecture based on FPGA and multicore CPU developed for multi-DoF robots, and the implementation of the design principles for energy efficient running robots.")
                    .slideUrl("http://www.slideshare.net/deview/124-mit-cheetah")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/ksfTj1yNom57u0\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/124-mit-cheetah\" title=\"[124] mit cheetah 로봇의 탄생\" target=\"_blank\">[124] mit cheetah 로봇의 탄생</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=1ED842C2A6CC90337573D96732A009E527A9&outKey=V129573823a6372723912e6e2906b89eec07280fa445a81cdec23e6e2906b89eec072&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(3))
                    .room(room4)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Get real-time insights from your application with Packetbeat & Elasticsearch", "While monitoring your application in production was always an important concern for the conscious developers, it has become critical with the wide adoption of DevOps methodologies. When practicing continuous deployment, for example, it is essential to be able to spot performance degradations before they escalate and quickly find the root cause. But for many systems getting the required visibility into what the application is busy with or waiting for is not easy. Packetbeat is an open source project that assists you in monitoring and troubleshooting your application by making use of a different data source: the network packets. It extracts data from application layer protocols like HTTP, MySQL, PgSQL or Redis and leverages Elasticsearch and Kibana to analyze this data and compute performance indicators. The talk will present the inner workings for Packetbeat, going into the details of how the packets are captured at high speed, how TCP streams are reassembled and how protocols are reverse engineered and decoded. It will also show how to use Elasticsearch aggregations to extract key performance indicators and how to use techniques like moving averages to get automatic alerts with minimal configuration.")
                    .slideUrl("http://www.slideshare.net/deview/131-packetbeat-elasticsearch")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/KgrRcM3ZjdnJly\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/131-packetbeat-elasticsearch\" title=\"[131] packetbeat과 elasticsearch\" target=\"_blank\">[131] packetbeat과 elasticsearch</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=A0A5BFD7FAF27BDEB0E2FF1A3199CF0B8398&outKey=V124af6358271f561e3935a3a735d0a8cb030e7950ba4db3f0cd75a3a735d0a8cb030&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(4))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Rust programming language", "I will introduce Rust, a new system programming language. Rust, like C++, offers overhead-free abstraction and memory management without GC. But, unlike C++, blocks memory bugs and simultaneous bugs during compiling. Version 1.0 was released in 2015.")
                    .slideUrl("http://www.slideshare.net/deview/132-rust")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/mjCX4EW6wzMcZk\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/132-rust\" title=\"[132] rust\" target=\"_blank\">[132] rust</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=D2298D1325D4C41F2EAC0CE0E8A7CA9BE407&outKey=V123dc322d0028e1b20ea382dc9d3029d458ea22a247bef4ab811382dc9d3029d458e&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(4))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("VSync in the web browser", "What connection is there between VSync(Vertical Synchronization) signal from hardware and web browser? Furthermore, what relation is there between Window.requestAnimationFrame and Window.requestIdleCallback and VSync?In this session, I will try to find answers to these questions, and speak about what role VSync plays in web browsers.")
                    .slideUrl("http://www.slideshare.net/deview/133-vsync")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/6GrZFmNFcvdTyM\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/133-vsync\" title=\"[133] 브라우저는 vsync를 어떻게 활용하고 있을까\" target=\"_blank\">[133] 브라우저는 vsync를 어떻게 활용하고 있을까</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=F792526FE76397D3184B4F62CDF762868522&outKey=V1296f68e38f3100212409b4ae236e28da6e2821239000bf3d3bb9b4ae236e28da6e2&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(4))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Immersive Sound for VR", "Immersive sound is an absolutely necessary technology for implementing VR, which could tear down the barrier between reality and fantasy. In this session I will go over the basics that startups doing this could implement, to the most current technology trends.I hope in doing so, you can understand Binaural Audio, necessary sound rendering technology for creating Mobile AR/VR. ")
                    .slideUrl("http://www.slideshare.net/deview/134-immersive-sound-vr")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/3kcArwNncXUhoC\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/134-immersive-sound-vr\" title=\"[134] immersive sound vr\" target=\"_blank\">[134] immersive sound vr</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=65E3EA18316B26AD9A6A64038139B1DA06D8&outKey=V1249be33d74d306e719338fc0ff994a9933a647ac7381516470038fc0ff994a9933a&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(4))
                    .room(room4)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("React Everywhere", "Recently, React has been becoming a trend. A part of Facebook’s web version, Ad managers (iOS, Android), Facebook groups (iOS), and Instagram’s web version were all made with React. Airbnb, BBC, CodeCademy, ebay, Flipboard, Netflix, Paypal, reddit, Twitter, NYTimes, Salesforce, Yahoo etc. are all introducing React to their businesses. React is also gaining interest in Korea, and the number of people interested in React continues to grow.The purpose of this session is to be able to use React anywhere - Web, PC (hybrid), or mobile (native). I endeavor to show you how to use this technology more effectively, by presenting a mix of components for using React easily, as well as explaining React’s operation principles.")
                    .slideUrl("http://www.slideshare.net/deview/141-react-everywhere")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/cHO26ALSrA6XT8\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/141-react-everywhere\" title=\"[141] react everywhere\" target=\"_blank\">[141] react everywhere</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=BAA1FFA4240788DDA9D4174FC5C8BA8DCF46&outKey=V1289ef936c3d32277873bd84a5ea14d93e0287a8c7a4eda4905fbd84a5ea14d93e02&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(5))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("How Riot Works", "There are several complex factors necessary in creating a good software development culture. That’s because it's not enough to just put a bunch of skilled developers together. I will describe how we define and advance development culture at Riot Games, where we regard player experience with the utmost importance. Development culture at Riot Games is all based on the Riot Manifesto. We will also take a look at what factors influence development cultures, along with the worries and challenges associated with creating a continually growing development culture. <br>- PLAYER EXPERIENCE FIRST<br>- CHALLENGE CONVENTION<br>- FOCUS ON TALENT AND TEAM<br>- TAKE PLAY SERIOUSLY<br>- STAY HUNGRY, STAY HUMBLE")
                    .slideUrl("http://www.slideshare.net/deview/142-how-riot-works")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/yNNvsNxzzzqMSh\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/142-how-riot-works\" title=\"[142] how riot works\" target=\"_blank\">[142] how riot works</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=9E7AD4F304CE21FC874592194B49B923943C&outKey=V1289ad6afc3bed6b3f0e20d7d30b15428276499a6fed5fccc59920d7d30b15428276&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(5))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("The task of developing a blood type diagnosis for Android, and our experience", "BBB is developing a joint medical data platform with mobile blood type diagnosis elemark. In this process, hardware and software engineers, UX designers and marketing planners have gone beyond blood type diagnosis, using health care data to focus on offering a service that goes 200% beyond the device. I will share the trials and errors these developers from different fields have all gone through, each driving innovation and advancing the process to extend beyond Korea, with the goal of reaching the US and China. ")
                    .slideUrl("http://www.slideshare.net/deview/143-52736062")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/bGuE4KDftk9Idh\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/143-52736062\" title=\"[143] 모바일 혈액진단기기 개발 삽질기\" target=\"_blank\">[143] 모바일 혈액진단기기 개발 삽질기</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=D6307E7DD7C133C9B40DB9822D7AE024021C&outKey=V12972d2dc2f3944bb347c6c8bcbd22996d1d666fc04637558e36c6c8bcbd22996d1d&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(5))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Effective storage access methods in Mobile Apps", "The content of this session is the effectiveness of multi-thread I/O in mobile terminals with flash solution installed. If you use multi-thread I/O in a mobile app, where I/O capability is important, execution speed is overall expected to decrease. I plan to show exactly what kind of effect there is, by comparing specific apps that used multi-thread I/O and apps that didn’t. Furthermore, I will explain the capabilities offered in flash solution, and I will use this to show you that efficiency can be improved.")
                    .slideUrl("http://www.slideshare.net/deview/144mobile-storage")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/D70l4uGXLv1dk4\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/144mobile-storage\" title=\"[144]mobile앱에서 효율적인 storage 접근 방법\" target=\"_blank\">[144]mobile앱에서 효율적인 storage 접근 방법</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=4C6816F42EE96D83E62CC3E47A943C2DC666&outKey=V128809c88b3e98f338aa67f2f9897408c13fdd62ecab14e1edd467f2f9897408c13f&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(5))
                    .room(room4)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Offline customer analysis solution using image recognition and deep learning", "ZOYI provides ‘Walk Insight’ service - analysis of offline customer behavior - by using wireless signals from cell phones. Recently, we have been conducting an R&D project called ‘Vision Insight’ in order to provide new value through CCTV analysis with image recognition technology, which was previously difficult for Walk Insight to provide. In the process of doing so, we often asked the following questions: First, will we be able to deduce a visitor’s profile (gender, age, whether or not they’re a citizen / foreigner)? Second, moving forward, when there are dozens if not hundreds of CCTV images, won’t this technology be able to assume the role human judgement, rather than just monitoring emergency situations? I thought that deep learning could be a good solution for both of these questions, and I applied deep learning to the first question in particular. I am attempting to study about deep learning and share the process of making an actual deep learning application, from the point of view of a normal developer.")
                    .slideUrl("http://www.slideshare.net/deview/151-52736250")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/AyGXwrQoXhYLlR\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/151-52736250\" title=\"[151] 영상 인식을 통한 오프라인 고객분석 솔루션과 딥러닝\" target=\"_blank\">[151] 영상 인식을 통한 오프라인 고객분석 솔루션과 딥러닝</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=A2538A1D3BEAEB9CC1917D99D8132BCBB44A&outKey=V1235b3cc8dbe10a6b0040298bd90fa246e42db11058f1f7a889c0298bd90fa246e42&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(6))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Surviving in the prison, which is a web browser.", "Entry was created as a software education service for new students, realized by using HTML5 only. We mobilized several web technologies such as HTML,Canvas,SVG and Websocket to provide a variety of functions by Entry. I will also share a variety of know-how, such as a visual programming language and interpreter which are based on individually designed JS , hardware device connecting solutions, etc.")
                    .slideUrl("http://www.slideshare.net/deview/152-52736153")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/ntOVKYjkj3OC7S\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/152-52736153\" title=\"[152] 웹브라우저 감옥에서 살아남기\" target=\"_blank\">[152] 웹브라우저 감옥에서 살아남기</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=A2147063DD15EDEFB7953A4BE634AE2B96B0&outKey=V125936a369c6f44401e51567d64b63eeebc3f17fd8212568ccd71567d64b63eeebc3&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(6))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Deep Dive into Apache REEF", "REEF is a meta-framework that provides a control-plane for scheduling and coordinating task-level (data-plane) work on cluster resources obtained from a Resource Manager. REEF provides mechanisms that facilitate resource re-use for data caching, and state management abstractions that greatly ease the development of elastic data processing workflows. REEF is being used to develop several commercial offerings such as the Azure Stream Analytics service and experimental prototypes for machine learning algorithms and a port of the CORFU system. REEF supports both Java and C# languages. REEF is currently an Apache Incubator project that has attracted contributors from several institutions.Code and documentation can be found at http://reef.incubator.apache.org.")
                    .slideUrl("http://www.slideshare.net/deview/153-apache-reef")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/jYaDKfAOBg2WNM\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/153-apache-reef\" title=\"[153] apache reef\" target=\"_blank\">[153] apache reef</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=3E54E23F4993D649DE96112AECBED6C932A1&outKey=V126fdfae3d72b337680659498e28e4722ca9a2c9dc0107a5118559498e28e4722ca9&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(6))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Data Center’s Open Source, Open Compute Project (OCP)", "Nowadays, Open source hardware area is getting bigger because of the wave between Arduino and Raspberry PI. I decided to look into the Open Compute Project, carried out by data centers - traditionally the most conservative space, completely opposed to openness and sharing - in an effort to work together with the excitement over open sourcing. Now faced with about 1.5 billion users, Facebook initiated the OCP project and openly shared all the technology that went into building the data center in April, 2011. I will look into OCP, which seeks to help spur growth in the (cloud; computing; data) ecosystem. Finally, I will examine the low power cloud ARM server, currently in development in conjunction with Naver Labs, which uses OCP’s standards as its base.")
                    .slideUrl("http://www.slideshare.net/deview/154-open-compute-project-ocp")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/jRHOa5OVMRZyJ1\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/154-open-compute-project-ocp\" title=\"[154] 데이터 센터의 오픈 소스 open compute project (ocp)\" target=\"_blank\">[154] 데이터 센터의 오픈 소스 open compute project (ocp)</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=CD19AF51C24748A6C4A6F9D37289474B4F81&outKey=V129d4bc0c473db6361ecb8cff075814dc1d7ccfefa11eb898c9cb8cff075814dc1d7&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(6))
                    .room(room4)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Building a data science team and constructing the system", "In this session I will explain how growing startups can build data science. I will skip over data platforms or machine learning - topics you can hear about in many other sessions - and introduce instead the steps necessary after introducing a data science system in service. <br>- Things you need to do after you get your startup up and running and you need data science.<br>- Role and responsibility of a data science system.<br>- Processes for each step and derivation of results.<br>- Quickly selecting within 4 months the necessary-for-business decision making data derivation, BI, dashboard, logging system, search system, recommendation system, and personalized systems, etc.")
                    .slideUrl("http://www.slideshare.net/deview/161-52739443")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/aqhgl2WkzrcGLK\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/161-52739443\" title=\"[161] 데이터사이언스팀 빌딩\" target=\"_blank\">[161] 데이터사이언스팀 빌딩</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=5C0988F69361E571397F8D0BBC4127CF5D92&outKey=V124fca32be4eae525ff76e704f39b0892d29fc9c98e8b7f99e456e704f39b0892d29&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(7))
                    .room(room1)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("JPA and modern JAVA data storage technology", "There is a huge gap between behavior oriented objects and data oriented relational databases. In order to fill this gap, Java programmers create endless amounts of SQL and mapping codes. In between simply object registration, editing, erasing and looking up through repeating SQL and resolving the disagreement in the paradigm of the gap between object and relational databases, developers go through lots of time and code. We in the Java camp have expended lots of effort in order to alleviate some of this work. As a result, standard Java Persistence API technology was born. JPA allows you to directly save objects in a database without writing SQL, and in the meantime the gap between the object and the relational database is solved.In this session we will take a look at the problem of paradigm disagreement for the object and relational database. We will also look into how the problem is solved through JPA. I also plan to look at the innovative open sources, like QueryDSL, which ensures time safety, and spring data JPA, which helps you use JPA easily in spring framework. Finally, I will share practical experience and know-how, along with optimal efficiency tips.")
                    .slideUrl("http://www.slideshare.net/deview/162-jpa")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/o0VqYMI7amFTPD\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/162-jpa\" title=\"[162] jpa와 모던 자바 데이터 저장 기술\" target=\"_blank\">[162] jpa와 모던 자바 데이터 저장 기술</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=D27B798D516DFB5674A7F9BF709FB4025E42&outKey=V12487e3810f7f167f7ef0da87f37d557eeab09173e9a6baeb6690da87f37d557eeab&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(7))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Accelerated compositing in WebKit: Now and in the future", "It’s been several years since GPU Accelerated Compositing was applied to web engines. Even so, this technology is not complete, and there are many areas that need improvement. In this session I will briefly go over the current Accelerated Compositing that we have made. I will also speak about the state of the coordinated graphics we use in WebKitGTK+/EFL and our future development directions. Finally, I will speak about zero-copy platform layer compositing, used to increase capability while compositing WebGL, HTML5 2D, Canvas, and HTML5 video, all of which apply GPU acceleration.")
                    .slideUrl("http://blog.ryumiel.net/deview2015/#/")
                    .slideEmbed("")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=9B15FAFCF30227E06766EF00FD6CEA124CD2&outKey=V129183b00013f48a97664f3d181e67d1bf1e81c8e5a5358dc0004f3d181e67d1bf1e&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(7))
                    .room(room3)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Pinpoint: Optimized APM for large scale distributed computing environments", "Pinpoint is a tool for APM (Application Performance Management) in large scale distributed computing environments. Recently, distributed architecture, like MSA (MicroService Architecture) is becoming more common. Yet if you actually institute distributed architecture, you run into difficulties in development and operation because of additional complexities. Pinpoint helps you quickly and easily solve efficiency problems and failure detection by offering a visualized overview of distributed components. <br>I plan to cover the following material in this session:<br>1. Common problems that arise in distributed environments.<br>2. Pinpoint introduction and the two technologies that comprise it<br>3. Examples of problems solved using Pinpoint <br>4. Introduction of RPC TimeLine Pattern, which easily divides and conquers the problems of distributed environments.")
                    .slideUrl("http://www.slideshare.net/deview/164-pinpoint")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/16i6mayeNK12Ai\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/164-pinpoint\" title=\"[164] pinpoint\" target=\"_blank\">[164] pinpoint</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=8B3E4703564F97B2324684DAAA3C8470A23D&outKey=V126be8b6348f5c4d31ba343b1734c0a7d68ad2e391384e567e39343b1734c0a7d68a&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(7))
                    .room(room4)
                    .speakerSet(speakers).build());


            // Day 2
            programDate2 = programDateService.add(
                    new ProgramDate.Builder("Day 2", "2015-09-15").conference(conference).build());

            programs = new ArrayList<>();

            programs.add(programService.add(new Program.Builder("Registration", "Registration", "09:30", "10:00")
                    .date(programDate2)
                    .room(room1)
                    .programType(ProgramType.REGISTER).build()));

            programs.add(programService.add(new Program.Builder("프로그램 1 (10:00-10:50)", "", "10:00", "10:50")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 2 (11:00-11:50)", "", "11:00", "11:50")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 3 (12:00-12:50)", "", "12:00", "12:50")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("Lunch", "Lunch", "12:50", "14:10")
                    .date(programDate2)
                    .programType(ProgramType.LUNCH).build()));

            programs.add(programService.add(new Program.Builder("프로그램 4 (14:10-15:00)", "", "14:10", "15:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 5 (15:10-16:00)", "", "15:10", "16:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("프로그램 6 (16:10-17:00)", "", "16:10", "17:00")
                    .date(programDate2)
                    .programType(ProgramType.SESSION).build()));

            programs.add(programService.add(new Program.Builder("BOF", "BOF", "17:15", "18:30")
                    .date(programDate2)
                    .room(room1)
                    .programType(ProgramType.BOF).build()));

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Naver Search and data mining", "I will describe how data mining has been used at Naver Search over the past tenyears, and I will share the know-how I’ve gained from these processes. I will speakabout NAVER related search words, the first service to use data mining, real timesearch words, as well as the data mining projects currently in development.I will talk about what it takes to be a good data miner, as well as the differencebetween data mining with real data, and data mining through theory. ")
                    .slideUrl("http://www.slideshare.net/deview/211-52777946")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/6XZVMo96tgdeN2\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/211-52777946\" title=\"[211] 네이버 검색과 데이터마이닝\" target=\"_blank\">[211] 네이버 검색과 데이터마이닝</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=5204088D462F0EEC970B3EEDE749FF3FC015&outKey=V128b41d67534c81db8453e18cecaae499f656200df82f66b587b3e18cecaae499f65&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(2))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Large scale backend service development using Node.js, Docker and AWS", "League of Legends (LoL) is a multiplayer online video game developed and publishedby Riot Games.I'd like to share what I learned from my experience developing a backend serviceapplication that processes millions of requests (per minute) with the extremely lowlatency.<br>In this presentation, I'll discuss: <br>- AWS-based cluster setup for RESTful services <br>- Writing production level Node.js applications <br>- Redis use cases, scaling and optimization techniques <br>- Docker container deploy automation and performance optimization in AWSECS/EC2 ")
                    .slideUrl("http://www.slideshare.net/deview/212-large-scale-backend-service-develpment")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/mDJ2Vn6e4kjLAF\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/212-large-scale-backend-service-develpment\" title=\"[212] large scale backend service develpment\" target=\"_blank\">[212] large scale backend service develpment</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=BF1941592A00EF383238F9D37289474B4F81&outKey=V126e0a143d5be34066a6062e833f97b7da2efc68b33bb5e090ca062e833f97b7da2e&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(2))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Building Ethereum", "The Ethereum project has advanced block-chain technology beyond the paradigm ofspecific use-cases; with Ethereum it is now possible to upload custom rules and havetheir execution secured by the block-chain. Ethereum can be seen as a \"World Computer\", a shared robust secure computing resource that anyone can use. Itincludes both execution and storage, with customizable cryptographic permissionspossible on both.This talk will explore the technology that makes this possible, some of the challengesthat had to be overcome and some of the possibilities that Ethereum can power. ")
                    .slideUrl("http://www.slideshare.net/deview/231-ethereum")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/bTq6421tlKpqSI\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/231-ethereum\" title=\"[213] ethereum\" target=\"_blank\">[213] ethereum</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=35898A4BF1D77828134E9D6A553FBF3DD2BF&outKey=V12638c873d15475cbff5599466dd70e845832a3f907c8212e1ce599466dd70e84583&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(2))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Data science with Apache Zeppelin", "Apache Zeppelin is a Notebook environment supporting Spark and several otheranalysis frameworks. When constructing data analysis systems using open sourcesoftware, analysts spend more time focusing on system composition and maintenancethan actual data analysis, due to the many complications you experience with systemcomponents.In this session, we will diagnose the problems encountered in previous experiencesapplying Zeppelin in systems that analyze AcessLog, using Hadoop / Hive / Spark /mongodb, etc. We will also look at he improved components of the system thanks toZeppelin. I will also present some solutions for problems that actual data analysis teams couldrun into, such as crowd user support, library archive composition and notebooksharing. I will also look at Zeppelin’s versatility, and share methods that users couldadd, such as a new analysis framework and visualization. ")
                    .slideUrl("http://www.slideshare.net/deview/214-data-science-with-apache-zeppelin")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/AB3pvZL8aAojuj\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/214-data-science-with-apache-zeppelin\" title=\"[214] data science with apache zeppelin\" target=\"_blank\">[214] data science with apache zeppelin</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=28A20F4B24B4CDDBCA854304CA058CC3AD52&outKey=V127a17b1f39d72fafb2f528e2fcba8005b174f635702bc16e8de528e2fcba8005b17&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(2))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Docker Orchestration", "In this talk, docker orchestration means the process of creating a service from severaldocker containers.I will talk about compositions and implementations of Docker Orchestration, andintroduce the work we are doing in order to easily dockerize a service through DockerOrchestration from the view of building and deploying services. ")
                    .slideUrl("http://www.slideshare.net/deview/221-docker-orchestration")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/6CYefsfZIwfvom\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/221-docker-orchestration\" title=\"[221] docker orchestration\" target=\"_blank\">[221] docker orchestration</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=6A22A14266C44FC826565B5545C6D6FA7D1B&outKey=V12599bc3d072339869ac094d0787416655bf68c88a551de614c6094d0787416655bf&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(3))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Dialogue system service trends and development methods", "Since Apple introduced Siri in 2011, many smart phone apps with dialoguecapabilities included have been introduced, including Samsung’s s-voice andGoogle’s Now. Furthermore, nowadays AI systems with dialogue capabilities, such asSoftBank’s Pepper, the robot Jibo, and IBM’s Watson are continually beingintroduced.At Naver we are conducting research related to dialogue technology and 2012’s Linkapp development. In this presentation I will share contents of studies / research atNaver concerning dialogue technology development. Specifically, I will cover‘dialogue management,’ absolutely necessary in developing a dialogue system. ‘Dialogue management’ is the base technology that manages the dialogue betweenthe user and the system, and ensures the system gives an appropriate answer to theusers questions. ")
                    .slideUrl("http://www.slideshare.net/deview/222-52779117")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/rz4Pim3pcVI688\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/222-52779117\" title=\"[222]대화 시스템 서비스 동향 및 개발 방법\" target=\"_blank\">[222]대화 시스템 서비스 동향 및 개발 방법</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=6BB36DE9C2613C1DF7880E798E7B382BB0E8&outKey=V12351390ac5a4d21f8a362ee1b296cddb4ec4e342f84739b011462ee1b296cddb4ec&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(3))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("HBase consistent secondary indexing", "In HBase, data scan is only possible in rowkey-based lexicographic order, andsecondary indices are not supported. In order to acquire data based on a value not arowkey, users have to build a secondary index by themselves, or use open-sourcedsecondary index systems. But these systems do not ensure index consistency. Someof them offers eventual consistency, and some of them offers batch scripts to fixmanually. Results from these are not trustworthy, and you can’t use them when theconsistency is crucial. Under these circumstances, HIM was developed to support thefull consistency of secondary index.In this session, I will propose a way to ensure the index consistency on HBase and giveyou some background informations. I will introduce HIM, the features, performancesand the internal structure using coprocessors, custom filters and the zookeeper. ThenI’ll discuss about use-patterns of the secondary index on HBase that differentiatesfrom that of RDBMS, and tips that supports this. ")
                    .slideUrl("http://www.slideshare.net/deview/223-h-base-consistent-secondary-indexing")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/ocQ96tLNFge6P\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/223-h-base-consistent-secondary-indexing\" title=\"[223] h base consistent secondary indexing\" target=\"_blank\">[223] h base consistent secondary indexing</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=418C21077EB1D33E966E04CC676647DAE467&outKey=V128f33271536740e439e39e3c5c982f5e4382d3853f4c26a0a6239e3c5c982f5e438&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(3))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Machine translation model based query typo correction system", "When users search, they often input typos such as ‘Nayver,’ and when they do so theyare not satisfied with the results. In this case, if you offer the correction ‘Naver’, theirsatisfaction rate goes up. For a few years ago, Naver offered a query typo correctionservice, using NLP technology. Typos have increased as smart phone usage hasincreased over PC use at home, and in accordance Naver’s existing query typocorrection system reached a correction recommendation limit. As a result, the needfor a higher quality query typo correction system increased in turn.So we have newly developed a query typo correction algorithm and library to replacethe current query typo correction system, and we are here at DEVIEW to introduce thenew system that will be applied to Naver search.")
                    .slideUrl("http://www.slideshare.net/deview/242-52779038")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/84NJTvDwZxDfEe\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/242-52779038\" title=\"[224] 번역 모델 기반_질의_교정_시스템\" target=\"_blank\">[224] 번역 모델 기반_질의_교정_시스템</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=890B250F426BE8B47A80F6CFF91B91F7AAEC&outKey=V1246f934f11dd6bdfcf2175e33d8d58ea42cdf34ac4ad2638c23175e33d8d58ea42c&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(3))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("The simplicity of cluster apps with the Circuit: Implementing a job scheduler", "The Circuit is a light-weight cluster operating system. It is based on a peer-to-peernetwork of daemons, connected through a robust, dynamic membership andbroadcasting protocol. Every host, member of a circuit, provides a local API thatpresents a dynamic, unified view and control over the entire cluster. The API isavailable as a command-line tool, as well as a Go package. During this talk, I am going to Go over the Circuit abstraction of a cluster as well asthe practical side of running and organizing a circuit cluster. We are then going toimplement a complete basic cluster job scheduler from scratch, using the Circuit GoAPI, and demo its usage. Time permitting, I will give a peek into the power of process recursion at cluster scale. ")
                    .slideUrl("http://www.slideshare.net/deview/231-the-simplicity-of-cluster-apps-with-circuit")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/bvgrLDOZKqDAuD\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/231-the-simplicity-of-cluster-apps-with-circuit\" title=\"[231] the simplicity of cluster apps with circuit\" target=\"_blank\">[231] the simplicity of cluster apps with circuit</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=DD66CE5C6864BCED23D681E760F310062BC7&outKey=V129bfa48eab3452335fb4aaac45aa20d9c9961447479c0a2815c4aaac45aa20d9c99&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(4))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Framework for supercomputing and data analytics, and UNIST’s big data management framework", "Many companies are turning to clusters to carry out data analysis, thanks to the jumpin interest in big data management. On the other hand, scientists, who previouslyused highly effective clusters produced by super computers, are increasinglybecoming more interested in big data analytics to analyze science data. In this presentation I will compare Hadoop and HPC midwear from a highly effectivecomputing standpoint and I will introduce highly effetive data analytics frameworkcurrently in development at UNIST. ")
                    .slideUrl("http://www.slideshare.net/deview/232-52780634")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/NyLna15cLs99w6\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/232-52780634\" title=\"[232] 수퍼컴퓨팅과 데이터 어낼리틱스\" target=\"_blank\">[232] 수퍼컴퓨팅과 데이터 어낼리틱스</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=75B19CD0DB40AE8AB91240CC50C2F6644F9E&outKey=V12838593a4379f6af21b24e10033cb9032cf2edc7c3322b9eba224e10033cb9032cf&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(4))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Level 2 Network Programming using PacketNgin RTOS", "Most network programming is based on the premise of end-to-end communication.To developers, it is only a way to send and receive data from long distances.Networkmust be working on and be existing.But do you guys know that for the packet yousend to reach the other side of the earth, it has to pass through countless operationprocesses? Do you guys even know that all the contents of the packets you send andreceive is being read and altered by a network in the middle?In this session we are going to go over the basic concepts that you learned in yournetwork major, and learn how to program from the viewpoint of network equipment,using an RTOS called PacketNgin. ")
                    .slideUrl("http://www.slideshare.net/deview/233-level-2-network-programming-using-packet-ngin-rtos")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/n1hbkKjLArqBNX\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/233-level-2-network-programming-using-packet-ngin-rtos\" title=\"[233] level 2 network programming using packet ngin rtos\" target=\"_blank\">[233] level 2 network programming using packet ngin rtos</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=5352354831F6CAB38CDDC82F7BDB208959D3&outKey=V125e9103a4707079d942ded0b06bbc892e149c269b1621994a25ded0b06bbc892e14&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(4))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Developing augmented reality DAQRI Helmet for industry job sites", "Once available only in movies such as 'Minority Report', Augmented Reality (AR) nowbecomes real. In this session, we discuss brief overview and keytechnologies/challenges in AR. The session further covers hardware specifications ofDAQRI Helmet, the wearable AR device for the industrial market, and DAQRI's opensource platform for AR developers. ")
                    .slideUrl("http://www.slideshare.net/deview/234-daqri-helmet")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/3C7wSxEiBebrXi\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/234-daqri-helmet\" title=\"[234] 산업 현장을 위한 증강 현실 기기 daqri helmet 개발기\" target=\"_blank\">[234] 산업 현장을 위한 증강 현실 기기 daqri helmet 개발기</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=28D800746109CD63D54AD96732A009E527A9&outKey=V129eff1d06a3157b80ba34a89f530e7c10ece397837cca01f7b734a89f530e7c10ec&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(4))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Implementing a realtime notification system in logging platform, using Apache Storm and Elasticsearch", "In this session I would like to talk about the new notification system we haveimplemented recently. As our product, the logging platform, is serving all the logsgenerated from a variety of sources, there is a huge data inflow to this. By using thisnew notification system, developers are able to register their custom alerts for whatthey want to be notified about with Lucene query, and able to take an actionimmediately when there is a problem after they get a realtime notification about it.I would like to also introduce open source products, such as Apache Storm,Elasticsearch, Redis, and Kafka, that we made use of implementing the realtimenotification system and share our exprience about the values we could deliver to ourcustomers. ")
                    .slideUrl("http://www.slideshare.net/deview/241-storm-elasticsearch")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/4QpZqx6RMKoqhQ\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/241-storm-elasticsearch\" title=\"[241] Storm과 Elasticsearch를 활용한 로깅 플랫폼의 실시간 알람 시스템 구현\" target=\"_blank\">[241] Storm과 Elasticsearch를 활용한 로깅 플랫폼의 실시간 알람 시스템 구현</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=1AE58AE84FFB2DAD7CCEBE1E45F99EDC45F4&outKey=V1276b04873db4e7a5d7f26fc8a54e6a68531dcbac78bf5c7f96626fc8a54e6a68531&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(5))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Using WIFI to recognize indoor places", "It's the O2O(Online to Offline) age. Lots of people are curious where other peopleare. Knowing this, you could understand users better and offer better service. Yet it’shard to distinguish given the location technology on smart phones now if I’m on thesecond or first floor of my house, and it’s impossible to identify people who were inthe same place. In this session I will share with you my actual experience of solving these kinds ofproblems using wifi, the most widely installed wireless signal source in existence. ")
                    .slideUrl("http://www.slideshare.net/deview/242-wifi")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/C5bwtds47QxCc2\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/242-wifi\" title=\"[242] wifi를 이용한 실내 장소 인식하기\" target=\"_blank\">[242] wifi를 이용한 실내 장소 인식하기</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=51A8804F714469FB537FC8ACD0C5EFA4308C&outKey=V12802245bb3a2a5c1fe18880c6dfa2b1982a17c897a663f38f098880c6dfa2b1982a&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(5))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Turning data into value", "Practical advice and insights about applying machine learning to real life big dataproblems: Methods, Examples and Lessons learned ")
                    .slideUrl("http://www.slideshare.net/deview/243-turning-data-into-value")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/38964Awax5x9c\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/243-turning-data-into-value\" title=\"[243] turning data into value\" target=\"_blank\">[243] turning data into value</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=01515E53E483198769082EC8B90C7959E792&outKey=V121305785cf407f4a5ea50c2b3b93a31b32d7b6cdd10dfb9e54d50c2b3b93a31b32d&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(5))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Integrated processing model of batch and streaming in large-scale environments for NAVER indexing system", "Document refinement, necessary for NAVER search service, requires a bulk dataprocessing of enormous data set (batch processing) and real-time processing ofobtained data (stream processing). Furthermore, there needs to be harmony betweenboth systems. There are many open source solutions for large scale distributedsystems, such as Hadoop, HBase, Storm, Spark, Kafta etc, but not every problem canbe solved with just one solution. Additionally, there are maintenance problems if youmake data processing logics for each solution separately. In this session we willexplain to solve these problems how we abstract to the programming model, andalso how you can combine both batch and stream processing. <br>- Batch Processing vs Stream Processing<br>- Domaine Architecuture<br>- Method of initial batch processing ")
                    .slideUrl("http://www.slideshare.net/deview/244-52781290")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/euBc09lp154g0e\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/244-52781290\" title=\"[244] 분산 환경에서 스트림과 배치 처리 통합 모델\" target=\"_blank\">[244] 분산 환경에서 스트림과 배치 처리 통합 모델</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=D45E893EB0B0DA3B2FC26ADFD10800810E81&outKey=V12591113dc086d00bc049050f64e9a0c968acbdb3f856babeb849050f64e9a0c968a&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(5))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Implementing Deep Learning using cuDNN", "I will share with you methods and difficulties we learned from our experiencedeveloping a Deep Learning Engine (VUNO-Net) using Cuda Library. I will also sharewith you several results from this.<br>1. Deep Learning Review <br>2. Implementation on GPU using cuDNN <br>3. Optimization Issues <br>4. Introduction to VUNO-Net ")
                    .slideUrl("http://www.slideshare.net/deview/251-implementing-deep-learning-using-cu-dnn")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/k0J0tMjI9PHi4\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/251-implementing-deep-learning-using-cu-dnn\" title=\"[251] implementing deep learning using cu dnn\" target=\"_blank\">[251] implementing deep learning using cu dnn</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=2DF28734011459F3106E909DE1F3D56A1DE7&outKey=V1241f3305d7058bc7cdb539917a6a68d380916001776ebda6b1b539917a6a68d3809&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(6))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Developing Cana, the incremental processing platform", "In this session I will introduce Cana, used for document refining in incremental searchsystems. I will also share several problems we encountered and subsequent solutionsduring the development phase. Cana allows you to use Snapshot Isolation leveltransactions in HBase as an incremental processing platform and offers an executionmodel through Event Notification.")
                    .slideUrl("http://www.slideshare.net/deview/252-cana")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/hTTlpC14IlTb3G\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/252-cana\" title=\"[252] 증분 처리 플랫폼 cana 개발기\" target=\"_blank\">[252] 증분 처리 플랫폼 cana 개발기</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=61367F6C0F7BD5AB2985977D5A4B782B3374&outKey=V127c789993c21e275d01a138c997517b84eed7b674c2e556ba99a138c997517b84ee&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(6))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Enterprise Dataflow with Apache NiFi", "Dataflow is all about connecting systems with different protocols and data formatsthat are constantly changing. Messaging-based solutions are a popular answercurrently, but they don’t address many of the fundamental challenges of enterprisedataflow. We will identify the capabilities of Apache NiFi provides for end-to-end dataflow andhow it ensures compliance of distributed systems at scale with new lineagetechniques and comprehension the information supply chain. ")
                    .slideUrl("http://www.slideshare.net/deview/253-apache-ni-fi")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/dFUoY6q9GoqvdU\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/253-apache-ni-fi\" title=\"[253] apache ni fi\" target=\"_blank\">[253] apache ni fi</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=624DE5E13E2B141ADAF8DA2B8AF67AA03195&outKey=V129f07a23003046a6a36216fddaa4e7ce4786f9e870ed3e2393a216fddaa4e7ce478&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(6))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Digging up Presto’s internal structure", "In this session I will dissect Presto’s internal structure, something you can’t do throughsource code analysis or search. I will touch on main Library, ByteCode generation, functions with hidden Plugins, andhow to write new Plugins. ")
                    .slideUrl("http://www.slideshare.net/deview/245-presto")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/y62PNWHf7pEQhL\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/245-presto\" title=\"[245] presto 내부구조 파헤치기\" target=\"_blank\">[245] presto 내부구조 파헤치기</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=4E9B76630BF40304DD65ACB9DC3074B7360C&outKey=V1284071602382b8b95f1e1170fa295b4ff63a5826109913c748de1170fa295b4ff63&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(6))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Putting an elephant in a fridge: Real-time recommendation engine in one machine", "Anyone who works with data tries to build a recommendation engine at least once.Whether it be though small-scale collaboration filtering, hadoop, or large-scaledistributed processing with spark, trying to build this engine is one of many romanticnotions. During this time, let's take a look at building a real-time, global-scalerecommendation engine and putting it into the size of a good laptop. This isn’t ajoke. A global-service sized, real time engine in one machine. I’m going to look intohow that’s possible.Let’s take a look at the beauty of a probabilistic data structures. ")
                    .slideUrl("http://www.slideshare.net/deview/261-52784785")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/qlBWwUSkc2kHb\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/261-52784785\" title=\"[261] 실시간 추천엔진 머신한대에 구겨넣기\" target=\"_blank\">[261] 실시간 추천엔진 머신한대에 구겨넣기</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=9CC2F615FB0F8B725D3C231E17F531B10E29&outKey=V1264ae9e3e7bce36e52f04fe9c604946e9857646a7da1c183a2e04fe9c604946e985&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(7))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Netflix Big Data Platform and Apache Spark", "The Big Data Platform team at Netflix maintains a cloud-based data warehouse withover 10 petabytes of data stored predominantly in Parquet format. Our platform hastraditionally leveraged Pig for ETL processing, Hive for large analytic workloads, andPresto for interactive and exploratory use cases. For a long time, Spark seemedattractive to complement our platform, but technical gaps prevented effective use atscale in our environment. Recent improvements have allowed us to add Spark to ourcloud data architecture and interoperate seamlessly with the other tools and servicesin our stack. We will go into detail about our deployment configuration and what it takes to runSpark alongside traditional workloads on YARN. We will share examples of a few ofour largest workflows translated to Spark for comparison in terms of bothperformance and complexity. ")
                    .slideUrl("http://www.slideshare.net/deview/262-netflix")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/y8cFNI5Z1rQM8y\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/262-netflix\" title=\"[262] netflix 빅데이터 플랫폼\" target=\"_blank\">[262] netflix 빅데이터 플랫폼</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=F1E121E710EDAB66D7ACC752D9BD61DC4C8B&outKey=V12411536fe149711f1e1a8f87dd4a791d1d5c32803d2c85b838ca8f87dd4a791d1d5&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(7))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("S2Graph: A Large scale graph database with HBase", "S2Graph is a Graph Database that can collect and check user activity in real time. I willshare with you the purpose of creating Graph Database, whose name is even new,along with existing problems you can solve by using it. I will specifically look at howyou can use S2Graph to quickly and easily build something like Facebook’s newsfeed, chatting services or real-time content recommendation engines.And of course, no matter how convenient and easy technology is, if it doesn’t bringresults, it’s meaningless. I will check S2Graph’s linear scalability with benchmark, andshare with you the actual service traffic condition. ")
                    .slideUrl("http://www.slideshare.net/deview/263-s2graph-largescalegraphdatabasewithhbase2")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/iNgVtJYhhvGuYU\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/263-s2graph-largescalegraphdatabasewithhbase2\" title=\"[263] s2graph large-scale-graph-database-with-hbase-2\" target=\"_blank\">[263] s2graph large-scale-graph-database-with-hbase-2</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=476077CFB274F31E3A3C9017F65C3DC26D30&outKey=V125fb0a32f63f576621e1b6811099841c5e088c1a1d90e05bef21b6811099841c5e0&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(7))
                    .room(room2)
                    .speakerSet(speakers).build());

            speakers = new HashSet<>();
            speakers.add(users.get(random.nextInt(10)));
            sessionService.add(new Session.Builder("Large-Scale Deep Learning on Spark", "Deep Learning algorithms for big data are usually realized from GPGPUprogramming. Yet Google is driving Deep Learning on large-scale distributedsystems, like DistBelief. Large Scale Deep Learning is also being carried out in opensource communities, on distributed systems like Hadoop.I will introduce Deep Learning algorithms realized under these circumstances, and Iwill talk about methods for pursuing Deep Learning using distributed architecture,such as Spark. ")
                    .slideUrl("http://www.slideshare.net/deview/246-large-scale-deeplearningonspark")
                    .slideEmbed("<iframe src=\"//www.slideshare.net/slideshow/embed_code/key/cUIrzmLT4ayDjN\" width=\"595\" height=\"485\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" style=\"border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;\" allowfullscreen> </iframe> <div style=\"margin-bottom:5px\"> <strong> <a href=\"//www.slideshare.net/deview/246-large-scale-deeplearningonspark\" title=\"[264] large scale deep-learning_on_spark\" target=\"_blank\">[264] large scale deep-learning_on_spark</a> </strong> from <strong><a href=\"//www.slideshare.net/deview\" target=\"_blank\">NAVER D2</a></strong> </div>")
                    .videoUrl("http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=20353647861C3D7B884B2110A47682ED43D4&outKey=V1213d6cc7b142b019ba20c6d1b1808f8cced609ef93f7892af010c6d1b1808f8cced&controlBarMovable=true&jsCallable=true&skinName=tvcast_white")
                    .program(programs.get(7))
                    .room(room2)
                    .speakerSet(speakers).build());

            // PyCon APAC 2016
            conference = conferenceService.add(new Conference.Builder("PyCon APAC 2016", "Respect, Diversity", "<h1>행사개요</h1><div>파이콘은 세계 각국의 파이썬 프로그래밍 언어 커뮤니티에서 주관하는 비영리 컨퍼런스입니다.</div><div><br></div><div>한국에서는 처음으로 열린 파이콘 한국 2014를 시작으로 파이콘 한국 준비위원회는 건강한 국내 파이썬 생태계에 지속적인 보탬이 되고자, 커뮤니티 멤버들의 자발적인 봉사로 운영되고 있습니다. 2016년에는 파이콘 한국을 아시아-태평양 지역 파이썬 사용자들과 함께하는 파이콘 APAC 으로 진행하여, 더욱 다양한 참가자들과 함께 새로운 기술과 정보를 공유하고 서로 교류할 수 있는 행사가 되기를 희망합니다.</div><div><br></div><ul><li>일정 : 2016년 8월 13일(토) ~ 15일(월)</li><li>장소 : 강남구 코엑스 전시장</li><li>인원 : 약 1,500명 예상</li><li>주최 : 파이콘 한국 준비 위원회</li><li>대상 : 국내외 파이썬 개발자 혹은 관심이 있는 분이라면 누구나</li><li>발표 : 25분 또는 40분 길이의 발표</li></ul><div><br></div><h1>발표자 모집</h1><div>파이썬에 대한 학술적 또는 상업적 프로젝트, 케이스 스터디 등 다양한 파이썬 관련 발표를 모집하고 있습니다. 자세한 내용은 발표자 모집 페이지를 참고해주세요.</div><div><br></div><ul><li>2016. 4. 12 : 발표 주제 모집 오픈</li><li>2016. 5. 31 : 발표 주제 모집 마감</li><li>2016. 6. 15 : 최종 발표자 확정</li></ul>")
                    .location("COEX Grand Ballroom, SEOUL")
                    .locationUrl("https://www.google.co.kr/maps/place/%EC%BD%94%EC%97%91%EC%8A%A4+%EC%BB%A8%EB%B2%A4%EC%85%98%EC%84%BC%ED%84%B0+%EA%B7%B8%EB%9E%9C%EB%93%9C%EB%B3%BC%EB%A3%B8/@37.5081321,127.0336615,14z/data=!4m5!1m2!2m1!1scoex!3m1!1s0x357ca46bcb9e129f:0xd6bf8dde518b69a4")
                    .latlan(37.513204, 127.058638)
                    .host(host)
                    .charge(Charge.CHARGED)
                    .price(50000)
                    .build()
            );

            File file = new File(filePath + "pycon/main/milkyway.jpg");
            assetsService.add(utils.fileSaveHelper(file, host, "/img/").setConference(conference));

            conferenceRoleService.add(new ConferenceRole().setUser(host).setConference(conference).setConferenceRole(Role.HOST));
            presenceService.add(new Presence().setUser(host).setConference(conference).setPresenceCheck(Check.PRESENCE));
        };
    }
}
