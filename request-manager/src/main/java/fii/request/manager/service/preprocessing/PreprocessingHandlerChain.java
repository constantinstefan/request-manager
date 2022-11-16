package fii.request.manager.service.preprocessing;

import fii.request.manager.service.preprocessing.PreprocessingHandler;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Component
public class PreprocessingHandlerChain {
    List<PreprocessingHandler> handlers;

    @Autowired
    public PreprocessingHandlerChain(List<PreprocessingHandler> handlers) {
        this.handlers=handlers;
    }

    public BufferedImage doPreprocessing(BufferedImage bufferedImage) {
        for(var handler: handlers) {
            bufferedImage = handler.doPreprocessing(bufferedImage);
        }
        return bufferedImage;
    }
}
