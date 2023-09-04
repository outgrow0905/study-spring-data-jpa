package com.study.jpa.ch4.v3.visitor;

import com.study.jpa.ch4.v3.entity.AlbumV3;
import com.study.jpa.ch4.v3.entity.BookV3;
import com.study.jpa.ch4.v3.entity.MovieV3;

public interface Visitor {
    void visit(AlbumV3 album);
    void visit(BookV3 book);
    void visit(MovieV3 movie);
}
