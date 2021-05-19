package com.token.jwtoken.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * @author Karthik Suresh
 *
 */
@JsonInclude(Include.NON_NULL)
public class RestResponse {
	
	public RestResponse() {
	}

	@NotNull
	private String msg;

	@JsonInclude
	private Object data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public RestResponse(final String msg) {
		this.msg = msg;
	}

	public RestResponse(final Object data) {
		super();
		this.data = data;
	}

	public RestResponse(final String msg, final Object data) {
		super();
		this.data = data;
		this.msg = msg;
	}

	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("RestResponse [msg=").append(msg).append(", data=").append(data).append("]");
		return builder.toString();
	}

}



