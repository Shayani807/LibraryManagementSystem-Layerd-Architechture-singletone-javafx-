package bo;
import dto.MemberDTO;
import java.util.List;

public interface MemberBO {
    boolean saveMember(MemberDTO dto);
    List<MemberDTO> getAllMembers();
    boolean updateMember(MemberDTO dto);
    boolean deleteMember(String memberId);
}