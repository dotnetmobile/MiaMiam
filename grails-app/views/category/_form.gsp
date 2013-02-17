<%@ page import="miamiam.Category" %>



<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="category.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="100" required="" value="${categoryInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="category.description.label" default="Description" />
	</label>
	<g:textField name="description" maxlength="200"  value="${categoryInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'recipes', 'error')} ">
	<label for="recipes">
		<g:message code="category.recipes.label" default="Recipes" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${categoryInstance?.recipes?}" var="r">
    <li><g:link controller="recipe" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="recipe" action="create" params="['category.id': categoryInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'recipe.label', default: 'Recipe')])}</g:link>
</li>
</ul>

</div>

