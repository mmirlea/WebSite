package com.green.board.dao;

import java.util.List;
import java.util.Map;

import com.green.board.domain.BoardDto;
import com.green.board.domain.SearchCondition;

public interface BoardDao {

	BoardDto select(int bno) throws Exception;

	int count() throws Exception;

	int insert(BoardDto dto) throws Exception;

	int update(BoardDto dto) throws Exception;

	int delete(Integer bno, String writer) throws Exception;

	int deleteForAdmin(Integer bno) throws Exception;

	int deleteAll() throws Exception;

	List<BoardDto> selectAll() throws Exception;

	int increaseViewCnt(Integer bno) throws Exception;
	
	List<BoardDto> selectPage(Map map) throws Exception;
	
	public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception; 
	
	int searchResultCnt(SearchCondition sc) throws Exception;

	int updateCommentsCnt(Integer bno, int cnt);
}