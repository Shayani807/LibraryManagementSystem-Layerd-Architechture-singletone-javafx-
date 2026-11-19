package dao;
import entity.Member;
import java.util.List;

public interface MemberDAO {
    boolean save(Member member);
    List<Member> getAll();
    boolean update(Member member);
    boolean delete(String memberId);
}