package org.example;

import java.util.Objects;

public class CourseDto {
    private Long id;
    private String name;
    private String author;
    private String content;
    private Integer result;

    public CourseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseDto courseDto)) return false;
        return Objects.equals(id, courseDto.id) && Objects.equals(name, courseDto.name) && Objects.equals(author, courseDto.author) && Objects.equals(content, courseDto.content) && Objects.equals(result, courseDto.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, content, result);
    }
}
