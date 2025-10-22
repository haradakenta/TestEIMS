package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Shows;

/**
 * ShowRepositoryインターフェイス
 * 論理削除用のテーブル(shows)をデータベースから取得・保存するためのインターフェイス
 */
public interface ShowRepository extends JpaRepository<Shows, Integer>{
	@Query("SELECT s FROM Shows s WHERE s.status = '0'")
	List<Shows> findAllVisible();

}
