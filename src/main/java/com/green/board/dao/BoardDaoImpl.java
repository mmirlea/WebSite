package com.green.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.board.domain.BoardDto;
import com.green.board.domain.SearchCondition;

@Repository
public class BoardDaoImpl implements BoardDao {
	
	@Autowired
	SqlSession session;
	
	String namespace = "com.green.board.dao.BoardMapper.";
	
	@Override
	public BoardDto select(int bno) throws Exception{
		return session.selectOne(namespace + "select", bno);
	}
	  
	@Override
	public int count() throws Exception{
		return session.selectOne(namespace + "count");
	}
	
	@Override
	public int insert(BoardDto dto) throws Exception{
		return session.insert(namespace + "insert", dto);
	}
	
	@Override
	public int update(BoardDto dto) throws Exception{
		return session.update(namespace + "update", dto);
	}
	
	@Override
	public int delete(Integer bno, String writer) throws Exception{
		Map map = new HashMap();
		map.put("bno", bno);
		map.put("writer", writer);
		return session.delete(namespace + "delete", map);
	}
	
	@Override
	public int deleteForAdmin(Integer bno) throws Exception{
		return session.delete(namespace + "deleteForAdmin", bno);
	}
	
	@Override
	public int deleteAll() throws Exception{
		return session.delete(namespace + "deleteAll");
	}
	
	@Override
	public List<BoardDto> selectAll() throws Exception{
		return session.selectList(namespace + "selectAll");
	}
	
	@Override
	public int increaseViewCnt(Integer bno) throws Exception{
		return session.update(namespace + "increaseViewCnt", bno);
	}
	
	@Override
	public List<BoardDto> selectPage(Map map) throws Exception{
		return session.selectList(namespace + "selectPage", map);
	}

	@Override
	public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
		return session.selectList(namespace + "searchSelectPage", sc);
	}

	@Override
	public int searchResultCnt(SearchCondition sc) throws Exception {
		return session.selectOne(namespace + "searchResultCnt", sc);
	}

	@Override
	public int updateCommentsCnt(Integer bno, int cnt) {
		Map map = new HashMap();
		map.put("cnt", cnt);
		map.put("bno", bno);
		return session.update(namespace + "updateCommentsCnt", map);
	}
	
}
