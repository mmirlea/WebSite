package com.green.board.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.green.board.domain.CommentsDto;

public interface CommentsService {

	int getCount(Integer bno) throws Exception;

	List<CommentsDto> getList(Integer bno) throws Exception;

	CommentsDto read(Integer cno) throws Exception;
	
	int modify(CommentsDto commentsDto) throws Exception;

	int write(CommentsDto commentsDto) throws Exception;

	int remove(Integer cno, String commenter, Integer bno) throws Exception;

}