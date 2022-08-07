package com.board.board.controller;

import com.board.board.entity.Board;
import com.board.board.service.BoardService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") // localhost 8080/board/write 로 가면 실행을한다
    public String boardWriteForm(){


        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String BoardWriteProc(Board board, Model model, MultipartFile file) throws Exception {

        model.addAttribute("message", "글작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");
        boardService.write(board, file);


        return "message";
    }
    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size= 10, direction = Sort.Direction.DESC) Pageable pageable
                            ,String searchKeyword){

        Page<Board> list = null;

        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }else{
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage-4, 1);
        int endPage = Math.min(nowPage+5, list.getTotalPages());

        model.addAttribute("list",list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardlist";
    }
    @GetMapping("/board/view") // localhost:8080/board/view?id=1 이렇게 넘긴다
    public String boardview(Model model, Integer id){

        model.addAttribute("board", boardService.boardview(id));
        return "boardview";
    }


    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boarddelete(id);
        return "redirect:/board/list";
    }


    @GetMapping("/board/modify/{id}") // 뒷부분의 id가 아래의 id로 넘어가는 뜻
    public String boardModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("message", "글작성이 완료되었습니다.");
        model.addAttribute("board", boardService.boardview(id));
        return "boardmodify";
    }




    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception{
        Board boardTemp = boardService.boardview(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp,file);
        model.addAttribute("message", "글작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }
}
