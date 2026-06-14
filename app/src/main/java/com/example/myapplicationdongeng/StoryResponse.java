package com.example.myapplicationdongeng;

import java.util.List;

public class StoryResponse {

    private List<StoryItem> items;

    public List<StoryItem> getItems() {
        return items;
    }

    public void setItems(List<StoryItem> items) {
        this.items = items;
    }

    public static class StoryItem {

        private String title;
        private String file;
        private String flag;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
