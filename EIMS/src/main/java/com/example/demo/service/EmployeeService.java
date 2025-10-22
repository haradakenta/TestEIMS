package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;


//EmployeeServiceクラス
//社員情報を管理するサービスクラスで、データ取得・フォーマット変換・パスワード情報の表示
@Service //Spring がこのクラスをサービスとして管理。ビジネスロジックを実行する
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    
    /**
     * 社員番号からEmployeeを検索する
     * 
     * @param sttaffcode 社員番号
     * @return Employee(見つからない場合はnull)
     */

    public Employee findEmployeeByStaffcode(Integer staffcode) {
        return employeeRepository.findByStaffcode(staffcode).orElse(null);
    }
    
    /**
     * 社員一覧を取得＆社員番号をゼロ埋め処理
     * 
     * @return List<Employee> ゼロ埋め後の社員情報一覧
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll(); //DBからEmployeeの一覧を取得

        // ゼロ埋め処理の追加。ゼロ埋めされた社員番号をFormattedStaffCode二セット
        employees.forEach(employee -> {
            employee.setFormattedStaffCode(String.format("%03d", employee.getStaffcode())); 
        });

        return employees;
    }
    
    /**
     * 社員番号から社員情報とパスワードの表示
     * 
     * @param staffcode 社員番号
     */
    public void showEmloyeeWithPassword(Integer staffcode) { //社員番号(staffcode)をキーにEmployeeを取得
    	Employee employee = employeeRepository.findByStaffcode(staffcode).orElse(null);
    	
    	//デバック用
    	if(employee != null) {
    		System.out.println("名前:  "); employee.getEname();
    		if(employee.getUsers() != null) {
    			System.out.println("パスワードが:  " + employee.getUsers().getPassword());
    		} else {
    			System.out.println("パスワード情報が存在しません");
    		}
    	} else {
    		System.out.println("社員情報が見つかりません");
    	}
    }

    
}