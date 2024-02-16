package com.timakh.blog_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Action {
    UPVOTE(1),
    DOWNVOTE(-1);

    private final int dynamic;

}
