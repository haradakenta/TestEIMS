package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//これはパスワード確認用です。設定したパスワードがハッシュ化されるとどうなるのかを確認
public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "test1234";  //設定したパスワード
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("ハッシュ化されたパスワード: " + encodedPassword); //ハッシュ化
    }
}
