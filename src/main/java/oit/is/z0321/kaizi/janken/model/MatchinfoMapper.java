package oit.is.z0321.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchinfoMapper {

  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(Matchinfo match_info);

  @Select("SELECT * from matchinfo;")
  ArrayList<Matchinfo> selectAllMatchInfos();

  @Select("SELECT * from matchinfo where isActive = true and ( user1 = #{user} or user2 = #{user} );")
  ArrayList<Matchinfo> selectActiveMatchInfos(int user);
}
