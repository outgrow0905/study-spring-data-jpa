package com.study.jpa.ch4.v3.visitor;

import com.study.jpa.ch4.v3.entity.AlbumV3;
import com.study.jpa.ch4.v3.entity.BookV3;
import com.study.jpa.ch4.v3.entity.MovieV3;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VisitorImpl1 implements Visitor{
    @Override
    public void visit(AlbumV3 album) {
        // album logic1...
        log.info("accept album: {}", album);
    }

    @Override
    public void visit(BookV3 book) {
        // book logic1...
        log.info("accept book: {}", book);
    }

    @Override
    public void visit(MovieV3 movie) {
        // movie logic1...
        log.info("accept movie: {}", movie);
    }
}
