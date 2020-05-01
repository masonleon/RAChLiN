package io.seeport.rachlin.aiswebserver.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "message_types")
public class MessageType {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "note")
	private String note;

	@Column(name = "supported")
	private Boolean supported;

	public MessageType(){};

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getSupported() {
		return supported;
	}

	public void setSupported(Boolean supported) {
		this.supported = supported;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s %s", name, description, note, supported);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MessageType)) return false;

		MessageType messageType = (MessageType) o;

		if (!id.equals(messageType.id)) return false;
//		if (!name.equals(messageType.name)) return false;
		if (name != null ? !name.equals(messageType.name) : messageType.name != null) return false;
		if (description != null ? !description.equals(messageType.description) : messageType.description != null) return false;
		if (note != null ? !note.equals(messageType.note) : messageType.note != null) return false;
		return supported.equals(messageType.supported);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (note != null ? note.hashCode() : 0);
		result = 31 * result + supported.hashCode();
		return result;
	}
}
