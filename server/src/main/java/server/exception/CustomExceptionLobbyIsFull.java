package server.exception;


public class CustomExceptionLobbyIsFull extends RuntimeException{
    public CustomExceptionLobbyIsFull(){
        super();
    }
    public CustomExceptionLobbyIsFull(String msg){
        super(msg);
    }
}
