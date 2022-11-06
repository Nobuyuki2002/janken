package oit.is.z0321.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0321.kaizi.janken.model.Match;
import oit.is.z0321.kaizi.janken.model.MatchMapper;
import oit.is.z0321.kaizi.janken.model.Matchinfo;
import oit.is.z0321.kaizi.janken.model.MatchinfoMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;
  int id;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper mMapper;

  @Autowired
  MatchinfoMapper minfoMapper;

  @Transactional
  public void syncUpdateMatchinfo(int id) {
    // 更新
    minfoMapper.updateById(id);
  }

  public void syncSetId(int id) {
    this.id = id;
    // 非同期でDB更新したことを共有する際に利用する
    this.dbUpdated = true;
  }

  @Async
  public void syncShowMatchresult(SseEmitter emitter) {
    dbUpdated = true;

    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        Match thisMatch = mMapper.getById(this.id);
        emitter.send(thisMatch);
        mMapper.updateById(this.id);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }

}
