package gr.unipi.android.audiostories.model;

public class Story {
    private String key;
    private int titleResourceId;
    private int imageId;
    public Story(String key, int titleResourceId, int imageId) {
        this.key = key;
        this.titleResourceId = titleResourceId;
        this.imageId = imageId;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setTitleResourceId(int titleResourceId) {
        this.titleResourceId = titleResourceId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getKey() {
        return key;
    }
    public int getTitleResourceId() {
        return titleResourceId;
    }
    public int getImageId() {
        return imageId;
    }
}
