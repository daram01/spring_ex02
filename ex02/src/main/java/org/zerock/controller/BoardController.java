package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

	private BoardService service;// 단일 생성자 자동 주입
	
	// 전체 목록을 가져오는 처리
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list", service.getList());
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	// 등록 처리
	@PostMapping("/register") // 포스트 방식으로 들어왔을 때
	public String register(BoardVO board, RedirectAttributes rttr) { // 커맨드 객체 BoardVO에서 값을 자동으로 받아서 생성
		// RedirectAttributes의 addFlashAttribute -> 한번 사용할 데이터를 이동하는 페이지에 정보 전달 
		log.info("register : " + board);
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno()); // 
		return "redirect:/board/list";
	}
	
	// 조회 처리
	@GetMapping("/get")
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("/get");
		model.addAttribute("board", service.get(bno));
	}
	
	// 수정 처리
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify : " + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	// 삭제 처리
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("remove : " + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
}
