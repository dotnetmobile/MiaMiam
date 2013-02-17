
<%@ page import="miamiam.Category" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
		<title><g:message code="category.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-category" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav roundedBorder" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="category.list.label" args="[entityName]" /></g:link></li>
				<sec:ifAllGranted roles="ROLE_ADMIN">
					<li><g:link class="create" action="create"><g:message code="category.new.label" args="[entityName]" /></g:link></li>
				</sec:ifAllGranted>					
			</ul>
		</div>
		<div id="show-category" class="content scaffold-show" role="main">
			<h1><g:message code="category.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list category">
			
				<g:if test="${categoryInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="category.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${categoryInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${categoryInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="category.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${categoryInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${categoryInstance?.recipes}">
				<li class="fieldcontain">
					<span id="recipes-label" class="property-label"><g:message code="category.recipes.label" default="Recipes" /></span>
					
						<g:each in="${categoryInstance.recipes}" var="r">
						<span class="property-value" aria-labelledby="recipes-label"><g:link controller="recipe" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons roundedBorder">
					<g:hiddenField name="id" value="${categoryInstance?.id}" />
					<sec:ifAllGranted roles="ROLE_ADMIN">
						<g:link class="edit" action="edit" id="${categoryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</sec:ifAllGranted>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
