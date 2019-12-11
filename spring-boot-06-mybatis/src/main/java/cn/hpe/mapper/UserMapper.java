package cn.hpe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.hpe.pojo.User;

@Mapper
public interface UserMapper {
	
	@Select("select * from tb_user where user_name like '%${name}%'")
	public List<User> queryByName(@Param("name") String name);
	
	
	public List<User> queryAll();
	
	

}
