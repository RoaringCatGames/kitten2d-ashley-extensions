package com.roaringcatgames.kitten2d.gdx.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Map;

/**
 * A basic default preference manager implementation. Most small games
 * can use this implementation without much hassle.
 */
public class K2PreferenceManager implements IPreferenceManager {

    private final String PREFERENCES_NAME;
    public K2PreferenceManager(String preferenceName) {
        PREFERENCES_NAME = preferenceName;
    }

    @Override
    public String getStoredString(String key, String...defaultValue) {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        String value = "";
        if(preferences.contains(key)){
            value = preferences.getString(key);
        }

        if(defaultValue != null && defaultValue.length == 1 && (value == null || "".equals(value.trim()))){
            value = defaultValue[0];
        }
        return value;
    }

    @Override
    public int getStoredInt(String key, int...defaultValue) {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        int value = defaultValue != null && defaultValue.length == 1 ? defaultValue[0] : -1;
        if(preferences.contains(key)){
            value = preferences.getInteger(key);
        }
        return value;
    }

    @Override
    public float getStoredFloat(String key, float...defaultValue) {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        float value = defaultValue != null && defaultValue.length == 1 ? defaultValue[0] : -1f;
        if(preferences.contains(key)){
            value = preferences.getFloat(key);
        }
        return value;
    }

    @Override
    public void updateFloat(String key, float value) {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        preferences.putFloat(key, value);
        preferences.flush();
    }

    @Override
    public void updateInt(String key, int value) {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        preferences.putInteger(key, value);
        preferences.flush();
    }

    @Override
    public void updateString(String key, String value) {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        preferences.putString(key, value);
        preferences.flush();
    }

    @Override
    public void updateValues(Map<String, ?> values) {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        preferences.put(values);
        preferences.flush();
    }

    @Override
    public void clear() {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        preferences.clear();
        preferences.flush();
    }

    @Override
    public void deleteValue(String key) {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        preferences.remove(key);
        preferences.flush();
    }
}
