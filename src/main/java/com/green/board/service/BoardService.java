package com.green.board.service;

import java.util.List;
import java.util.Map;

import com.green.board.domain.BoardDto;
import com.green.board.domain.SearchCondition;

public interface BoardService {

	int getCount() throws Exception;

	int write(BoardDto dto) throws Exception;

	int modify(BoardDto dto) throws Exception;

	int remove(Integer bno, String writer) throws Exception;

	int removeAll() throws Exception;

	List<BoardDto> getList() throws Exception;

	//조회수 증가
	BoardDto read(int bno) throws Exception;

	List<BoardDto> getPage(Map map) throws Exception;
	
	List<BoardDto> getSearchSelectPage(SearchCondition sc) throws Exception;
	
	int getSearchResultCnt(SearchCondition sc) throws Exception;

}