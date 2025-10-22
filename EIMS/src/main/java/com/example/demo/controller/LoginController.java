package com.example.demo.controller;

/**
 * ログイン認証画面でのHTTPリクエストを処理するためのクラス（コントローラ）
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //このクラスがwebコントローラー
public class LoginController {
	
	@GetMapping("/logins") //"logins"というURLに対するGET(取得)する
	public String login(@RequestParam(value = "error", required = false) String error, Model model){
		if(error != null) model.addAttribute("error", true);
		return "logins";		//logins.htmlを表示する
	}

}
