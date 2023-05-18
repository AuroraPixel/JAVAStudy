package create_patterns.factoryPattern.factoryPattern.ImageFactory;

import create_patterns.factoryPattern.factoryPattern.ImageReader.PngReader;
import create_patterns.factoryPattern.factoryPattern.Reader;
import create_patterns.factoryPattern.factoryPattern.ReaderFactory;

public class PngReaderFactory implements ReaderFactory {
    public Reader getReader() {
        return new PngReader();
    }
}
