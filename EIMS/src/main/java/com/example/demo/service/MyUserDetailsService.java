package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

/**
 * Spring Security用のUserDetalsService
 * ログインフォームから送られた社員番号(staffcode)を受け取り、ユーザー情報を取得、認証処理に渡す
 */

//社員番号をキーとして社員番号を使ったユーザー検索を実装
@Service
public class MyUserDetailsService implements UserDetailsService{
	
	
	@Autowired // EmployeeRepositoryを実装注入し、データベース操作を可能に
	private EmployeeRepository employeeRepository;
	
	/**
	 * 社員番号(staffcode)からユーザー情報を探し、Spring Security用に変換して返す
	 * 
	 * @param staffcode ログインフォームから送られる「社員番号」
	 */
	//ログインフォームからはstaffcodeがString(文字列)で受け取る
	@Override
	//数値に変換できない場合は UsernameNotFoundException をスローしてエラーハンドリング
	public UserDetails loadUserByUsername(String staffcodeString) throws UsernameNotFoundException {
		//社員番号の変換(String→Integer)
		Integer staffcode;
		try {
			staffcode = Integer.parseInt(staffcodeString); //Integer.parseInt()で数字に変換してからDB検索
		} catch(NumberFormatException e) { //不正な社員番号(文字が含まれているなど)なら、即座に認証を拒否
			System.out.println("社員番号が数字じゃない!");
			throw new UsernameNotFoundException("社員番号が無効です: " + staffcodeString);
		}
		
		Employee employee = employeeRepository.findByStaffcode(staffcode).orElse(null); //DBから社員情報を検索
				
		if(employee == null) {
			System.out.println("社員番号がDBに存在しない");
			throw new UsernameNotFoundException("社員番号が見つかりません:" + staffcode); //該当する社員が見つからない場合は UsernameNotFoundException をスロー

		}
		
		//パスワード情報(Usersテーブル)のチェック
		if(employee.getUsers() == null) {
			throw new UsernameNotFoundException("パスワード情報がありません"); //認証時に Users が null の場合はエラー (UsernameNotFoundException) をスロー
		}
		
		//ハッシュ済みのパスワードの取得
		String rawPassword = employee.getUsers().getPassword(); // DBから取得したハッシュ済みパスワード
        System.out.println("DB登録のパスワードハッシュ: " + rawPassword); //デバッグ用

        // MyUserDetailsを生成して、Spring Security に認証情報として提供
        return new MyUserDetails(employee, rawPassword);
		

	}

}
