package oit.is.z0321.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import oit.is.z0321.kaizi.janken.model.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0321.kaizi.janken.model.Janken;
import oit.is.z0321.kaizi.janken.model.User;
import oit.is.z0321.kaizi.janken.model.UserMapper;

/**
 * JankenController
 *
 * @author 田渕宣行
 */
@Controller
@RequestMapping("/janken")
public class JankenController {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;

  /** */
  @GetMapping
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    ArrayList<User> users_info = userMapper.selectAllUserName();

    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("user_name", loginUser);

    model.addAttribute("user_info", users_info);
    return "janken.html";
  }

  // /**
  // * Getでアクセスされた場合
  // *
  // * @return
  // */
  // @GetMapping
  // public String janken() {
  // return "janken.html";
  // }

  /**
   * jankenの結果を返す
   *
   * @param hand
   * @param model
   * @return
   */
  @GetMapping("game")
  public String janken(@RequestParam Integer hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("my_hand", janken.getMyhand());
    model.addAttribute("cpu_hand", janken.getCpuhand());
    model.addAttribute("result", janken.Result());

    return "janken.html";
  }
}
