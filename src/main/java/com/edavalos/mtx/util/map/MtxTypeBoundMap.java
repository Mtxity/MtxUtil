package com.edavalos.mtx.util.map;

import java.lang.invoke.WrongMethodTypeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MtxTypeBoundMap extends HashMap<String, Object> {

    public String getString(String key) {
        if (super.get(key) == null) {
            return null;
        }

        try {
            return (String) super.get(key);
        } catch (ClassCastException cce) {
            throw new WrongMethodTypeException("Value for key '" + key + "' is not a string");
        }
    }

    public Integer getInteger(String key) {
        if (super.get(key) == null) {
            return null;
        }

        try {
            return (Integer) super.get(key);
        } catch (ClassCastException cce) {
            throw new WrongMethodTypeException("Value for key '" + key + "' is not an integer");
        }
    }

    public Boolean getBoolean(String key) {
        if (super.get(key) == null) {
            return null;
        }

        try {
            return (Boolean) super.get(key);
        } catch (ClassCastException cce) {
            throw new WrongMethodTypeException("Value for key '" + key + "' is not a boolean");
        }
    }

    public Double getDouble(String key) {
        if (super.get(key) == null) {
            return null;
        }

        try {
            return (Double) super.get(key);
        } catch (ClassCastException cce) {
            throw new WrongMethodTypeException("Value for key '" + key + "' is not a double");
        }
    }

    public Long getLong(String key) {
        if (super.get(key) == null) {
            return null;
        }

        try {
            return (Long) super.get(key);
        } catch (ClassCastException cce) {
            throw new WrongMethodTypeException("Value for key '" + key + "' is not a long");
        }
    }

    public Character getCharacter(String key) {
        if (super.get(key) == null) {
            return null;
        }

        try {
            return (Character) super.get(key);
        } catch (ClassCastException cce) {
            throw new WrongMethodTypeException("Value for key '" + key + "' is not a character");
        }
    }

    public List<?> getList(String key) {
        if (super.get(key) == null) {
            return null;
        }

        try {
            return (List<?>) super.get(key);
        } catch (ClassCastException cce) {
            throw new WrongMethodTypeException("Value for key '" + key + "' is not a list");
        }
    }

    public Map<?, ?> getMap(String key) {
        if (super.get(key) == null) {
            return null;
        }

        try {
            return (Map<?, ?>) super.get(key);
        } catch (ClassCastException cce) {
            throw new WrongMethodTypeException("Value for key '" + key + "' is not a map");
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        if (super.get(key) == null) {
            return null;
        }

        return (T) super.get(key);
    }
}
