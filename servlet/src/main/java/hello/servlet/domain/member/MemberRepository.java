package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {

    //아이디, 멤버값(이름, 나이) 저장
    private static Map<Long, Member> store = new HashMap<>();

    //id는 증가하는 시퀀스값
    private static long sequence = 0L;

    //싱글톤으로 만들거에요
    private static final MemberRepository instance = new MemberRepository();

    //무조건 얘로 조회해야함
    public static MemberRepository getInstance(){
        return instance;
    }

    //싱글톤을 만들때는 private으로 생성자를 막을거에요
    private MemberRepository() {
    }

    //멤버 정보 저장
    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId() ,member);
        return member;
    }

    //아이디로 멤버 정보 찾기
    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    //다 날려버리기
    public void clearStore(){
        store.clear();
    }


}
