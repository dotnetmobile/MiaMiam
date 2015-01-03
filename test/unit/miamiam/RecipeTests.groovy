/*  <MiaMiam: Web application for publishing recipes
    Copyright (C) <2014>  <Michel Petrovic> <email: dotnetmobile@gmail.com>

    Contributor(s): _____________________.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package miamiam



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Recipe)
class RecipeTests {

    void testSingleRecipe() {
		Category successfulCategory = new Category(name:'mot successful')
		
		Recipe recipe = new Recipe(name:'pizza',ingredient:'melt, eggs, milk, salt, ...',description:'call mama for preparation', category:successfulCategory)
		recipe.save()
		
       assert Recipe.count == 1
	   
	   assert recipe.name == 'pizza'
	   assert recipe.ingredient == 'melt, eggs, milk, salt, ...'
	   assert recipe.description == 'call mama for preparation'
	   assert recipe.category == successfulCategory
    }
}
