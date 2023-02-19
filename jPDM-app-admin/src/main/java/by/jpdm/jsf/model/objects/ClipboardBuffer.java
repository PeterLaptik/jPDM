package by.jpdm.jsf.model.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Cut-paste buffer
 * 
 * @author Peter Laptik
 */
public class ClipboardBuffer<T> {
    private List<T> buffer = new ArrayList<T>();

    public ClipboardBuffer() {

    }

    public void cut(List<T> cutList) {
        buffer.clear();

        if (cutList == null)
            return;

        buffer.addAll(cutList);
    }

    public List<T> flush() {
        List<T> result = buffer;
        buffer = new ArrayList<T>();
        return result;
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public void clear() {
        buffer.clear();
    }
}
