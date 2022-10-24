package oit.is.z0321.kaizi.janken.model;

public class User {
  public String name;

  // Thymeleafでフィールドを扱うためにはgetter/setterが必ず必要
  // vscodeのソースコード右クリック->ソースアクションでsetter/getterを簡単に追加できる
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}