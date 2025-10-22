package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity //データベースのテーブルを表す定義
@Table(name = "Employee") //このクラスが対応するテーブル名
public class Employee {

    @Id //primarykeyで各ユーザを一意に識別するためのIDとなります
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDは自動的に増加します
    @Column(name = "id") //データベースに沿って記入
    private Integer id;

    @Column(name = "staffcode", nullable = false, unique = true) //staffcodeカラムの内容
    private Integer staffcode;

    @Column(name = "ename", nullable = false)
    private String ename;

    @Column(name = "position", nullable = false)
    private String position;
    
    // ゼロ埋めした社員番号（データベースには保存しない）
    @Transient // EmployeeServiceで定義
    private String formattedStaffCode;


    //以下は各値を取得するためのメソッド（ゲッター）です。 各カラムを返す
    public Integer getId() {
        return this.id;
    }

    public Integer getStaffcode() {
        return this.staffcode;
    } 

    public String getEname() {
        return this.ename;
    }

    public String getPosition() {
        return this.position;
    }
    
    public String getFormattedStaffCode() {
        return this.formattedStaffCode;
    }


    //以下は各値を取得するためのメソッド（セッター）です。各カラムを設定
    public void setId(Integer id) {
        this.id = id;
    }

    public void setStaffcode(Integer staffcode) {
        this.staffcode = staffcode;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setFormattedStaffCode(String formattedStaffCode) {
        this.formattedStaffCode = formattedStaffCode;
    }

    //1対1の関連テーブル(Users)を紐づけ
    @OneToOne(mappedBy = "employee") //employeeテーブルがオーナー　双方向関連
    private Users users;
    
    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
