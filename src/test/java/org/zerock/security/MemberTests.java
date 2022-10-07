package org.zerock.security;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/security-context.xml"})
@Log4j
public class MemberTests {
    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder pwencoder;

    @Setter(onMethod_ = @Autowired)
    private DataSource ds;

    @Test
    public void testInsertMember() {
        String sql = "insert into tbl_member(userid, userpw, username) values (?,?,?)";

        for(int i=0; i<100; i++) {
            Connection con = null;
            PreparedStatement pStmt = null;

            try {
                con = ds.getConnection();
                pStmt = con.prepareStatement(sql);

                pStmt.setString(2, pwencoder.encode("pw" + i));

                if(i < 80) {

                    pStmt.setString(1, "user" + i);
                    pStmt.setString(3, "일반사용자" + i);

                } else if(i < 90) {

                    pStmt.setString(1, "manager" + i);
                    pStmt.setString(3, "운영자" + i);

                } else {

                    pStmt.setString(1, "admin" + i);
                    pStmt.setString(3, "관리자" + i);

                }
                pStmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(pStmt != null) {
                    try {
                        pStmt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }   // end for
    }

    @Test
    public void testInsertAuth() {
        String sql = "insert into tbl_member_auth (userid, auth) values (?,?)";

        for(int i=0; i<100; i++) {

            Connection con = null;
            PreparedStatement pStmt = null;

            try {
                con = ds.getConnection();
                pStmt = con.prepareStatement(sql);

                if (i < 80) {

                    pStmt.setString(1, "user" + i);
                    pStmt.setString(2, "ROLE_USER");

                } else if (i < 90) {

                    pStmt.setString(1, "manager" + i);
                    pStmt.setString(2, "ROLE_MEMBER");

                } else {

                    pStmt.setString(1, "admin" + i);
                    pStmt.setString(2, "ROLE_ADMIN");

                }
                pStmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (pStmt != null) {
                    try {
                        pStmt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
