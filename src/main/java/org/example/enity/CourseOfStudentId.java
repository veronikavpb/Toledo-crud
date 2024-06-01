package org.example.enity;

import jakarta.persistence.Embeddable;

@Embeddable
public class CourseOfStudentId {
    private Long user;
    private Long course;

    public CourseOfStudentId() {
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getCourse() {
        return course;
    }

    public void setCourse(Long course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
