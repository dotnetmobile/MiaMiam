
<%@ page import="miamiam.PhotoStep" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'photoStep.label', default: 'PhotoStep')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-photoStep" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav roundedBorder" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="photoStep.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-photoStep" class="content scaffold-list" role="main">
			<h1><g:message code="photoStep.list.label"/></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'photoStep.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'photoStep.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="photo" title="${message(code: 'photoStep.photo.label', default: 'Photo')}" />
					
						<th><g:message code="photoStep.recipe.label" default="Recipe" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${photoStepInstanceList}" status="i" var="photoStepInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${photoStepInstance.id}">${fieldValue(bean: photoStepInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: photoStepInstance, field: "description")}</td>
					
						<td><img src="${createLink(controller:'photoStep', action:'viewImage', id:"${photoStepInstance?.id}")}" /></td>
					
						<td>${fieldValue(bean: photoStepInstance, field: "recipe")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${photoStepInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
