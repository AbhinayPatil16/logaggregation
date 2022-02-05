package com.log.aggregation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_aggregations")
public class OutputLog {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
	
	@Column(name = "event_id")
	private String eventId;
	
	@Column(name = "duration")
	private Long duration;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "alert")
	private boolean alert;
	
	
	public OutputLog(String eventId, Long duration, String type, boolean alert) {
		super();
		this.eventId = eventId;
		this.duration = duration;
		this.type = type;
		this.alert = alert;
	}
	
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	
	
	
}
