package com.green.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.board.dao.BoardDao;
import com.green.board.dao.CommentsDao;
import com.green.board.domain.CommentsDto;

@Service
public class CommentsServiceImpl implements CommentsService {
	
	//@Autowired
	CommentsDao commentsDao;
	
	//@Autowired
	BoardDao boardDao;
	
	//생성자 인잭션 (주입해야할게 2개 이상일 때)
	//생성자가 딱 하나만 있을때만 사용 가능
	@Autowired
	public CommentsServiceImpl(CommentsDao commentsDao, BoardDao boardDao) {
		this.commentsDao = commentsDao;
		this.boardDao = boardDao;
	}
	
	//DaoImpl보면서 작성
	//댓글 개수
	@Override
	public int getCount(Integer bno) throws Exception{
		return commentsDao.count(bno);
	}
	
	//게시글의 모든 댓글
	@Override
	public List<CommentsDto> getList(Integer bno) throws Exception{
		//throw new Exception("test");
		return commentsDao.selectAll(bno);
	}
	
	//댓글 내용 가져오기
	@Override
	public CommentsDto read(Integer cno) throws Exception{
		return commentsDao.select(cno);
	}
	
	//댓글 수정
	@Override
	public int modify(CommentsDto commentsDto) throws Exception{
		return commentsDao.update(commentsDto);
	}
	
	//댓글 작성(댓글 수 증가)
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int write(CommentsDto commentsDto) throws Exception{
		boardDao.updateCommentsCnt(commentsDto.getBno(), 1);
		//throw new Exception("test");
		return commentsDao.insert(commentsDto);
	}
	
	//댓글 지우기 (댓글 수 감소)
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int remove(Integer cno, String commenter, Integer bno) throws Exception{
		int rowCnt = boardDao.updateCommentsCnt(bno, -1);
		System.out.println("updateCommentsCnt - rowCnt = " + rowCnt);
		//throw new Exception("test");
		rowCnt = commentsDao.delete(cno, commenter);
		System.out.println("rowCnt = " + rowCnt);
		return rowCnt;
	}
}
