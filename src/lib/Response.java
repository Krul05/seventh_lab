package lib;

import java.io.Serializable;

public class Response implements Serializable {
    String response;
    Boolean flag = false;
    public Response(String response) {
        this.response = response;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getFlag() {
        return flag;
    }

    public String getResponse() {
        return response;
    }
}
