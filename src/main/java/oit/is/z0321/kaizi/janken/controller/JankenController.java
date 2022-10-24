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
import oit.is.z0321.kaizi.janken.model.Match;
import oit.is.z0321.kaizi.janken.model.MatchMapper;
import oit.is.z0321.kaizi.janken.model.User;
import oit.is.z0321.kaizi.janken.model.UserMapper;

/**
 * JankenController
 *
 * @author 田渕宣行
 */
@Controller
public class JankenController {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  /** */
  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    ArrayList<User> users_info = userMapper.selectAllUserName();
    ArrayList<Match> matches_info = matchMapper.selectAllMatches();

    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("user_name", loginUser);

    model.addAttribute("user_info", users_info);
    model.addAttribute("matches_info", matches_info);
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
  @GetMapping("/match")
  public String janken(Principal prin, @RequestParam Integer id, ModelMap model) {
    String loginUser = prin.getName();
    User match_user = userMapper.selectById(id);
    model.addAttribute("user_id", id);
    model.addAttribute("user_name", loginUser);

    model.addAttribute("match_user", match_user.getName());

    return "match.html";
  }

  /**
   * jankenの結果を返す
   *
   * @param hand
   * @param model
   * @return
   */
  @GetMapping("/fight")
  public String janken(Principal prin, @RequestParam Integer id, @RequestParam Integer hand, ModelMap model) {
    Janken janken = new Janken(hand);
    User match_user = userMapper.selectById(id);
    User loginUser = userMapper.selectByName(prin.getName());

    Match match_data = new Match(loginUser.getId(), match_user.getId(), janken.getMyhand(), janken.getCpuhand());
    matchMapper.insertMatches(match_data);

    model.addAttribute("user_id", id);
    model.addAttribute("user_name", loginUser.getName());
    model.addAttribute("match_user", match_user.getName());

    model.addAttribute("my_hand", janken.getMyhand());
    model.addAttribute("cpu_hand", janken.getCpuhand());
    model.addAttribute("result", janken.Result());

    return "match.html";
  }
}
