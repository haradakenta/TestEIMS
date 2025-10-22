package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Users;

/**
 * UsersRepositoryインターフェイス
 * ユーザー(パスワード情報)をデータベースから取得・保存するためのインターフェイス
 */

public interface UsersRepository extends JpaRepository<Users, Integer> {

}
