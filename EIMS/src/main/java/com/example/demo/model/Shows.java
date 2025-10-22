package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter //getメソッドを自動生成
@Setter //getメソッドを自動生成
@Table(name = "Shows") //このクラスが対応するテーブル名
@Entity
public class Shows {
    @Id
    private int id;

    private String status;

}