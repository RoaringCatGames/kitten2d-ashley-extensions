package com.roaringcatgames.kitten2d.gdx.helpers;

import java.util.Map;

/**
 * Defines the API for interacting with preferences
 */
public interface IPreferenceManager {

    String getStoredString(String key, String...defaultValue);
    int getStoredInt(String key, int...defaultValue);
    float getStoredFloat(String key, float...defaultValue);
    boolean getStoredBoolean(String key, boolean...defaultValue);

    void updateString(String key, String value);
    void updateInt(String key, int value);
    void updateFloat(String key, float value);
    void updateBoolean(String key, boolean value);

    void updateValues(Map<String, ?> values);

    void clear();
    void deleteValue(String key);
}
