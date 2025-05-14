package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private Long id;
    private String username;
    private int age;

    //기본생성자
    public Member() {
    }

    //ALT INSERT
    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

}
