package com.green.board.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.board.domain.BoardDto;
import com.green.board.domain.PageHandler;
import com.green.board.domain.SearchCondition;
import com.green.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService service;
	
	//수정하기
	@PostMapping("/modify")
	public String modify(BoardDto boardDto, RedirectAttributes rattr, HttpSession session, Model m) {
		String writer = (String) session.getAttribute("id");
		
		boardDto.setWriter(writer);
		
		try {
			if(service.modify(boardDto) != 1) 
				throw new Exception("Modify failed");
			
			rattr.addFlashAttribute("msg", "MOD_OK");
			
			return "redirect:/board/list"; //주소를 이용하여 찾아감
		} catch (Exception e) {
			e.printStackTrace();
			
			m.addAttribute("mode", "new"); //글쓰기 모드로 이동
			m.addAttribute("boardDto", boardDto); //등록하려던 내용 전송
			m.addAttribute("msg", "MOD_ERR"); //에러메세지
			
			return "board"; //파일 위치를 찾아감
		}
			
	}
	
	//글쓰기
	@GetMapping("/write")
	public String write(Model m) {
		
		m.addAttribute("mode", "new");
		return "board";
	}
	
	@PostMapping("/write")
	public String write(BoardDto boardDto, RedirectAttributes rattr, HttpSession session, Model m) {
		String writer = (String) session.getAttribute("id");
		
		boardDto.setWriter(writer);
		
		try {
			if(service.write(boardDto) != 1) 
				throw new Exception("Write failed");
			
			rattr.addFlashAttribute("msg", "WRT_OK");
			
			return "redirect:/board/list"; //주소를 이용하여 찾아감
		} catch (Exception e) {
			e.printStackTrace();
			
			m.addAttribute("mode", "new"); //글쓰기 모드로 이동
			m.addAttribute("boardDto", boardDto); //등록하려던 내용 전송
			m.addAttribute("msg", "WRT_ERR"); //에러메세지
			
			return "board"; //파일 위치를 찾아감
		}
			
	}
	
	//삭제하기
	@PostMapping("/remove")
	public String remove(Integer bno, Integer page, Integer pageSize, RedirectAttributes rattr, Model m, HttpSession session) {
		String writer = (String) session.getAttribute("id");
		
		try {
			m.addAttribute("page", page);
			m.addAttribute("pageSize", pageSize);
			
			int rowCnt = service.remove(bno, writer);
			
			//삭제 되면 메세지 띄움
			if (rowCnt != 1) {
				throw new Exception("remove error");
			}
			
			//rattr.addFlashAttribute : 일회용
			//세션에 메세지를 저장하여 전달하고 한번 사용하고 나면 알아서 삭제함
			rattr.addFlashAttribute("msg", "DEL_OK");
			
		} catch (Exception e) {
			e.printStackTrace();
			rattr.addFlashAttribute("msg", "DEL_ERR");
		}
		
		return "redirect:/board/list";
	}
	
	//게시글읽기
	@GetMapping("/read")
	public String read(Integer bno, Integer page, Integer pageSize, Model m) {
		try {
			BoardDto boardDto = service.read(bno);
			
			//addAttribute의 이름을 생략하면
			//입력하는 값의 타입의 첫 글자를 소문자로 바꿔서 이름으로 사용함
			m.addAttribute(boardDto);
			m.addAttribute("page",page);
			m.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "board";
	}
	
	@GetMapping("/list")
	public String list(SearchCondition sc, Model m, HttpServletRequest request) {
//		if (page==null) page=1;
//		if (pageSize == null) pageSize=10;
		
		//로그인 확인
		if(!loginCheck(request))
			//request.getRequestURL() : 호출된 URL주소를 가져옴
			return "redirect:/login/login?toURL=" + request.getRequestURL();
		try {
			
			int totalCnt = service.getCount();
			
			PageHandler pageHandler = new PageHandler(totalCnt, sc);
			
//			Map map = new HashMap();
//			map.put("page", page);
//			map.put("pageSize", pageSize);
			
			List<BoardDto> list = service.getSearchSelectPage(sc);
			
			m.addAttribute("list", list);
			m.addAttribute("ph", pageHandler);
//			m.addAttribute("page",page);
//			m.addAttribute("pageSize", pageSize);
			
			//LocalDate : 날짜 정보만 필요할 때 사용
			//now() : 현재 날짜
			//atStartOfDay : LocalDateTime객체를 가져오는 메서드
			//  하루가 시작되는 날짜에 시간을 추가하고 날짜-시간을 반환
			//ZoneId : 현재 지역의 시간을 나타내는 클래스
			//systemDefault() : 시스템의 기본값
			//toEpochMilli() : 현재 시간의 값을 밀리초로 변환
			Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
			m.addAttribute("startOfToday", startOfToday.toEpochMilli());
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("msg", "LIST_ERR");
			m.addAttribute("totalCnt", 0);
		}
		return "boardList";
	}
	
	//로그인 여부 확인
	private boolean loginCheck(HttpServletRequest request) {
		 
		//1) 세션을 가져오기
		HttpSession session = request.getSession();
		
		//2) 세션에 id가 있는지 확인
		return session.getAttribute("id") != null;

	}
}
