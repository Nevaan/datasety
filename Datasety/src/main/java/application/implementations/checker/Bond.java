package application.implementations.checker;

import application.interfaces.checker.Checker;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by pawel on 26.08.2016.
 *  *<p>
 *     Implementacja wzorca obligacja
 * </p>
 */
public class Bond extends Checker{
    @Override
    public boolean checkPattern(Map<String, ArrayList<String>> dataMap) {
        return false;
    }
}