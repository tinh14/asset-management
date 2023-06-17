package utilz;

/**
 *
 * @author tinhlam
 */
public class ResponseMessage {

    private int status;
    private String message;

    public ResponseMessage() {

    }

    public ResponseMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isError() {
        return status >= 400 && status <= 599;
    }
    
    public void appendMessage(String newMessage){
        this.message += newMessage;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
