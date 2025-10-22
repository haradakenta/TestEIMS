package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.MyUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * 各メソッドがセキュリティ関連の設定を提供。パスワードのハッシュ化、アクセス制御、ログインページの設定、ログアウトの設定など
 */

@Configuration //設定ファイルと定義
@EnableWebSecurity //カスタムWebセキュリティを有効化
@RequiredArgsConstructor //必須フィールド（final付き）のコンストラクタを自動生成
public class SecurityConfig { //セキュリティ設定の内容
	
    //finalフィールドで宣言
	private final MyUserDetailsService myUserDetailsService;
    
	//  HttpSecurity を利用して、認証・認可の設定 を行う
    @Bean //このメソッドの返り値をSpringのBeanとして登録
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {//セキュリティフィルタチェーンを定義するメソッド
        http
        	.authenticationManager(authenticationManager(http)) //認証マネージャーの設定
            .authorizeHttpRequests(auth -> auth // 認証リクエストを設定します
                .requestMatchers("/logins", "/register", "/css/**", "/js/**").permitAll() //ログインページなどは誰でも見れる
                .anyRequest().authenticated() //その他はログイン必須
                
            )
            .formLogin(form -> form
                .loginPage("/logins")           //ログインページ
                .loginProcessingUrl("/logins")    //フォームの送信先
                .usernameParameter("staffcode") //社員番号でユーザー名として認証
                .passwordParameter("password")  //パスワード認証
                .defaultSuccessUrl("/employee/list", true)  //ログイン認証後、社員一覧画面へ
                .failureUrl("/logins?error=true")            //ログイン失敗後//ログイン失敗時にエラー付きで戻る予定（これを JSで拾ってポップアップ出せる）
                .permitAll()                    //  ログインフォーム画面は認証不要
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")     //ログアウト後はログインイン画面へ
                .invalidateHttpSession(true) //ログアウト時にセッションを削除
                .deleteCookies("JSESSIONID") //セッションIDのCookieを削除して完全ログアウト
            );

        return http.build();
    } 

    //パスワードをハッシュ化するエンコーダ(DB保存時にも使う)。ハッシュ化されたDBの値と一致するかチェック
    //AuthenticationManagerはログイン時の認証処理を管理するクラス
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception { 
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class); //ログインに必要な設定をまとめるためのビルダー
        builder.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder()); //社員情報をDB取得、パスワードをBCryptでハッシュチェック
        return builder.build(); //AuthenticationManagerを完成させて返す。SpringSecurityに適用
    }

    //Bcryptアルゴリズムでハッシュ化を行うエンコーダだけを返す
    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //
    }
}
