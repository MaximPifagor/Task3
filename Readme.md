# Server
class Threaded implements Runable.
ThreadDispatcher runs Threaded's object as a Thread when you put its into method add(Threaded t).
ThreadMonitor collect list of alive Threads that was runned by ThreadDispatcher in interactive mode.
FileWorker write files's hash into "output.txt".
webSocket is a Server that have two commands - hash <fileName>, list;
first command return hash of file
second command return list of all files in main Directory 



