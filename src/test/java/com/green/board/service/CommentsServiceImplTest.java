package com.green.board.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.green.board.dao.BoardDao;
import com.green.board.dao.CommentsDao;
import com.green.board.domain.BoardDto;
import com.green.board.domain.CommentsDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class CommentsServiceImplTest {

	@Autowired
	CommentsService commentsService;
	
	@Autowired
	CommentsDao commentsDao;
	
	@Autowired
	BoardDao boardDao;
	
	@Test
	public void remove() throws Exception {
		//게시글 다 지우고
		boardDao.deleteAll();
		
		//새 글 작성
		BoardDto boardDto = new BoardDto("title1", "hello", "green1");
		
		//입력이 제대로 되었는지 확인
		assertTrue(boardDao.insert(boardDto)==1);
		
		//게시글 번호 가져오기
		Integer bno = boardDao.selectAll().get(0).getBno();
		System.out.println("bno : " + bno);
		
		//해당 게시글의 댓글 전부 삭제
		commentsDao.deleteAll(bno);
		
		//댓글 새로 생성
		CommentsDto commentsDto = new CommentsDto(bno, 0, "hi", "abcd");
		
		//댓글 개수가 0개
		assertTrue(boardDao.select(bno).getComments_cnt()==0);
		
		//댓글 입력
		assertTrue(commentsService.write(commentsDto)==1);
		
		//댓글 개수가 1개인지 확인
		assertTrue(boardDao.select(bno).getComments_cnt()==1);
		
		//댓글 번호 확인
		Integer cno = commentsDao.selectAll(bno).get(0).getCno();
		
		//댓글 지우고 삭제 확인
		int rowCnt = commentsService.remove(cno, commentsDto.getCommenter(), bno);
		assertTrue(rowCnt==1);
		
		//댓글 개수가 0인지 확인
		assertTrue(boardDao.select(bno).getComments_cnt()==0);
	}
	
	@Test
    public void write() throws  Exception {
        boardDao.deleteAll();

        BoardDto boardDto = new BoardDto("hello", "hello", "");
        assertTrue(boardDao.insert(boardDto) == 1);
        Integer bno = boardDao.selectAll().get(0).getBno();
        System.out.println("bno = " + bno);

        commentsDao.deleteAll(bno);
        CommentsDto commentDto = new CommentsDto(bno,0,"hi","abcd");

        assertTrue(boardDao.select(bno).getComments_cnt() == 0);
        assertTrue(commentsService.write(commentDto)==1);

        Integer cno = commentsDao.selectAll(bno).get(0).getCno();
        assertTrue(boardDao.select(bno).getComments_cnt() == 1);
    }
}
