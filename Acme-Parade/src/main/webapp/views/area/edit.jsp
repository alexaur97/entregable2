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

				<form:form action="area/administrator/edit.do"
					modelAttribute="area" class="form-horizontal" method="post">
					<div class="form-group ">

						<form:hidden path="id"/>
						<form:hidden path="version"/>
						<acme:textbox code="area.name" path="name" />
						
						<acme:textarea code="area.photo" path="photo" />
						<spring:message code="area.photos"/>
						<br>
						<acme:submit name="save" code="area.save" />
						<jstl:if test="${area.id!=0}">
							<acme:submitConfirmation name="delete" code="area.delete"
								onclick="area.delete.confirmation" />
						</jstl:if>
						<acme:cancel url="/area/administrator/list.do"
							code="area.cancel" />
					</div>
				</form:form>
			</fieldset>
		

		</div>
	</div>
</div>
