package org.zerock.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.zerock.domain.MemberVO;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomUser extends User {
    private static final long serialVersionUID = 1L;

    @Setter(onMethod_ = @Autowired)
    private MemberVO member;

    // 부모클래스의 생성자
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    // MemberVO를 파라미터로 전달해서 User 클래스에 맞게 처리하는 생성자
    // AuthVO 인스턴스는 GrantedAuthority 객체로 변환
    public CustomUser(MemberVO vo) {
        super(vo.getUserid(), vo.getUserpw(), vo.getAuthList().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
                .collect(Collectors.toList()));

        this.member = vo;
    }
}
