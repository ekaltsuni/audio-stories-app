package gr.unipi.android.audiostories.model;

public class Story {
    private String title;
    private int imageId;

    public Story(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }
}
