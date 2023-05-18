package create_patterns.factoryPattern.factoryPattern.ImageFactory;

import create_patterns.factoryPattern.factoryPattern.ImageReader.JpgReader;
import create_patterns.factoryPattern.factoryPattern.Reader;
import create_patterns.factoryPattern.factoryPattern.ReaderFactory;

public class JpgReaderFactory implements ReaderFactory {

    public Reader getReader() {
        return new JpgReader();
    }
}
