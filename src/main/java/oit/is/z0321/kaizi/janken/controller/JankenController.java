package oit.is.z0321.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0321.kaizi.janken.model.Janken;

/**
 * JankenController
 *
 * @author 田渕宣行
 */
@Controller
@RequestMapping("/janken")
public class JankenController {

  /**
   * index.htmlでPOST受付/jankenへ遷移する
   *
   * @param user
   * @param model
   * @return
   */
  @PostMapping
  public String janken(@RequestParam String user, ModelMap model) {
    model.addAttribute("user_name", user);
    return "janken.html";
  }

  /**
   * Getでアクセスされた場合
   *
   * @return
   */
  @GetMapping
  public String janken() {
    return "janken.html";
  }

  /**
   * jankenの結果を返す
   *
   * @param hand
   * @param model
   * @return
   */
  @GetMapping("/game")
  public String janken(@RequestParam Integer hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("my_hand", janken.getMyhand());
    model.addAttribute("cpu_hand", janken.getCpuhand());
    model.addAttribute("result", janken.Result());

    return "janken.html";
  }
}
