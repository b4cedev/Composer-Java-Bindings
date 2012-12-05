package org.getcomposer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.getcomposer.annotation.Name;
import org.getcomposer.collection.JsonCollection;
import org.json.simple.JSONValue;

public abstract class Entity {

	private transient PropertyChangeSupport changeSupport = new PropertyChangeSupport(
			this);
	private transient Set<String> listening = new HashSet<String>(); 

	public Entity() {
		listen();
		initialize();
	}
	
	// can be filled by subclasses
	protected void initialize() {
	}
	
	protected void listen() {
		try {
			for (Field field : getFields(this.getClass())) {
				if (JsonCollection.class.isAssignableFrom(field.getType())) {
					final String prop = getFieldName(field);	
					
					if (listening.contains(prop)) {
						continue;
					}
					
					field.setAccessible(true);
					Entity obj = (Entity)field.get(this);

					if (obj != null) {
						obj.addPropertyChangeListener(new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent e) {
								firePropertyChange(prop, e.getOldValue(), e.getNewValue());
							}
						});
						
						listening.add(prop);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected ArrayList<Field> getFields(Class entity) {
		ArrayList<Field> fields = new ArrayList<Field>();
		Class superClass = entity;
		
		while (superClass != null) {
			for (Field field : superClass.getDeclaredFields()) {
				if (!((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT)) {
					fields.add(field);
				}
			}
			superClass = superClass.getSuperclass();		
		}
		
		return fields;
	}
	
	protected String getFieldName(Field field) {
		String name = field.getName();
		for (Annotation anno : field.getAnnotations()) {
			if (anno.annotationType() == Name.class) {
				name = ((Name) anno).value();
			}
		}
		return name;
	}
	
	
	// subclasses should implement that
	protected abstract void parse(Object obj);
	
	public abstract Object prepareJson(LinkedList<String> fields);
	
	public String toJson() {
		return JsonFormatter.format(prepareJson(new LinkedList<String>()));
	}
	
	public void fromJson(Object json) {
		parse(json);
	}
	
	public void fromJson(String json) {
		parse(JSONValue.parse(json));
	}
	
	public void fromJson(File file) throws IOException {
		fromJson(new FileReader(file));
	}
	
	public void fromJson(Reader reader) throws IOException {
		parse(JSONValue.parse(reader));
	}
	
	/**
	 * Adds a listener to be notified when a property changes
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a listener that no longer receives notification about property changes
	 * 
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Adds a listener to be notified when the passed property changes
	 * 
	 * @param propertyName the property upon which the listener will be notified
	 * @param listener
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Removes a listener that no longer receives notification about changes from
	 * the passed property
	 * 
	 * @param propertyName the property upon which the listener has been notified
	 * @param listener
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Notify listeners that a property has changed its value
	 * 
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}
