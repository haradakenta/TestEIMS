package com.example.demo.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.Employee;



/**
 * Spring Security のログイン認証に使う「ユーザー情報」 を管理
 *ログイン認証後、認証済みユーザー情報として保持
 */

//Spring Securityの UserDetails を実装 することで、認証時に必要なユーザー情報（社員情報・パスワードなど）を提供
public class MyUserDetails implements UserDetails {
    private final Employee employee;    //Employee情報(社員情報・名前・役職)
    private final String password;      //パスワード情報

    //コンストラクタ。認証データをセット
    public MyUserDetails(Employee employee, String password) {
        this.employee = employee;
        this.password = password;
    }

    //ログインユーザーが持つ権限を返す
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); //ここでは権限を設定していないため、emptyList() を返している
    }

    //認証時に比較する「パスワード」を返す
    @Override
    public String getPassword() { return password; }


    //認証時に比較する「ユーザー名」を返す
    @Override
    public String getUsername() { return employee.getStaffcode().toString().trim(); }

    //社員名(ename)を取り出すメソッドも念の為、追加
    //@Override
    public String getEname() {  return employee.getEname(); }
    
    //アカウントの状態チェック
    //アカウントが有効期限でないことを示すために常にtrueを返す
    @Override public boolean isAccountNonExpired() { return true;}
    //アカウントがロックされていないこと
    @Override public boolean isAccountNonLocked() { return true;}
    //資格情報が有効期限切れでないこと
    @Override public boolean isCredentialsNonExpired() { return true;}
    //アカウントが有効であること
    @Override public boolean isEnabled() { return true;}

}
