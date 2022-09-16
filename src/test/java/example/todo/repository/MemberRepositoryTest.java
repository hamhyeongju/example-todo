package example.todo.repository;

import example.todo.Domain.Member;
import example.todo.service.memberService.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberServiceImpl service;

    @Test
    public void save() throws Exception {
        //given
        Member member1 = new Member("123", "456", "member1");
        Member member2 = new Member("123", "555", "member2");

        //when
        Long savedId1 = service.save(member1);
        Long savedId2 = service.save(member2);

        //then
        assertThat(savedId1).isEqualTo(member1.getId());
        assertThat(savedId2).isNull();
    }

}