package oit.is.z0321.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT id,name from users where id = #{id}")
  User selectById(int id);

  @Select("SELECT id,name from users where name = #{name}")
  User selectByName(String name);

  // @Insert("INSERT INTO users (name) VALUES (#{name},#{name});")
  // @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  // void insertUser(User user);
  @Select("SELECT id, name from users;")
  ArrayList<User> selectAllUserName();

}
