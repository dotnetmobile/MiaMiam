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

class Category {
	
	String name
	String description


  	static searchable = {
		root false	
	}
	
	static hasMany = [ recipes: Recipe]
	
    static constraints = {
		name(size: 1..100, blank: false, unique: true)
		description(size: 1..200, blank: true)
    }
	
	String toString() {
		return name
	}
}
