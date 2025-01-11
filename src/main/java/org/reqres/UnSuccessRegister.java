package org.reqres;

public class UnSuccessRegister {
    private String error;

    public UnSuccessRegister(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
