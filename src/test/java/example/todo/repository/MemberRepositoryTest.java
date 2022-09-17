package example.todo.repository;

import example.todo.Domain.Member;
import example.todo.service.LoginService.LoginServiceImpl;
import example.todo.service.memberService.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.useDefaultRepresentation;

@SpringBootTest
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberServiceImpl service;

    @Autowired
    LoginServiceImpl loginService;

    @Test
    public void save() throws Exception {
        //given
        Member member1 = new Member("123", "456", "member1");
        Member member2 = new Member("123", "555", "member2");

        //when
        Long savedId1 = service.save(member1);
        Long savedId2 = service.save(member2); // loginId 같으므로 저장 실패

        //then
        assertThat(savedId1).isEqualTo(member1.getId());
        assertThat(savedId2).isNull();
    }

    @Test
    public void login() throws Exception {
        //given
        Member member1 = new Member("123", "456", "member1");
        service.save(member1);

        //when
        Optional<Member> loginMember1 = loginService.login("123", "456"); // O, O
        Optional<Member> loginMember2 = loginService.login("123", "555"); // O, X
        Optional<Member> loginMember3 = loginService.login("333", "123"); // X, O
        Optional<Member> loginMember4 = loginService.login("333", "333"); // X, O

        //then
        assertThat(loginMember1.isPresent()).isTrue();
        assertThat(loginMember2.isPresent()).isFalse();
        assertThat(loginMember3.isPresent()).isFalse();
        assertThat(loginMember4.isPresent()).isFalse();

    }

    @Test
    @Transactional
    public void findAll() throws Exception {
        //given
        Member member1 = new Member("111", "222", "member1");
        Member member2 = new Member("333", "444", "member2");
        Member member3 = new Member("555", "666", "member3");
        Long savedId1 = service.save(member1);
        Long savedId2= service.save(member2);
        Long savedId3 = service.save(member3);

        //when
        List<Member> list = service.findAll();

        //then
        assertThat(list.size()).isEqualTo(3);
        assertThat(list).contains(
                service.findById(savedId1).get(),
                service.findById(savedId2).get(),
                service.findById(savedId3).get());
    }


}