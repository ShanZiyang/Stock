package com.ys.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 杨森
 * @description:
 * @date 2023年06月15日 21:59
 */
@SpringBootTest
public class TestPasswordEncoder {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void testPwd(){
        String pwd="123456";
        //加密  $2a$10$WAWV.QEykot8sHQi6FqqDOAnevkluOZJqZJ5YPxSnVVWqvuhx88Ha
        String encode = passwordEncoder.encode(pwd);
        String encode2 = passwordEncoder.encode(pwd);
        System.out.println(encode);
        System.out.println(encode2);
        String encode3 = "$2a$10$R2B8mM9/PRrEGFGEsF3KN.RuD4XY2OgPdaqD0Z8g5K8hk0J0QXbum";
        /*
            matches()匹配明文密码和加密后密码是否匹配，如果匹配，返回true，否则false
            just test
         */
//        boolean flag = passwordEncoder.matches(pwd,"$2a$10$WAWV.QEykot8sHQi6FqqDOAnevkluOZJqZJ5YPxSnVVWqvuhx88Ha");
//        System.out.println(flag);
        boolean r1 = passwordEncoder.matches(pwd,encode);
        System.out.println(r1);
        boolean r2 = passwordEncoder.matches(pwd, encode2);
        System.out.println(r2);
        boolean r3 = passwordEncoder.matches(pwd, encode3);
        System.out.println(r3);
    }
}
