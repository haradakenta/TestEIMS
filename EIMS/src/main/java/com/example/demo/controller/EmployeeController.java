package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.model.Shows;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ShowRepository;

@Controller //Webリクエスト(HTMLページを表示)を処理することをSpringに伝える
public class EmployeeController {
	
    @Autowired //インスタンスを自動的に作成してEmployeeService に注入
    private EmployeeRepository employeeRepository;

    @Autowired //インスタンスを自動的に作成してEmployeeService に注入
    private ShowRepository showRepository;

    // 新規登録画面表示
    @GetMapping("/employee/register") //GETリクエストを受け取り、新規登録ページを返す(リソース取得)
    public String registerForm() {
        return "register"; // resources/templates/register.html を表示する
    }
    //社員一覧画面
    @GetMapping("/employee/list")
    public String list(Model model) {
        List<Employee> employees = employeeRepository.findAll().stream()
            .filter(e -> {
                Shows show = showRepository.findById(e.getId()).orElse(null);
                return show != null && "0".equals(show.getStatus());
            })
            .peek(e -> e.setFormattedStaffCode(String.format("%03d", e.getStaffcode()))) // ★ゼロ埋め適用
            .collect(Collectors.toList());

        model.addAttribute("employees", employees);
        return "employee/list";
    }
    
    //更新ボタンの処理
    @PostMapping("/employee/update") //データ送信・登録
    public String updateEmployee(@RequestParam("id") Integer id, //フォームデータを受け取りでIDデータ更新
                                 @RequestParam("ename") String ename,
                                 @RequestParam("position") String position) {
        // IDを元に社員情報を取得
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee != null) {
            // 名前と役職を更新
            employee.setEname(ename);
            employee.setPosition(position);
            employeeRepository.save(employee);
        }

        // 更新後、社員一覧画面へリダイレクト
        return "redirect:/employee/list";
    }
    
    //削除ボタンの処理
    @PostMapping("/employee/delete")
    public String deleteEmployee(@RequestParam("id") Integer id) {
        // 該当社員の "SHOWS" テーブル情報を取得
        Shows show = showRepository.findById(id).orElse(null);

        if (show != null) {
            // STATUS を "1" に変更（論理削除）
            show.setStatus("1");
            showRepository.save(show);
        }

        // 社員一覧画面へリダイレクト
        return "redirect:/employee/list";
    }
    
    
}
