package com.vfc.petz.test.config.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.test.steps.EntitySampleFactory;
import io.cucumber.datatable.TableEntryTransformer;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class EntityTableTransformer<T> implements TableEntryTransformer<T> {

    protected final ObjectMapper objectMapper;
    protected final EntitySampleFactory entitySampleFactory;
    private final Type type;

    protected EntityTableTransformer(ObjectMapper objectMapper, EntitySampleFactory entitySampleFactory) {
        this.objectMapper = objectMapper;
        this.entitySampleFactory = entitySampleFactory;
        this.type = this.loadType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T transform(Map<String, String> entry) throws Throwable {
        return this.objectMapper.readValue(this.objectMapper.writeValueAsString(entry), (Class<T>) this.type);
    }

    protected boolean isFilledVariable(String variableValue) {
        return StringUtils.isNotBlank(variableValue) && !StringUtils.equalsIgnoreCase("null", variableValue);
    }

    private Type loadType() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) {
            throw new IllegalArgumentException("TypeReference constructed without type information");
        }
        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }
}
