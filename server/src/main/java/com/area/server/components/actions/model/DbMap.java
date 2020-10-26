package com.area.server.components.actions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Db map.
 */
@JsonIgnoreProperties(value = { "id" })
@Entity
@Table(name = "map")
public class DbMap {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "map_id")
	private int id;

	@NotNull
	private String name;

	@NotNull
	private String type;

	@NotNull
	private String value;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
		return id;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
		return type;
	}

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
		this.type = type;
	}

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
		return value;
	}

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
		this.value = value;
	}

    /**
     * Db to map map.
     *
     * @param list the list
     * @return the map
     */
    public static Map<String, DbMap> dbToMap(Collection<DbMap> list) {
		Map<String, DbMap> res = new HashMap<>();
		for (DbMap m : list) {
			res.put(m.getName(), m);
		}
		return res;
	}
}