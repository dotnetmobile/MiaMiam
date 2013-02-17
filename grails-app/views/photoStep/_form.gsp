<%@ page import="miamiam.PhotoStep" %>



<div class="fieldcontain ${hasErrors(bean: photoStepInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="photoStep.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="100" required="" value="${photoStepInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: photoStepInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="photoStep.description.label" default="Description" />
	</label>
	<g:textField name="description" maxlength="200" value="${photoStepInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: photoStepInstance, field: 'photo', 'error')} required">
	<label for="photo">
		<g:message code="photoStep.photo.label" default="Photo" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="photo" name="photo" />
</div>

<div class="fieldcontain ${hasErrors(bean: photoStepInstance, field: 'recipe', 'error')} required">
	<label for="recipe">
		<g:message code="photoStep.recipe.label" default="Recipe" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="recipe" name="recipe.id" from="${miamiam.Recipe.list()}" optionKey="id" required="" value="${photoStepInstance?.recipe?.id}" class="many-to-one"/>
</div>

