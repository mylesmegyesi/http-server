package HttpServer.Mocks;


import SocketServer.SocketServer;

/**
 * Author: Myles Megyesi
 */
public class SocketServerMock extends SocketServer {

    public int startCalledCount = 0;
    public int stopCalledCount = 0;

    public SocketServerMock() {
        super(0, null);
    }

    @Override
    public void start() {
        this.startCalledCount++;
    }

    @Override
    public void stop() {
        this.stopCalledCount++;
    }
}
