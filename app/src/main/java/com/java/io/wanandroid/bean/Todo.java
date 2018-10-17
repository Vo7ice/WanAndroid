package com.java.io.wanandroid.bean;

import java.util.List;

public class Todo {
    private List<TodoBean> doneList;
    private List<TodoBean> todoList;
    private int type;

    public class TodoBean {
        private long date;
        private List<TodoListBean> todoList;

        public class TodoListBean{
            private long completeDate;
            private String completeDateStr;
            private String content;
            private long date;
            private String dateStr;
            private int id;
            private int status;
            private String title;
            private int type;
            private int userId;
        }
    }
}
