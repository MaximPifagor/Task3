package webSocket;

public interface IServer<T> {
    T doCommand(String command);
}
