package goldstarproject.template.community.board.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BoardType {
    BOARD("board"),
    RECRUIT("recruit"),
    NOTICE("notice"),
    QUESTION("question"),
    IMAGE("image"),
    IMAGES("images"),
    ADCONNECTS("adConnects"),
    LIVE("live");
    private final String value;
}
