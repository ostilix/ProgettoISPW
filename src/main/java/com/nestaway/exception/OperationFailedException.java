package com.nestaway.exception;

public class OperationFailedException extends Exception{

    public OperationFailedException(String message){
        super(message);
    }

    public OperationFailedException() {
        super("Unable to complete the operation. If problem persists, contact support. Caller: " + getCallerMethod());
    }

    private static String getCallerMethod() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if (stack.length > 3) {
            return stack[3].getClassName() + "." + stack[3].getMethodName();
        } else {
            return "Unknown";
        }
    }

}
