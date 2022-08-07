package com.board.board.controller;

import com.board.board.entity.Board;
import com.board.board.service.BoardService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") // localhost 8080/board/write 로 가면 실행을한다
    public String boardWriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String BoardWriteProc(Board board){

        System.out.println("제목:" + board.getTitle());
        System.out.println("내용:" + board.getContent());
        boardService.write(board);

        return "";
    }
    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("list",boardService.boardList());
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
        model.addAttribute("board", boardService.boardview(id));
        return "boardmodify";
    }
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board){
        Board boardTemp = boardService.boardview(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);
        return "redirect:/board/list";
    }
}
