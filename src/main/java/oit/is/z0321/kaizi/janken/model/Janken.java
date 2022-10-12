package oit.is.z0321.kaizi.janken.model;

import java.util.Random;

public class Janken {
  private int Hand = 1;
  private int CpuHand = 1;

  public Janken(int hand) {
    this.Hand = hand;
    this.setCpuHand(); // CPUの手を初期化する
  }

  public String Result() {
    // TODO:CPUの手をランダムにする(現在はぐー固定)
    if (this.Hand == this.CpuHand) {
      return "あいこ";
    }

    switch (this.Hand) {
      case 1:// GU
        switch (this.CpuHand) {
          case 2:// CH
            return "かち";
          case 3:// PA
            return "まけ";
        }
      case 2:// CH
        switch (this.CpuHand) {
          case 1:// GU
            return "まけ";
          case 3:// PA
            return "かち";
        }
      case 3:// PA
        switch (this.CpuHand) {
          case 1:// GU
            return "かち";
          case 2:// CH
            return "まけ";
        }
    }
    return "";
  }

  public void setCpuHand() {
    Random rand = new Random();

    this.CpuHand = rand.nextInt(3) + 1;
  }

  /**
   * 自分の手を取得
   *
   * @return
   */
  public String getMyhand() {
    switch (this.Hand) {
      case 1:
        return "ぐー";
      case 2:
        return "ちょき";
      case 3:
        return "ぱー";
    }
    return "";
  }

  /**
   * CPUの手を取得
   *
   * @return
   */
  public String getCpuhand() {
    switch (this.CpuHand) {
      case 1:
        return "ぐー";
      case 2:
        return "ちょき";
      case 3:
        return "ぱー";
    }
    return "";
  }
}
