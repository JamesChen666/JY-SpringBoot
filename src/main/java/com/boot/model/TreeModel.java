package com.boot.model;

import java.util.List;

public class TreeModel {
        public String id ;
        public String pid;
        public String text;
        public String state;
        public boolean Checked;
        public String attributes;
        public List<TreeModel> children;

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getPid() {
                return pid;
        }

        public void setPid(String pid) {
                this.pid = pid;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public String getState() {
                return state;
        }

        public void setState(String state) {
                this.state = state;
        }

        public boolean isChecked() {
                return Checked;
        }

        public void setChecked(boolean checked) {
                Checked = checked;
        }

        public String getAttributes() {
                return attributes;
        }

        public void setAttributes(String attributes) {
                this.attributes = attributes;
        }

        public List<TreeModel> getChildren() {
                return children;
        }

        public void setChildren(List<TreeModel> children) {
                this.children = children;
        }
}