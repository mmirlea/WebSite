package com.green.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.board.domain.CommentsDto; 

@Repository
public class CommentsDaoImpl implements CommentsDao {
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.green.board.dao.CommentsMapper.";
	
	@Override
	public int deleteAll(Integer bno) throws Exception{
		return session.delete(namespace + "deleteAll", bno);
	}
	
	@Override
	public int count(Integer bno) throws Exception{
		return session.selectOne(namespace + "count", bno);
	}
	
	@Override
	public int delete(Integer cno, String commenter) throws Exception{
		Map map = new HashMap();
		map.put("cno", cno);
		map.put("commenter", commenter);
		return session.delete(namespace + "delete", map);
	}
	
	@Override
	public int insert(CommentsDto dto) throws Exception{
		return session.insert(namespace + "insert", dto);
	}
	
	@Override
	public List<CommentsDto> selectAll(Integer bno) throws Exception{
		return session.selectList(namespace + "selectAll", bno);
	}
	
	@Override
	public CommentsDto select(Integer cno) throws Exception{
		return session.selectOne(namespace + "select", cno);
	}
	
	@Override
	public int update(CommentsDto dto) throws Exception{
		return session.update(namespace + "update", dto);
	}
}
