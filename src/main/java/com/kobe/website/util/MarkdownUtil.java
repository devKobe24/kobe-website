package com.kobe.website.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.kobe.website.util
 * fileName       : MarkdownUtil
 * author         : kobe
 * date           : 2025. 12. 19.
 * description    : Markdown 텍스트를 HTML로 변환하는 유틸리티 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 19.        kobe       최초 생성
 */
@Component
public class MarkdownUtil {

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownUtil() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }

    /**
     * Markdown 문자열을 HTML로 변환
     * @param markdown 마크다운 형식의 텍스트
     * @return HTML 문자열
     */
    public String toHtml(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}

