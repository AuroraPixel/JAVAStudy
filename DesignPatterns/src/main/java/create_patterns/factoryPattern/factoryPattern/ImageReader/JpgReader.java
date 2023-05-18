package create_patterns.factoryPattern.factoryPattern.ImageReader;

import create_patterns.factoryPattern.factoryPattern.Reader;

public class JpgReader implements Reader {
    public void read() {
        System.out.println("jpgReader");
    }
}
