package hello.servlet.domain.member;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    //테스트가 끝나면 초기화
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("회원 저장 테스트")
    void save(){
        //given
        Member member = new Member("hello",23);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);

    }

    @Test
    void findAll(){
        //given
        Member member1 = new Member("member1", 21);
        Member member2 = new Member("member2",33);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();

        //then
        //alt enter => import 추출
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1,member2);
    }

}