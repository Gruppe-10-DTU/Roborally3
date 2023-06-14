package server.exception;

public class CustomExceptionNoSavedGames extends RuntimeException {
    public CustomExceptionNoSavedGames(){
        super();
    }
    public CustomExceptionNoSavedGames(String msg){
        super(msg);
    }

}
