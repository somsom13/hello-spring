package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository{ //인터페이스 상속(추상메소드 구현)

    private static Map<Long,Member> store=new HashMap<>();//(id-member) 저장
    private static long sequence=0L;
    @Override
    public Member save(Member member) {
        member.setId(++sequence);//id는 1 증가한 seq 값으로 set!, name은 user가 입력한 값이 그대로 들어감
        store.put(member.getId(),member);//store이라는 hashMap에 key-value로 저장 (id에 member 대응)
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));//null일 떄 처리
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member->member.getName().equals(name))//store을 모두 돌면서, 저장되어있는 각 member의 이름에 대해 일치하는 이름 찾기
                .findAny();//하나라도 찾아라!  끝까지 없으면 null이, 찾는다면 해당 Member 객체 return

    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    } //Member 객체 list

    public void clearStore(){
        store.clear();
    }
}
