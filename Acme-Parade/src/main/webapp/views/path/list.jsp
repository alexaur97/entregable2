<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('BROTHERHOOD')">
	
	<jstl:forEach items="${paths}" var="path">
	<h4><spring:message code="path.path" /><jstl:out value="${path.id}" /></h4>
		<table id="segmentsTable" class="table table-striped">
			<thead>
		        <tr>
		            <th><spring:message code="segment.originX" /></th>
		            <th><spring:message code="segment.originY" /></th>
		            <th><spring:message code="segment.destinationX" /></th>
		            <th><spring:message code="segment.destinationY" /></th>
		            <th><spring:message code="segment.originTime" /></th>
		            <th><spring:message code="segment.destinationTime" /></th>
		            <th><spring:message code="segment.edit" /></th>
		            <th><spring:message code="segment.delete" /></th>	            
		        </tr>
	        </thead>
	        <tbody>		
					<jstl:forEach items="${path.segments}" var="segment">
		    			<tr>
		    				<td>   					  								
								<jstl:out value="${segment.originX}" />
							</td>
							<td>   					  								
								<jstl:out value="${segment.originY}" />
							</td>
							<td>   					  								
								<jstl:out value="${segment.destinationX}" />
							</td>
							<td>   					  								
								<jstl:out value="${segment.destinationY}" />
							</td>
							<td>   					  								
								<jstl:out value="${segment.originTime}" />
							</td>
							<td>   					  								
								<jstl:out value="${segment.destinationTime}" />
							</td>
							<td>   					  								
								<acme:cancel url="/path/editSegment.do?segmentId=${segment.id}" code="segment.edit"/>
							</td>
							<td>   					  								
								<acme:cancel url="/path/deleteSegment.do?segmentId=${segment.id}" code="segment.delete"/>
							</td>
						</tr>	
					</jstl:forEach>	
			</tbody>
		</table>
		<acme:cancel url="/path/createSegment.do?pathId=${path.id}" code="segment.create"/>
		<acme:cancel url="/path/delete.do?pathId=${path.id}" code="path.delete"/>
	</br>		
			
	</jstl:forEach>
	</br>	
	</br>
	</br>	
		
		<acme:cancel url="/path/create.do?paradeId=${paradeId}" code="path.create"/>
</security:authorize>
