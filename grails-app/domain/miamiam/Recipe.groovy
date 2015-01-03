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

class Recipe {

	String name
	String ingredient
	String description
	Category category

	static searchable = {
		photoSteps component: true
		category component: true
	}

	static belongsTo = Category
	static hasMany = [ photoSteps : PhotoStep]
	
	
    static constraints = {
		name(nullable: false, size: 1..100, blank: false)
		ingredient(size: 1..3000, nullable:true)
		description(size: 1..3000, blank: false)
	}


		
	String toString() {
		return name
	}

	String ingredientAsHTML() {
		return ingredient.encodeAsHTML().replaceAll("\n", "<br>").replaceAll("\"","")
	}

	String recipeAsHTML() {
		return description.encodeAsHTML().replaceAll("\n", "<br>").replaceAll("\"","")
	}
}
