package bo.impl;
import bo.MemberBO;
import dao.MemberDAO;
import dao.impl.MemberDAOImpl;
import dto.MemberDTO;
import entity.Member;
import java.util.ArrayList;
import java.util.List;

public class MemberBOImpl implements MemberBO {
    MemberDAO memberDAO = new MemberDAOImpl();

    @Override
    public boolean saveMember(MemberDTO dto) {
        Member m = new Member();
        m.setMemberId(dto.getMemberId());
        m.setFullName(dto.getFullName());
        m.setEmail(dto.getEmail());
        m.setPhone(dto.getPhone());
        m.setAddress(dto.getAddress());
        m.setCreatedAt(dto.getCreatedAt());
        m.setUpdatedAt(dto.getUpdatedAt());
        return memberDAO.save(m);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        List<MemberDTO> list = new ArrayList<>();
        for (Member m : memberDAO.getAll()) {
            list.add(new MemberDTO(m.getMemberId(), m.getFullName(), m.getEmail(), m.getPhone(), m.getAddress(), m.getCreatedAt(), m.getUpdatedAt()));
        }
        return list;
    }

    @Override
    public boolean updateMember(MemberDTO dto) {
        Member m = new Member();
        m.setMemberId(dto.getMemberId());
        m.setFullName(dto.getFullName());
        m.setEmail(dto.getEmail());
        m.setPhone(dto.getPhone());
        m.setAddress(dto.getAddress());
        m.setCreatedAt(dto.getCreatedAt());
        m.setUpdatedAt(dto.getUpdatedAt());
        return memberDAO.update(m);
    }

    @Override
    public boolean deleteMember(String memberId) {
        return memberDAO.delete(memberId);
    }
}