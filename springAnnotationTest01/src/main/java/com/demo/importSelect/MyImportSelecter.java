package com.demo.importSelect;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Joe
 * @version 1.0
 * @date 2020/3/13 10:29
 */
public class MyImportSelecter implements ImportSelector {
    /**
     *
     * @param annotationMetadata 类上的所有注解信息
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        return new String[]{"com.demo.compent.Black","com.demo.compent.Red"};
    }
}
