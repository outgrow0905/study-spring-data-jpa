package com.study.jpa.ch4.v3.visitor;

import com.study.jpa.ch4.v3.entity.AlbumV3;
import com.study.jpa.ch4.v3.entity.BookV3;
import com.study.jpa.ch4.v3.entity.MovieV3;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VisitorImpl2 implements Visitor{
    @Override
    public void visit(AlbumV3 album) {
        // album logic2...
    }

    @Override
    public void visit(BookV3 book) {
        // book logic2...
    }

    @Override
    public void visit(MovieV3 movie) {
        // movie logic2...
    }
}
