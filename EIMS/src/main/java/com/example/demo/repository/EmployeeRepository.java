package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Employee;

//データベースから 社員情報 (Employee) を取得するためのリポジトリを定義
//JpaRepository<Employee, Integer> を継承して、データベースにデータ操作可能
public interface EmployeeRepository extends JpaRepository<Employee, Integer> { 

	//DB操作のカスタムクエリ発行で論理削除 (STATUS=0) のデータのみ取得
    @Query("SELECT e FROM Employee e WHERE e.id IN (SELECT s.id FROM Shows s WHERE s.status = '0')")
    List<Employee> findAllVisibleEmployees();

    
    // 社員番号からEmployeeを取得する（直接Employeeを返す）
    //Optional<Employee> を返すことで、検索結果が存在しない場合も対応できる
    Optional<Employee> findByStaffcode(Integer staffcode);
}
