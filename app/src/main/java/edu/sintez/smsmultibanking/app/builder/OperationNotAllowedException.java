package edu.sintez.smsmultibanking.app.builder;


public class OperationNotAllowedException extends RuntimeException {

    public OperationNotAllowedException() {
    }

    public OperationNotAllowedException(String s) {
        super(s);
    }

    public OperationNotAllowedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public OperationNotAllowedException(Throwable throwable) {
        super(throwable);
    }

    /*public OperationNotAllowedException(String s, Throwable throwable, boolean b, boolean b2) {
        super(s, throwable, b, b2);
    }*/
}