package dou.ding.nyat.blog.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BeanTools {

    private ModelMapper modelMapper;

    public BeanTools() {
        modelMapper = new ModelMapper();
    }

    public <M, E> E map(M source, E target) {
        modelMapper.map(source, target);
        return target;
    }
}
