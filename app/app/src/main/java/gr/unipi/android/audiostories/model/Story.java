package gr.unipi.android.audiostories.model;

public class Story {
    private final String key;
    private final int titleResourceId;
    private final int imageId;
    public Story(String key, int titleResourceId, int imageId) {
        this.key = key;
        this.titleResourceId = titleResourceId;
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
