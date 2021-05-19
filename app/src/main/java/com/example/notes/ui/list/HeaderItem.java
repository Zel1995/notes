package com.example.notes.ui.list;

public class HeaderItem implements AdapterItem<String>{
    private final String title;

    public HeaderItem(String title) {
        this.title = title;
    }

    @Override
    public String getUniqueTag() {
        return "headerItem" + title;
    }

    @Override
    public String getItem() {
        return title;
    }
}
