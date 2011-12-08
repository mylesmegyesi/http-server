package HttpServer.Utility;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class Logging {

    public Logger getLoggerAndSetLevel(String name, Level level) {
        Logger globalLogger = Logger.getLogger("");
        Handler[] globalLoggerHandlers = globalLogger.getHandlers();
        for (Handler handler : globalLoggerHandlers) {
            handler.setLevel(level);
        }
        Logger logger = Logger.getLogger(name);
        logger.setLevel(level);
        return logger;
    }

}
