import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class CompareUtil<T> {

    private final ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompareUtil.class);

    public CompareUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, String> getUpdatedFieldValue(T original , T updated ){
        Map<String, String> originalObjectMap =  objectMapper.convertValue(original, Map.class);
        Map<String, String> updatedObjectMap = objectMapper.convertValue(updated, Map.class);
        Map<String, String> updatedValues = getUpdatedValues(originalObjectMap, updatedObjectMap);
        LOGGER.debug("getUpdatedFieldValue() return with updated field-value :{}", updatedValues);
        return updatedValues;
    }

    private Map<String, String> getUpdatedValues(Map<String, String> originalObjectMap
            , Map<String, String> updatedObjectMap) {
        Map<String, String> updatedValues = new HashMap<>();
        for(Map.Entry<String, String> updated : updatedObjectMap.entrySet()){
            Optional<String> key = originalObjectMap.keySet().stream().filter(p -> updated.getKey().equals(p)).findFirst();
            key.ifPresent(s -> updatedValues.put(s, updated.getValue()));
        }
        return updatedValues;
    }
}
