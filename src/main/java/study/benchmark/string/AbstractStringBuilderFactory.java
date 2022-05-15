package study.benchmark.string;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * @author wangdengwu
 */
@State(Scope.Benchmark)
public class AbstractStringBuilderFactory {
    StringBuilder stringBuilder = new StringBuilder();
    StringBuffer stringBuffer = new StringBuffer();
    String string = new String();

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public StringBuffer getStringBuffer() {
        return stringBuffer;
    }

    @Setup(Level.Iteration)
    public void init() {
        stringBuilder = new StringBuilder();
        stringBuffer = new StringBuffer();
        string = new String();
    }
}
