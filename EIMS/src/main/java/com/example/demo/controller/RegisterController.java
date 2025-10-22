package com.example.demo.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.model.Shows;
import com.example.demo.model.Users;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ShowRepository;
import com.example.demo.repository.UsersRepository;

/**
 * 新規社員登録の処理するコントローラ
 */

@Controller
public class RegisterController {

	//依存性の注入　Springが自動で適切なオブジェクトを注入
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ShowRepository showRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    //登録画面表示
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("employee", new Employee()); //新規登録用のオブジェクトを作成し、Thymeleafへ渡す
        return "register";
    }

    //DB接続するために必要な設定 EntityManager は JPA を使用してデータベースを直接操作 するためのオブジェクト
    @PersistenceContext
    private EntityManager entityManager; //EntityManager は JPA を使用してデータベースを直接操作 するためのオブジェクト
    
    @Transactional //すべての処理がトランザクション内で実行され、一括管理される.エラーが発生した場合は ロールバック（処理を取り消す） できる
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Employee employee, // フォームデータを Employeeで自動マッピング
                               @RequestParam String password, Model model) {//パスワードを個別に受け取る
    	
    	
        // 重複チェック（staffcodeがすでに存在するか）
        if (employeeRepository.findByStaffcode(employee.getStaffcode()).isPresent()) {// 該当する社員番号がDBにあるか確認
            model.addAttribute("errorMessage", "この社員番号はすでに登録されています。"); //エラーが出た場合のエラーメッセージ
            return "register";
        }
        
        // 1.社員情報(employee)を保存 employee.idが自動生成される
        employeeRepository.save(employee);

        //後の処理で employee.id を Users や Shows に利用する
        // 2.USERSにemployeeをセットし、パスワードを設定
        Users user = new Users();
        user.setEmployee(employee);		//MapsIdでIDが連動(USERとEmployeeの関連付け)
        user.setPassword(passwordEncoder.encode(password)); //パスワードをハッシュ化
        usersRepository.save(user); //認証情報をデータベースに保存

        // 3.SHOWSにIDとSTATUSをセット
        Shows show = new Shows();
        show.setId(employee.getId());		//EmployeeのIDを使用
        show.setStatus("0");  //新規登録時は表示（0）に設定
        showRepository.save(show); //データベースに保存

        return "redirect:/logins";
    }
}
