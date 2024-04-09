package com.guen.init;

import com.guen.common.model.MemberType;
import com.guen.error.ErrorCode;
import com.guen.program.member.entity.Member;
import com.guen.program.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class InitRunner implements ApplicationRunner {
    @Autowired
    ErrorCode.ErrorCodeInitializer errorCodeInitializer;

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("\n ================================== property 초기화  ========================= \n");
        errorCodeInitializer.init();

        log.info("\n ================================== db 임시 데이터 저장  ========================= \n");
        log.info("\n ================================== admin 임시 데이터 저장  ========================= \n");
        memberRepository.findByAccount("admin").ifPresentOrElse(
                member-> System.out.println("admin 존재"),
                ()-> {
                    memberRepository.save(Member.builder()
                        .account("admin")
                        .password(encoder.encode("admin"))
                        .name("관리자")
                        .type(MemberType.ADMIN)
                        .build());
                }
        );
        memberRepository.findByAccount("user").ifPresentOrElse(
                member-> System.out.println(""),
                ()-> {
                    memberRepository.save(Member.builder()
                            .account("user")
                            .password(encoder.encode("user"))
                            .name("사용자")
                            .type(MemberType.USER)
                            .build());
                }
        );
    }
}
