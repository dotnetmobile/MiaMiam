
<%@ page import="miamiam.PhotoStep" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'photoStep.label', default: 'PhotoStep')}" />
		<title><g:message code="photoStep.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-photoStep" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav roundedBorder" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="photoStep.list.label" args="[entityName]" /></g:link></li>
				<sec:ifAllGranted roles="ROLE_ADMIN">
					<li><g:link class="create" action="create"><g:message code="photoStep.new.label" args="[entityName]" /></g:link></li>
				</sec:ifAllGranted>
			</ul>
		</div>
		<div id="show-photoStep" class="content scaffold-show" role="main">
			<h1><g:message code="photoStep.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list photoStep">
			
				<g:if test="${photoStepInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="photoStep.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${photoStepInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${photoStepInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="photoStep.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${photoStepInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${photoStepInstance?.photo}">
				<li class="fieldcontain">
					<span id="photo-label" class="property-label"><g:message code="photoStep.photo.label" default="Photo" /></span>				
					
					<img src="${createLink(controller:'photoStep', action:'viewImage', id:"${photoStepInstance?.id}")}" />   
				</li>
				</g:if>
			
				<g:if test="${photoStepInstance?.recipe}">
				<li class="fieldcontain">
					<span id="recipe-label" class="property-label"><g:message code="photoStep.recipe.label" default="Recipe" /></span>
					
						<span class="property-value" aria-labelledby="recipe-label"><g:link controller="recipe" action="show" id="${photoStepInstance?.recipe?.id}">${photoStepInstance?.recipe?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons roundedBorder">
					<g:hiddenField name="id" value="${photoStepInstance?.id}" />
					<sec:ifAllGranted roles="ROLE_ADMIN">
						<g:link class="edit" action="edit" id="${photoStepInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</sec:ifAllGranted>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
