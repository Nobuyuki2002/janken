package oit.is.z0321.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import oit.is.z0321.kaizi.janken.model.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0321.kaizi.janken.model.Janken;
import oit.is.z0321.kaizi.janken.model.Match;
import oit.is.z0321.kaizi.janken.model.MatchMapper;
import oit.is.z0321.kaizi.janken.model.Matchinfo;
import oit.is.z0321.kaizi.janken.model.MatchinfoMapper;
import oit.is.z0321.kaizi.janken.model.User;
import oit.is.z0321.kaizi.janken.model.UserMapper;
import oit.is.z0321.kaizi.janken.service.AsyncKekka;

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

  @Autowired
  MatchinfoMapper matchinfoMapper;

  @Autowired
  AsyncKekka kekka;

  /** */
  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    ArrayList<User> users_info = userMapper.selectAllUserName();
    ArrayList<Match> matches_info = matchMapper.selectAllMatches();
    ArrayList<Matchinfo> active_matches_info = matchinfoMapper
        .selectActiveMatchInfos((userMapper.selectByName(loginUser)).getId());

    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("user_name", loginUser);

    model.addAttribute("user_info", users_info);
    model.addAttribute("matches_info", matches_info);
    model.addAttribute("active_matches", active_matches_info);
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
  @Transactional
  public String janken(Principal prin, @RequestParam Integer id, @RequestParam Integer hand, ModelMap model) {
    Janken janken = new Janken(hand);
    User match_user = userMapper.selectById(id);
    User loginUser = userMapper.selectByName(prin.getName());
    Matchinfo getMatch = matchinfoMapper.selectActiveThisMatchInfo(match_user.getId(), loginUser.getId());
    Match thisMatch;
    if (getMatch == null) {
      getMatch = matchinfoMapper.selectActiveThisMatchInfo(loginUser.getId(), match_user.getId());
      if (getMatch == null) {
        Matchinfo match_info = new Matchinfo(loginUser.getId(), match_user.getId(), janken.getMyhand(), true);
        matchinfoMapper.insertMatchInfo(match_info);
      }
    } else {
      thisMatch = new Match(loginUser.getId(), match_user.getId(), janken.getMyhand(), getMatch.getUser1Hand(), true);
      matchMapper.insertMatches(thisMatch);

      this.kekka.syncUpdateMatchinfo(getMatch.getId());

      kekka.syncSetId(thisMatch.getId());
    }

    model.addAttribute("user_name", loginUser.getName());

    return "wait.html";
  }

  @GetMapping("/result")
  public SseEmitter result() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.kekka.syncShowMatchresult(sseEmitter);
    return sseEmitter;
  }
}
