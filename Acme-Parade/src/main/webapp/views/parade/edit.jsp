<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<fieldset class="col-md-6 col-md-offset-3">
	<security:authorize access="hasRole('BROTHERHOOD')">		
				<form:form action="brotherhood/parade/edit.do"
					modelAttribute="parade" class="form-horizontal" method="post">
					<div class="form-group ">

						<form:hidden path="id"/>
						<form:hidden path="version"/>
						<acme:textbox code="parade.title" path="title" />
						<acme:textarea code="parade.description" path="description" />
						<acme:textbox placeholder="dd-MM-yyyy" code="parade.moment" path="moment" />
						
						<spring:message code ="parade.mode"/>	
						<form:select code="parade.mode" path="mode" >
							<form:option value="FINAL"></form:option>
							<form:option value="DRAFT"></form:option>
						</form:select>
						
						
						<br>
						<br>
						<spring:message code = "parade.floats"/>
						<br>
						<form:select id="floats" code="parade.floats" path="floats">
							<form:options items="${floats}" itemLabel="title" itemValue="id" />
						</form:select>
		
						<br>
						<br>
						<acme:submit name="save" code="parade.save" />
						<jstl:if test="${parade.id!=0}">
							<acme:submitConfirmation name="delete" code="parade.delete"
								onclick="parade.delete.confirmation" />
						</jstl:if>
						<acme:cancel url="/brotherhood/parade/myList.do"
							code="parade.cancel" />
					</div>
				</form:form>
				</security:authorize>
					<security:authorize access="hasRole('CHAPTER')">
					<form:form action="chapter/parade/reject.do"
					modelAttribute="parade" class="form-horizontal" method="post">
					<div class="form-group ">

						<form:hidden path="id"/>
						<form:hidden path="version"/>
						<acme:textarea code="parade.explanation" path="explanation" />

						<acme:submit name="save" code="parade.save" />
						<acme:cancel url="/chapter/parade/list.do"
							code="parade.cancel" />
					</div>
				</form:form>		
				</security:authorize>
			</fieldset>
		

		</div>
	</div>
</div>
