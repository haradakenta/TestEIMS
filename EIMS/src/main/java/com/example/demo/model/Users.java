package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Users {

    @Id //primarykeyで各ユーザを一意に識別するためのIDとなります
    @Column(name = "id") //データベースに沿って記入
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id") // // usersテーブル→employeeテーブル。employee_idに沿って作成するためにjoin(結合)する// FK
    private Employee employee;
    
    private String password;

    // --- Getter / Setter ---
    public Integer getId() { return this.id;}
    public void setId(Integer id) { this.id = id;}

    public String getPassword() { return this.password;}
    public void setPassword(String password) { this.password = password;}

    }
