package study.jigl.com.rxjavastudy.bean;

import java.util.List;

public class Student {

    public Student() {

    }

    public String name;//学生名字
    public int id;
    public List<Source> mSources;//每个学生的所有课程

    public Student(String name, int id, List<Source> sources) {
        this.name = name;
        this.id = id;
        mSources = sources;
    }
}

