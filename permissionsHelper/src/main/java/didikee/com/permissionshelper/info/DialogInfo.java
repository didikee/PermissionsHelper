package didikee.com.permissionshelper.info;

/**
 * Created by didik 
 * Created time 2016/12/13
 * Description: 
 */

public class DialogInfo {
    private String title;
    private String content;
    private String positiveButtonText;
    private String negativeButtonText;
    private boolean isShow;

    public DialogInfo(String title, String content, String positiveButtonText, String
            negativeButtonText, boolean isShow) {
        this.title = title;
        this.content = content;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
        this.isShow = isShow;
    }

    public DialogInfo() {
    }

    public boolean isShow() {
        return isShow;
    }

    public void showDialog(boolean show) {
        isShow = show;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }
}
