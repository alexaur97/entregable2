<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="configurationParameters"
	action="configurationParameters/administrator/edit.do">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="name"><spring:message code="configurationParameters.name"/></form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br/>
	
	<form:label path="banner"><spring:message code="configurationParameters.banner"/></form:label>
	<form:input path="banner"/>
	<form:errors cssClass="error" path="banner"/>
	<br/>
	<form:label path="sysMessage"><spring:message code="configurationParameters.sysMessage"/></form:label>
	<form:input path="sysMessage"/>
	<form:errors cssClass="error" path="sysMessage"/>
	<br/>
	<form:label path="sysMessageEs"><spring:message code="configurationParameters.sysMessageEs"/></form:label>
	<form:input path="sysMessageEs"/>
	<form:errors cssClass="error" path="sysMessageEs"/>
	<br/>
	<form:label path="countryCode"><spring:message code="configurationParameters.countryCode"/></form:label>
	<form:input path="countryCode"/>
	<form:errors cssClass="error" path="countryCode"/>
	<br/>
	<acme:textbox type="number" min="0" max="24"  path="finderCachedHours" code="configurationParameters.finderCachedHours"/>
	<form:errors cssClass="error" path="finderCachedHours"/>
	<br/>	
	<acme:textbox type="number" min="0" max="100" path="finderMaxResults" code="configurationParameters.finderMaxResults"/>
	<form:errors cssClass="error" path="finderMaxResults"/>
	<br/>
	
	<jstl:if test="${b eq true}">
	<p style="color:green"><spring:message code="configurationParameters.success"/></p>
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="configurationParameters.save"/>"/>
	<input type="button" name="cancel" value="<spring:message code="configurationParameters.cancel"/>" onclick="javascript: relativeRedir('#')"/>
	
</form:form>
