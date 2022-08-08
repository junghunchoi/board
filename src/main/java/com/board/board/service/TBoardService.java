package com.board.board.service;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class TBoardService {


    @Autowired
    private BoardRepository boardRepository;

    //글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\staticfiles\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName); // 원래 파일 경로, 랜덤아이디를 붙인 파일명

        file.transferTo(saveFile); // 인자로 받은 파일에 이름과 경로를 넣은 변수를 보낸다.

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){


    }

    //특정 게시글 불러오기
    public Board boardview(Integer id){


    }

    //특정게시글삭제

    public void boarddelete(Integer id){



    }

    //검색
    public Page<Board> boardSearchList(String searchkeyword, Pageable pageable){

    }


}
