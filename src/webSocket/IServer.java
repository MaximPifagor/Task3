package webSocket;

public interface IServer<T> {
    T doCommands(String command);
}
