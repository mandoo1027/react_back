package com.commerce.comm;

import com.commerce.exception.UserException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class ObjectMapperUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String serialization(Object object) throws UserException {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new UserException("COM0001");
        }
    }

    public static <T> T deserialization(String jsonText, Class<T> valueType) throws UserException {
        try {
            return (T) mapper.readValue(jsonText, valueType);
        } catch (IOException e) {
            throw new UserException("COM0001");
        }
    }

    public static <T> T typeConvert(Object object, Class<T> valueType)  {
        return (T) mapper.convertValue(object, valueType);
    }
    
    /**
     * Map to Map 처리불가
     * @param source
     * @param clazz
     * @return
     */
    public static <T> T convertToVo(Object source, Class<T> clazz) {
    	if(source == null) {
    		return null;    		
    	}
		if (source instanceof CamelKeyMap) {
			if(((CamelKeyMap) source).size() == 0){
				return null;
			}
		}
    	if(clazz == Map.class) {
    		Map<String, Object> resultMap = new HashMap<>();
    		for(Field field : source.getClass().getDeclaredFields()) {
    			field.setAccessible(true);
    			Object value = null;
				try {
					value = field.get(source);
				} catch (Exception e) {
					//LOGGER.error(Effel.Request.getReqCom().getScreenId() + " : " + "converToVo Exception!! [" + field.getName() + "]");
				}
    			if(value != null) {
    				resultMap.put(field.getName(), String.valueOf(value));
    			} else {
    				resultMap.put(field.getName(), "");
    			}
    		}
    		
//    		if(Objects.nonNull(BizSession.getUser())) {
//    			resultMap.put("userId", BizSession.getUser().getUserId());
//    		}
    		
    		return (T) resultMap;
    	}
    	
    	T obj = null;
    	
    	try {
    		obj = clazz.getDeclaredConstructor().newInstance();
    		// source type = Map
    		if(source instanceof Map) {
    			Map<String, Object> map = (Map<String, Object>)source;
    			for(String key : map.keySet()) {
    				Object value = map.get(key);
    				
    				try {
    					Field targetField = clazz.getDeclaredField(key);
    					targetField.setAccessible(true);
    					
    					if(value != null) {
    						Class<?> fieldClass = targetField.getType();
    						if(!fieldClass.isAssignableFrom(value.getClass())) {
    							// 타입변환 추가필요시 여기다 추가
    							if(fieldClass == String.class) {
    								value = String.valueOf(value);
    							} else if(fieldClass == Integer.class) {
    								value = Integer.parseInt(String.valueOf(value));
    							} else if(fieldClass == Double.class) {
    								value = Double.parseDouble(String.valueOf(value));
    							} else if(fieldClass == Float.class) {
    								value = Float.parseFloat(String.valueOf(value));
    							} else if(fieldClass == boolean.class) {
    								value = Boolean.parseBoolean(String.valueOf(value));
    							} else if(fieldClass == Date.class) {
    								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    								value = simpleDateFormat.parse(String.valueOf(value));
    							} else {
    							//	LOGGER.debug("타입 변환을 지원하지 않습니다: " + value.getClass() + " to " + fieldClass);
    							}
    						}
    					}
    					
    					targetField.set(obj, value);
    				} catch(NoSuchFieldException e) {
    					continue;
    				}
    			}
    		} else {
    			// source type != Map
    			Class<?> sourceClass = source.getClass();
    			
    			for(Field sourceField : sourceClass.getDeclaredFields()) {
    				sourceField.setAccessible(true);
    				Object value = sourceField.get(source);
    				
    				if(value == null) {
    					continue;
    				}
    				
    				try {
    					Field targetField = clazz.getDeclaredField(sourceField.getName());
    					targetField.setAccessible(true);
    					targetField.set(obj, value);
    				} catch (NoSuchFieldException e) {
    					continue;
    				}
    				
    			}
    		}
		
    	} catch(Exception e) {
    		//LOGGER.error(Effel.Request.getReqCom().getScreenId() + " : " + "converToVo Exception!!");
    	}
    	
    	return obj;
    }

	public static <T> T convertToMap(Object source, String defaultValue) {
		if (source == null) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<>();
		for (Field field : source.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(source);
			} catch (Exception e) {
				//LOGGER.error(Effel.Request.getReqCom().getScreenId() + " : " + "converToVo Exception!! [" + field.getName() + "]");
			}
			if (value != null) {
				resultMap.put(field.getName(), String.valueOf(value));
			} else {
				if(Objects.nonNull(defaultValue)) {
					resultMap.put(field.getName(), defaultValue);
				}
			}
		}

//		if (Objects.nonNull(BizSession.getUser())) {
//			resultMap.put("userId", BizSession.getUser().getUserId());
//		}

		return (T) resultMap;
	}
    
    public static <T> List<T> convertToList(List<?> list, Class<T> clazz)  {
    	if(list == null) {
    		return null;
    	}
    	
    	List<T> convertList = new ArrayList<>();
    	for(Object item : list) {
    		T obj = convertToVo(item, clazz);
    		convertList.add(obj);
    	}
    	
    	return convertList;
    }
    
    /**
     * target 에 source의 entry.value != null 이 아닌것을 복사한다.
     * @param source
     * @param target
     * @return
     * @throws UserException
     */
    public static Map<String, Object> copyMap(Map<String, Object> source, Map<String, Object> target) throws UserException {
    	for(Map.Entry<String, Object> entry : source.entrySet()) {
    		String key = entry.getKey();
    		Object value = entry.getValue();
    		
    		if(Objects.nonNull(value)) {
    			target.put(key, value);
    		}
    	}
    	
    	return target;
    }

	/**
	 * source 값을 target에 복사시킨다. null 은 복사하지 않는다
	 * @param source object
	 * @param target object
	 */
	public static void copyProperties(Object source, Object target) {
		try{
			Class<?> sourceClass = source.getClass();
			Class<?> targetClass = target.getClass();

			for(Field sourceField : sourceClass.getDeclaredFields()) {
				sourceField.setAccessible(true);
				Object value = sourceField.get(source);

				if(value == null) {
					continue;
				}

				try {
					Field targetField = targetClass.getDeclaredField(sourceField.getName());
					targetField.setAccessible(true);
					targetField.set(target, value);
				} catch (NoSuchFieldException e) {
					continue;
				}
			}
		} catch(Exception e) {
			//LOGGER.error(Effel.Request.getReqCom().getScreenId() + " : " + "converToVo Exception!!");
		}
	}
}
