package com.green.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.board.dao.BoardDao;
import com.green.board.domain.BoardDto;
import com.green.board.domain.SearchCondition;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDao boardDao;
	
	@Override
	public int getCount() throws Exception {
		return boardDao.count();
	}
	
	@Override
	public int write(BoardDto dto) throws Exception{
		return boardDao.insert(dto);
		//throw new Exception("write test");
	}
	
	@Override
	public int modify(BoardDto dto) throws Exception{
		return boardDao.update(dto);
	}
	
	@Override
	public int remove(Integer bno, String writer) throws Exception{
		return boardDao.delete(bno, writer);
	}
	
	@Override
	public int removeAll() throws Exception {
		return boardDao.deleteAll();
	}
	
	@Override
	public List<BoardDto> getList() throws Exception{
		return boardDao.selectAll();
	}
	
//	deleteForAdmin은 지금 구현 안할거라서 생략
	
	//조회수 증가
	@Override
	public BoardDto read(int bno) throws Exception{
		BoardDto boardDto = boardDao.select(bno);
		boardDao.increaseViewCnt(bno);
		
		return boardDto;
	}
	
	@Override
	public List<BoardDto> getPage(Map map) throws Exception{
		return boardDao.selectPage(map);
	}

	@Override
	public List<BoardDto> getSearchSelectPage(SearchCondition sc) throws Exception {
		return boardDao.searchSelectPage(sc);
	}

	@Override
	public int getSearchResultCnt(SearchCondition sc) throws Exception {
		return boardDao.searchResultCnt(sc);
	}
}
