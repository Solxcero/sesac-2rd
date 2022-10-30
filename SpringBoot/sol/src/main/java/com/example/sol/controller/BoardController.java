package com.example.sol.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sol.mapper.BoardMapper;
import com.example.sol.mapper.NoticeMapper;
import com.example.sol.model.Board;
import com.example.sol.model.Notice;
import com.example.sol.model.User;

@Controller
@RequestMapping("board")
public class BoardController {
    @Autowired
    BoardMapper boardMapper;
    @Autowired
    NoticeMapper noticeMapper;

    // 전체 게시글 보기 
    @GetMapping("boardList")
    public String boardList(HttpSession session, Model model,Board board,Notice notice){
        User user = (User)session.getAttribute("user");
        board.setBoardWriter(user.getUserId());
       
        ArrayList<Board>boardList = boardMapper.boardList();
        model.addAttribute("boardList", boardList);        

        String person = (String) board.getBoardWriter();
        // System.out.println("person ;" + person);
        ArrayList<Board> userBoard = boardMapper.selectBoard(person);
        // System.out.println("userboard"+userBoard);
        model.addAttribute("userboard", userBoard);
        model.addAttribute("person", person);
        model.addAttribute("user", user);

        ArrayList<Notice>noticeList = noticeMapper.noticeList();
        model.addAttribute("noticeList", noticeList);       


        return "board/boardList";
    }
    // 내 게시글 보기 
    @GetMapping("boardPage")
    public String boardPage(@RequestParam("boardWriter") String boardWriter, Model model, Board board){
        ArrayList<Board> boardData= boardMapper.selectBoard(boardWriter);
        model.addAttribute("boardData", boardData);
        return "board/boardPage";
    }


    //게시글 작성하기
    @GetMapping("boardCreate")
    public String boardCreate(){
        return "board/boardCreate";
    }
    @PostMapping("boardCreate")
    public String boardCreate(HttpSession session, Board board){
        User user = (User)session.getAttribute("user");
        board.setBoardWriter(user.getUserId());
        boardMapper.boardCreate(board);
        return "redirect:/board/boardList";
    }

    
    // 게시글 상세보기
    @GetMapping("boardDetail")
    public String boardDetail(HttpSession session, Model model, @RequestParam("boardNo") int boardNo){
        ArrayList<Board> boardList = boardMapper.boardList();
        for(Board board:boardList){
            if(board.getBoardNo() == boardNo){
                model.addAttribute("board", board);
            }
        }
        User user = (User)session.getAttribute("user");
        model.addAttribute("user", user);

        return "board/boardDetail";
    }
    
    // 게시글 수정
    @GetMapping("boardUpdate")
    public String boardupdate(HttpSession session, Model model, @RequestParam("boardNo") int boardNo){
        ArrayList<Board> boardList = boardMapper.boardList();
        for(Board board : boardList){
            if(board.getBoardNo()==boardNo){
                model.addAttribute("board", board);
            }
        }
        return "board/boardUpdate";
    }
    @PostMapping("boardUpdate")
    public String boardUpdate(HttpSession session, Board board, Model model){
        boardMapper.boardUpdate(board);
        return "redirect:/board/boardList";
    }
    
    // 게시글 삭제
    @GetMapping("boardRemove")
    public String boardRemove(@RequestParam("boardNo") int boardNo){
        boardMapper.boardRemove(boardNo);
        return "redirect:/board/boardList";
    }

    // 관리자 작동
    // 관리자 공지 게시하기


    @GetMapping("noticeCreate")
    public String noticeCreate(){
        return "board/noticeCreate";
    }
    
    @PostMapping("noticeCreate")
    public String noticeCreate(HttpSession session, Notice notice){
        
        noticeMapper.noticeCreate(notice);       
        return "redirect:/board/boardList";
    }

    @GetMapping("noticeDetail")
    public String noticeDetail(HttpSession session, Model model, @RequestParam("notNo") int notNo){
        ArrayList<Notice> noticeList = noticeMapper.noticeList();
        for(Notice notice:noticeList){
            if(notice.getNotNo() == notNo){
                model.addAttribute("notice", notice);
            }
        }
        User user = (User)session.getAttribute("user");
        model.addAttribute("user", user);

        return "board/noticeDetail";
    }

    @GetMapping("noticeRemove")
    public String noticeRemove(@RequestParam("notNo") int notNo){
        noticeMapper.noticeRemove(notNo);
        return "redirect:/board/boardList";
    }
}

    
       
        
        
