package oit.is.z0321.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchMapper {

  // @Select("SELECT id,name from users where id = #{id}")
  // User selectById(int id);

  @Insert("INSERT INTO matches (user1, user2, user1Hand, user2Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{user2Hand}, #{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatches(Match match);

  @Select("SELECT * from matches;")
  ArrayList<Match> selectAllMatches();

  @Select("SELECT * from matches where id = #{id};")
  Match getById(int id);

  @Select("UPDATE matches SET isActive = false WHERE id = #{id};")
  void updateById(int id);

}
