package com.example.sol.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.sol.model.Notice;


@Mapper
public interface NoticeMapper {

    public void noticeCreate(Notice notice);

    public ArrayList<Notice> noticeList();

    public void noticeRemove(int notNo);


    
}
